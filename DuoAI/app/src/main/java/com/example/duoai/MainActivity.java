package com.example.duoai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_container);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                showHome();
                return true;
            } else if (itemId == R.id.navigation_settings) {
                showSettings();
                return true;
            }
            return false;
        });
    }

    private void showHome() {
        fragmentContainer.removeAllViews();
        LayoutInflater.from(this).inflate(R.layout.fragment_home, fragmentContainer, true);
    }

    private void showSettings() {
        fragmentContainer.removeAllViews();
        View settingsView = LayoutInflater.from(this).inflate(R.layout.fragment_settings, fragmentContainer, true);

        SwitchMaterial switchDarkMode = settingsView.findViewById(R.id.switchDarkMode);

        if (switchDarkMode != null) {
            switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
        }
    }
}
