package com.example.covidhelper.ui.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.MainActivity;
import com.example.covidhelper.R;

public class LoginActivity extends AppCompatActivity
{
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button sign_in_btn = findViewById((R.id.sign_in_btn));
        TextView link_signup = findViewById((R.id.link_signup));

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        loginViewModel = factory.create(LoginViewModel.class);

        sign_in_btn.setOnClickListener(v ->{

            EditText loginIcTextInput = findViewById((R.id.loginIdTextInput));
            String loginIcStr = loginIcTextInput.getText().toString();
            EditText loginPasswordTextInput = findViewById((R.id.loginPasswordTextInput));
            String loginPassword = loginPasswordTextInput.getText().toString();

            loginViewModel.getCertainUser(loginIcStr, loginPassword).observe(this, userArray -> {
                // Update the cached copy of the words in the adapter.
                if (userArray != null && userArray.size() == 1)
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                //System.out.println("查找失败"+userArray.size());
            });

        });
        link_signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }
}