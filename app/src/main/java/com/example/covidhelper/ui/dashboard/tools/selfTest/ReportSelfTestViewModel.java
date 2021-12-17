package com.example.covidhelper.ui.dashboard.tools.selfTest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.covidhelper.database.table.SelfTestResult;

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
}