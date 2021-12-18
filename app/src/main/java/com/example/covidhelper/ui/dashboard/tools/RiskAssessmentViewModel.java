package com.example.covidhelper.ui.dashboard.tools;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

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
