package com.example.btn_duolingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

        // Chuyển sang màn hình chính sau khi đăng ký thành công
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng màn hình đăng ký
        });

        // Quay lại màn hình đăng nhập
        tvLoginLink.setOnClickListener(v -> {
            finish(); // Đóng RegisterActivity sẽ quay lại LoginActivity (vì LoginActivity đã gọi nó)
        });
    }
}