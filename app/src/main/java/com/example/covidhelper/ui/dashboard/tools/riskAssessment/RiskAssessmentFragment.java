package com.example.covidhelper.ui.dashboard.tools.riskAssessment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.SelfTestResult;
import com.example.covidhelper.ui.dashboard.tools.selfTest.ReportSelfTestFragmentDirections;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class RiskAssessmentFragment extends Fragment {

    private RiskAssessmentViewModel riskAssessmentViewModel;
    int riskScore,symptomScore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_risk_assessment, container, false);

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        riskAssessmentViewModel = factory.create(RiskAssessmentViewModel.class);

        riskScore = 0;
        symptomScore = 0;
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
        question1_checkbox_list = new ArrayList<>();
        question1_checkbox_list.add(question1_cb1);
        question1_checkbox_list.add(question1_cb2);
        question1_checkbox_list.add(question1_cb3);
        question1_checkbox_list.add(question1_cb4);
        question1_checkbox_list.add(question1_cb5);
        question1_checkbox_list.add(question1_cb6);
        question2_checkbox_list = new ArrayList<>();
        question2_checkbox_list.add(question2_cb1);
        question2_checkbox_list.add(question2_cb2);
        question2_checkbox_list.add(question2_cb3);
        question2_checkbox_list.add(question2_cb4);

        RadioGroup question3 = root.findViewById(R.id.question3_rg);
        RadioGroup question4 = root.findViewById(R.id.question4_rg);
        RadioGroup question5 = root.findViewById(R.id.question5_rg);
        RadioGroup question6 = root.findViewById(R.id.question6_rg);

        question3.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
            if(rb.getText().toString().equals("Yes")){
                riskScore = riskScore+1;
            }
        });
        question4.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
            if(rb.getText().toString().equals("Yes")){
                riskScore = riskScore+1;
            }
        });
        question5.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
            if(rb.getText().toString().equals("Yes")){
                riskScore = riskScore+1;
            }
        });
        question6.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton rb = root.findViewById(radioGroup.getCheckedRadioButtonId());
            if(rb.getText().toString().equals("Yes")){
                riskScore = riskScore+1;
            }
        });

        button.setOnClickListener(v -> {
            StringBuilder question1_sb = new StringBuilder();
            for (CheckBox checkbox : question1_checkbox_list) {
                    if (checkbox.isChecked()){
                        symptomScore = symptomScore+1;
                        question1_sb.append(checkbox.getText().toString()).append(" ");
                    }
            }
            StringBuilder question2_sb = new StringBuilder();
            for (CheckBox checkbox : question2_checkbox_list) {
                if (checkbox.isChecked()){
                    symptomScore = symptomScore+1;
                    question2_sb.append(checkbox.getText().toString()).append(" ");
                }
            }

            if (question1_sb.toString().equals("") || question2_sb.toString().equals("") || question3.getCheckedRadioButtonId()==-1 || question4.getCheckedRadioButtonId()==-1 || question5.getCheckedRadioButtonId()==-1 || question6.getCheckedRadioButtonId()==-1){
                unfinishedError();
            } else {
                // create a confirmation dialog
                new MaterialAlertDialogBuilder(requireContext())
                        .setMessage("Make sure your selection is correct.  it cannot be changed.")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Confirm", (dialog, which) ->
                        {

                            SharedPreferences sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            // save the result
                            String riskStatus = saveRiskStatus(riskScore);
                            String symptomStatus = saveSymptomStatus(symptomScore);
                            riskAssessmentViewModel.updateRiskStatus(riskStatus, sp.getInt("userID", -1));
                            riskAssessmentViewModel.updateSymptomStatus(symptomStatus, sp.getInt("userID", -1));

                            // return to dashboard
                            NavController navController = Navigation.findNavController(root);
                            navController.navigate(R.id.dashboardFragment);
                        })
                        .show();
            }

        });


        return root;
    }

    private void unfinishedError() {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage("Please complete all options before submitting.")
                .setPositiveButton("GOT IT", null)
                .show();
    }

    private String saveRiskStatus(int score) {
        if (score <= 1) {
            return "Low Risk";
        } else if (score <= 3) {
            return "Medium Risk";
        } else {
            return "High Risk";
        }
    }

    private String saveSymptomStatus(int score) {
        if (score <= 5) {
            return "Low Symptom";
        } else if (score <= 8) {
            return "Medium Symptom";
        } else {
            return "High Symptom";
        }
    }


}