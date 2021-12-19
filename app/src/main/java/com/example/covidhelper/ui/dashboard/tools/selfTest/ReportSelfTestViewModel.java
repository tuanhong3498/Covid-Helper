package com.example.covidhelper.ui.dashboard.tools.selfTest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.covidhelper.database.table.SelfTestResult;

import java.util.concurrent.ExecutionException;

public class ReportSelfTestViewModel extends AndroidViewModel
{
    private final ReportSelfTestRepository reportSelfTestRepository;

    public ReportSelfTestViewModel(@NonNull Application application)
    {
        super(application);
        reportSelfTestRepository = new ReportSelfTestRepository(application);
    }

    public void saveSelfTestResult(SelfTestResult selfTestResult)
    {
        reportSelfTestRepository.insetSelfTestResult(selfTestResult);
    }

    public void updateRiskStatus(int userID, String riskStatus)
    {
        reportSelfTestRepository.updateRiskStatus(userID, riskStatus);
    }

    public String getRiskStatus(int userID) throws ExecutionException, InterruptedException
    {
        return reportSelfTestRepository.getRiskStatus(userID);
    }
}