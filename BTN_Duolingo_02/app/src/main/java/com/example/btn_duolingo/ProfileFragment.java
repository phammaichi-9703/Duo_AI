package com.example.btn_duolingo;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvFullName, tvUsername, tvXP, tvStreak, tvDOB, tvAddress, tvPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvFullName = view.findViewById(R.id.tvProfileFullName);
        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvXP = view.findViewById(R.id.tvProfileXP); // Đảm bảo bạn đã đặt ID này trong XML
        tvStreak = view.findViewById(R.id.tvProfileStreak); // Đảm bảo bạn đã đặt ID này trong XML
        tvDOB = view.findViewById(R.id.tvProfileDOB);
        tvAddress = view.findViewById(R.id.tvProfileAddress);
        tvPhone = view.findViewById(R.id.tvProfilePhone);

        loadUserData();

        return view;
    }

    private void loadUserData() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String username = sharedPref.getString("current_username", "");

        if (!username.isEmpty()) {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            Cursor cursor = dbHelper.getUserByUsername(username);

            if (cursor != null && cursor.moveToFirst()) {
                tvFullName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULLNAME)));
                tvUsername.setText("@" + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)));
                tvDOB.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOB)));
                tvAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS)));
                tvPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)));
                
                // Hiển thị XP và Streak (Nếu bạn đã thêm ID vào XML)
                if (tvXP != null) tvXP.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_XP)));
                if (tvStreak != null) tvStreak.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STREAK)));
                
                cursor.close();
            }
        }
    }
}