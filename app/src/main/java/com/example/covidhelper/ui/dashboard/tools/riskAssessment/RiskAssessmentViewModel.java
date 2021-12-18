package com.example.covidhelper.ui.dashboard.tools.riskAssessment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.covidhelper.ui.dashboard.tools.riskAssessment.RiskAssessmentRepository;

public class RiskAssessmentViewModel extends AndroidViewModel
{
    private final RiskAssessmentRepository mRepository;


    public RiskAssessmentViewModel (Application application) {
        super(application);
        mRepository = new RiskAssessmentRepository(application);
    }

    void  updateSymptomStatus(String symptomStatus,int userID){
        mRepository.updateSymptomStatus(symptomStatus,userID);
    }
}
