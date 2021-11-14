package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button sign_in_btn = findViewById((R.id.sign_in_btn));
        TextView link_signup = findViewById((R.id.link_signup));
        sign_in_btn.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        link_signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }
}