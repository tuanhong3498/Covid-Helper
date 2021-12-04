package com.example.covidhelper.ui.Sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidhelper.R;

public class SignUpActivity extends AppCompatActivity {

    AutoCompleteTextView stateDropDownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        stateDropDownMenu = findViewById(R.id.sign_up_autoTextView_state);

        Button sign_up_btn = findViewById((R.id.sign_up_btn));
        TextView link_sign_in = findViewById((R.id.link_sign_in));
        sign_up_btn.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
        link_sign_in.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, states);
        stateDropDownMenu.setAdapter(arrayAdapter);
    }
}