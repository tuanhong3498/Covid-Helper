package com.example.covidhelper.ui.dashboard.tools.selfTest;

import android.app.Application;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.SelfTestResultDAO;
import com.example.covidhelper.database.table.SelfTestResult;

public class ReportSelfTestRepository
{
    private final SelfTestResultDAO selfTestResultDAO;

    public ReportSelfTestRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);

        selfTestResultDAO = covidHelperDatabase.getSelfTestResultDAO();
    }

    public void insetSelfTestResult(SelfTestResult selfTestResult)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                selfTestResultDAO.insert(selfTestResult));
    }
}
