package com.example.ai;  // ✅ CORRECT!

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etName, etAge, etProfession;
    private MaterialButton btnLogin;
    private SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("CalmifyPrefs", MODE_PRIVATE);

        // If user already logged in, skip login
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etName       = findViewById(R.id.etName);
        etAge        = findViewById(R.id.etAge);
        etProfession = findViewById(R.id.etProfession);
        btnLogin     = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> validateAndLogin());
    }

    private void validateAndLogin() {
        String name       = etName.getText() != null ? etName.getText().toString().trim() : "";
        String ageStr     = etAge.getText() != null ? etAge.getText().toString().trim() : "";
        String profession = etProfession.getText() != null ? etProfession.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name)) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ageStr)) {
            etAge.setError("Please enter your age");
            etAge.requestFocus();
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 5 || age > 120) {
                etAge.setError("Enter a valid age (5-120)");
                etAge.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etAge.setError("Enter a valid age");
            etAge.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(profession)) {
            etProfession.setError("Please enter your profession");
            etProfession.requestFocus();
            return;
        }

        // Save to SharedPreferences
        prefs.edit()
                .putBoolean("isLoggedIn", true)
                .putString("userName", name)
                .putInt("userAge", age)
                .putString("userProfession", profession)
                .apply();

        Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}