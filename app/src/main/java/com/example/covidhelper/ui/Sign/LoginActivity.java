package com.example.covidhelper.ui.Sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.MainActivity;
import com.example.covidhelper.R;
import com.google.android.material.textfield.TextInputLayout;

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

            TextInputLayout loginIdTextInputLayout, loginPasswordInputLayout;
            loginIdTextInputLayout = findViewById(R.id.loginIdTextInputLayout);
            loginPasswordInputLayout = findViewById(R.id.loginPasswordInputLayout);


            String loginIcStr = loginIdTextInputLayout.getEditText().getText().toString();
            String loginPassword = loginPasswordInputLayout.getEditText().getText().toString();

            loginIdTextInputLayout.setErrorEnabled(false);
            loginPasswordInputLayout.setErrorEnabled(false);

            boolean logInIdIsNull, logInPasswordIsNull;
            logInIdIsNull = validateIsNull(loginIdTextInputLayout, loginIcStr, "IC/Passport number cannot be empty");
            logInPasswordIsNull = validateIsNull(loginPasswordInputLayout, loginPassword, "Password cannot be empty");

            if(logInIdIsNull && logInPasswordIsNull) {
                loginViewModel.getCertainUser(loginIcStr, loginPassword).observe(this, userArray -> {
                    // Update the cached copy of the words in the adapter.
                    if (userArray != null && userArray.size() == 1) {

                        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("userID", userArray.get(0).userID);
                        editor.putString("iCNumber", userArray.get(0).iCNumber);
                        editor.putString("fullName", userArray.get(0).fullName);
                        editor.putString("phoneNumber", userArray.get(0).phoneNumber);
                        editor.putString("email", userArray.get(0).email);
                        editor.putString("livingState", userArray.get(0).livingState);
                        editor.putString("password", userArray.get(0).password);
                        editor.putString("riskStatus", userArray.get(0).riskStatus);
                        editor.putString("symptomStatus", userArray.get(0).symptomStatus);
                        editor.putString("vaccinationStage", userArray.get(0).vaccinationStage);
                        editor.apply();

                        //when get the inform!!!!
//                        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                        sp.getInt("userID", -1);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        showError(loginPasswordInputLayout,"Password and account number do not match");
                    }
                });
            }

        });
        link_signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
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