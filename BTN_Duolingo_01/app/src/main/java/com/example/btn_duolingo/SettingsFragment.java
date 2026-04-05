package com.example.btn_duolingo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private EditText etFullName, etUsername, etPassword, etDOB, etAddress, etPhone;
    private Button btnSave, btnLogout;
    private SwitchMaterial switchDarkMode;
    private DatabaseHelper dbHelper;
    private String currentUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        etFullName = view.findViewById(R.id.etEditFullName);
        etUsername = view.findViewById(R.id.etEditUsername);
        etPassword = view.findViewById(R.id.etEditPassword);
        etDOB = view.findViewById(R.id.etEditDOB);
        etAddress = view.findViewById(R.id.etEditAddress);
        etPhone = view.findViewById(R.id.etEditPhone);
        btnSave = view.findViewById(R.id.btnSaveSettings);
        btnLogout = view.findViewById(R.id.btnLogout);
        switchDarkMode = view.findViewById(R.id.switchDarkMode);

        dbHelper = new DatabaseHelper(getContext());
        
        loadCurrentUserData();

        // Kiểm tra trạng thái Dark Mode hiện tại để set cho Switch
        SharedPreferences themePref = getActivity().getSharedPreferences("AppSettingPref", Context.MODE_PRIVATE);
        boolean isDarkMode = themePref.getBoolean("DarkMode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            
            // Lưu cấu hình vào SharedPreferences
            SharedPreferences.Editor editor = themePref.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();
        });

        btnSave.setOnClickListener(v -> saveUserData());

        btnLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void loadCurrentUserData() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        currentUsername = sharedPref.getString("current_username", "");

        if (!currentUsername.isEmpty()) {
            Cursor cursor = dbHelper.getUserByUsername(currentUsername);
            if (cursor != null && cursor.moveToFirst()) {
                etFullName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULLNAME)));
                etUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)));
                etPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD)));
                etDOB.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOB)));
                etAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS)));
                etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)));
                cursor.close();
            }
        }
    }

    private void saveUserData() {
        String newFullName = etFullName.getText().toString().trim();
        String newUsername = etUsername.getText().toString().trim();
        String newPassword = etPassword.getText().toString().trim();
        String newDOB = etDOB.getText().toString().trim();
        String newAddress = etAddress.getText().toString().trim();
        String newPhone = etPhone.getText().toString().trim();

        if (dbHelper.updateUserInfo(currentUsername, newUsername, newFullName, newPassword, newDOB, newAddress, newPhone)) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("current_username", newUsername);
            editor.apply();
            
            currentUsername = newUsername;
            Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}