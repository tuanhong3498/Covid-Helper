package com.example.covidhelper.ui.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    AutoCompleteTextView stateDropDownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        LoginViewModel loginViewModel = factory.create(LoginViewModel.class);

        TextInputLayout signUpIdTextInputLayout, signUpNameTextInputLayout, signUpPhoneTextInputLayout, signUpEmailInputLayout, signUpStateInputLayout, signUpPasswordInputLayout, signUpRetypePasswordInputLayout;
        signUpIdTextInputLayout = findViewById(R.id.signUpIdTextInputLayout);
        signUpNameTextInputLayout = findViewById(R.id.signUpNameTextInputLayout);
        signUpPhoneTextInputLayout = findViewById(R.id.signUpPhoneTextInputLayout);
        signUpEmailInputLayout = findViewById(R.id.loginEmailInputLayout);
        signUpStateInputLayout = findViewById(R.id.sign_up_state_drop_down_menu);
        signUpPasswordInputLayout = findViewById(R.id.loginPasswordInputLayout);
        signUpRetypePasswordInputLayout = findViewById(R.id.loginRetypePasswordInputLayout);



        stateDropDownMenu = findViewById(R.id.sign_up_autoTextView_state);


        final String[] signUpState = new String[1];
        signUpState[0] = "";
        stateDropDownMenu.setOnItemClickListener((parent, view, position, rowId) -> signUpState[0] = (String)parent.getItemAtPosition(position));


        Button sign_up_btn = findViewById((R.id.sign_up_btn));
        TextView link_sign_in = findViewById((R.id.link_sign_in));
        sign_up_btn.setOnClickListener(v ->{
            String signUpId, signUpName, signUpPhone, signUpEmail, signUpPassword, signUpRetypePassword;
            signUpId = Objects.requireNonNull(signUpIdTextInputLayout.getEditText()).getText().toString();
            signUpName = Objects.requireNonNull(signUpNameTextInputLayout.getEditText()).getText().toString();
            signUpPhone = Objects.requireNonNull(signUpPhoneTextInputLayout.getEditText()).getText().toString();
            signUpEmail = Objects.requireNonNull(signUpEmailInputLayout.getEditText()).getText().toString();
            signUpPassword = Objects.requireNonNull(signUpPasswordInputLayout.getEditText()).getText().toString();
            signUpRetypePassword = Objects.requireNonNull(signUpRetypePasswordInputLayout.getEditText()).getText().toString();

            signUpIdTextInputLayout.setErrorEnabled(false);
            signUpNameTextInputLayout.setErrorEnabled(false);
            signUpPhoneTextInputLayout.setErrorEnabled(false);
            signUpEmailInputLayout.setErrorEnabled(false);
            signUpStateInputLayout.setErrorEnabled(false);
            signUpPasswordInputLayout.setErrorEnabled(false);
            signUpRetypePasswordInputLayout.setErrorEnabled(false);

            boolean signUpIdIsNull, signUpNameNull, signUpPhoneIsNull, signUpEmailIsNull, signUpStateIsNull, signUpPasswordIsNull, signUpRetypePasswordIsNull, signUpPasswordAndRetypePasswordIsSame;
            signUpIdIsNull = validateIsNull(signUpIdTextInputLayout, signUpId, "IC/Passport number cannot be empty");
            signUpNameNull = validateIsNull(signUpNameTextInputLayout, signUpName, "Name cannot be empty");
            signUpPhoneIsNull = validateIsNull(signUpPhoneTextInputLayout, signUpPhone, "Phone cannot be empty");
            signUpEmailIsNull = validateIsNull(signUpEmailInputLayout, signUpEmail, "Email cannot be empty");
            signUpStateIsNull = validateIsNull(signUpStateInputLayout, signUpState[0], "State cannot be empty");
            signUpPasswordIsNull = validateIsNull(signUpPasswordInputLayout, signUpPassword, "Password cannot be empty");
            signUpRetypePasswordIsNull = validateIsNull(signUpRetypePasswordInputLayout, signUpRetypePassword, "Retyped password cannot be empty");
            signUpPasswordAndRetypePasswordIsSame = validateIsTheSame(signUpRetypePasswordInputLayout, signUpPassword,  signUpRetypePassword);

            if(signUpIdIsNull && signUpNameNull && signUpPhoneIsNull && signUpEmailIsNull && signUpStateIsNull && signUpPasswordIsNull && signUpRetypePasswordIsNull && signUpPasswordAndRetypePasswordIsSame) {
                loginViewModel.checkUniquenessOfIC(signUpId).observe(this, uniquenessOfIC -> {
                    if(uniquenessOfIC == 0) {
                        User user = new User(signUpId, signUpName, signUpPhone, signUpEmail, signUpState[0], signUpPassword, "Low Risk", "Low Symptom", null);
                        loginViewModel.insert(user);

                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }else{
                        showError(signUpIdTextInputLayout,"IC/Passport already exists");
                    }
                });

            }
        });
        link_sign_in.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, states);
        stateDropDownMenu.setAdapter(arrayAdapter);
    }

    private void showError(TextInputLayout textInputLayout,String error){
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

    private boolean validateIsTheSame(TextInputLayout textInputLayout, String input1, String input2){
        if(!input1.equals(input2)){
            showError(textInputLayout, "Retyped password is not the same as Password");
            return false;
        }
        return true;
    }


}