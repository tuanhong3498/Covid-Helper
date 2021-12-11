package com.example.covidhelper.ui.dashboard.tools;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.covidhelper.MainActivity;
import com.example.covidhelper.R;
import com.example.covidhelper.ui.Sign.LoginActivity;
import com.example.covidhelper.ui.Sign.LoginViewModel;
import com.example.covidhelper.ui.Sign.SignUpActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RiskAssessmentFragment extends Fragment {

    private RiskAssessmentViewModel riskAssessmentViewModel;
    int score;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_risk_assessment, container, false);

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        riskAssessmentViewModel = factory.create(RiskAssessmentViewModel.class);

        score = 0;
        MaterialButton button;
        button = root.findViewById(R.id.vaccine_button_confirmAppointment);

        CheckBox question1_cb1, question1_cb2, question1_cb3, question1_cb4, question1_cb5, question1_cb6;
        question1_cb1 = root.findViewById(R.id.question1_cb1);
        question1_cb2 = root.findViewById(R.id.question1_cb2);
        question1_cb3 = root.findViewById(R.id.question1_cb3);
        question1_cb4 = root.findViewById(R.id.question1_cb4);
        question1_cb5 = root.findViewById(R.id.question1_cb5);
        question1_cb6 = root.findViewById(R.id.question1_cb6);
        CheckBox question2_cb1, question2_cb2, question2_cb3, question2_cb4;
        question2_cb1 = root.findViewById(R.id.question2_cb1);
        question2_cb2 = root.findViewById(R.id.question2_cb2);
        question2_cb3 = root.findViewById(R.id.question2_cb3);
        question2_cb4 = root.findViewById(R.id.question2_cb4);

        List<CheckBox> question1_checkbox_list, question2_checkbox_list;
        question1_checkbox_list = new ArrayList<CheckBox>();
        question1_checkbox_list.add(question1_cb1);
        question1_checkbox_list.add(question1_cb2);
        question1_checkbox_list.add(question1_cb3);
        question1_checkbox_list.add(question1_cb4);
        question1_checkbox_list.add(question1_cb5);
        question1_checkbox_list.add(question1_cb6);
        question2_checkbox_list = new ArrayList<CheckBox>();
        question2_checkbox_list.add(question2_cb1);
        question2_checkbox_list.add(question2_cb2);
        question2_checkbox_list.add(question2_cb3);
        question2_checkbox_list.add(question2_cb4);

        RadioGroup question3 = root.findViewById(R.id.question3_rg);
        RadioGroup question4 = root.findViewById(R.id.question4_rg);
        RadioGroup question5 = root.findViewById(R.id.question5_rg);
        RadioGroup question6 = root.findViewById(R.id.question6_rg);

        question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb.getText().toString().equals("Yes")){
                    score = score+1;
                }
            }
        });
        question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb.getText().toString().equals("Yes")){
                    System.out.println("单选分数是"+rb.getText().toString());
                    score = score+1;
                }
            }
        });
        question5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb.getText().toString().equals("Yes")){
                    score = score+1;
                }
            }
        });
        question6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb.getText().toString().equals("Yes")){
                    score = score+1;
                }
            }
        });

        button.setOnClickListener(v -> {
            StringBuilder question1_sb = new StringBuilder();
            for (CheckBox checkbox : question1_checkbox_list) {
                    if (checkbox.isChecked()){
                        score = score+1;
                        question1_sb.append(checkbox.getText().toString()).append(" ");
                    }
            }
            if ("".equals(question1_sb.toString())){
                Toast.makeText(getContext(), "请至少选择一个", Toast.LENGTH_SHORT).show();
            }

            StringBuilder question2_sb = new StringBuilder();
            for (CheckBox checkbox : question2_checkbox_list) {
                if (checkbox.isChecked()){
                    score = score+1;
                    question2_sb.append(checkbox.getText().toString()).append(" ");
                }
            }
            if ("".equals(question2_sb.toString())){
                Toast.makeText(getContext(), "请至少选择一个", Toast.LENGTH_SHORT).show();
            }


            if(score <= 5){
                riskAssessmentViewModel.updateSymptomStatus("Low Symptom", 1);
            } else if(score >5 && score <= 8) {
                riskAssessmentViewModel.updateSymptomStatus("Medium Symptom", 1);
            }else {
                riskAssessmentViewModel.updateSymptomStatus("High Symptom", 1);
            }

            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.dashboardFragment);

        });


        return root;
    }


}