package com.example.covidhelper.ui.Sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.MainActivity;
import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity
{
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button sign_in_btn = findViewById((R.id.sign_in_btn));
        TextView link_signup = findViewById((R.id.link_signup));
        TextView link_forget_password = findViewById((R.id.link_forget_password));

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        loginViewModel = factory.create(LoginViewModel.class);

        sign_in_btn.setOnClickListener(v ->{

            TextInputLayout loginIdTextInputLayout, loginPasswordInputLayout;
            loginIdTextInputLayout = findViewById(R.id.loginIdTextInputLayout);
            loginPasswordInputLayout = findViewById(R.id.loginPasswordInputLayout);


            String loginIcStr = Objects.requireNonNull(loginIdTextInputLayout.getEditText()).getText().toString();
            String loginPassword = Objects.requireNonNull(loginPasswordInputLayout.getEditText()).getText().toString();

            loginIdTextInputLayout.setErrorEnabled(false);
            loginPasswordInputLayout.setErrorEnabled(false);

            boolean logInIdIsNull, logInPasswordIsNull;
            logInIdIsNull = validateIsNull(loginIdTextInputLayout, loginIcStr, "IC/Passport number cannot be empty");
            logInPasswordIsNull = validateIsNull(loginPasswordInputLayout, loginPassword, "Password cannot be empty");

            if(logInIdIsNull && logInPasswordIsNull) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());
                executor.execute(() ->
                {
                    User user = loginViewModel.getCertainUser(loginIcStr, loginPassword);
                    handler.post(() ->
                    {
                        if (user != null) {
                            SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("userID", user.userID);
                            editor.apply();

                            //when get the inform!!!!
//                        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                        sp.getInt("userID", -1);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            showError(loginPasswordInputLayout,"Password and account number do not match");
                        }
                    });
                });

            }

        });
        link_signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
//        link_forget_password.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, .class)));
    }

    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        Objects.requireNonNull(textInputLayout.getEditText()).setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    private boolean validateIsNull(TextInputLayout textInputLayout, String input, String error){
        if(input.equals("")){
            showError(textInputLayout,error);
            return false;
        }
        return true;
    }

}