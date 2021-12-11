package com.example.covidhelper.ui.dashboard.tools;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;
import com.example.covidhelper.ui.Sign.Repository;
import com.example.covidhelper.ui.dashboard.tools.RiskAssessmentRepository;

import java.util.List;

public class RiskAssessmentViewModel extends AndroidViewModel
{
    private RiskAssessmentRepository mRepository;


    public RiskAssessmentViewModel (Application application) {
        super(application);
        mRepository = new RiskAssessmentRepository(application);
    }

    void  updateSymptomStatus(String symptomStatus,int userID){
        mRepository.updateSymptomStatus(symptomStatus,userID);
    }
}
