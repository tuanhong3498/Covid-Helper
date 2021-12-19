package com.example.covidhelper.ui.dashboard.tools.selfTest;

import android.app.Application;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.SelfTestResultDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.SelfTestResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReportSelfTestRepository
{
    private final SelfTestResultDAO selfTestResultDAO;
    private final UserDAO userDAO;

    public ReportSelfTestRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);

        selfTestResultDAO = covidHelperDatabase.getSelfTestResultDAO();
        userDAO = covidHelperDatabase.getUserDAO();
    }

    public void insetSelfTestResult(SelfTestResult selfTestResult)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                selfTestResultDAO.insert(selfTestResult));
    }

    public void updateRiskStatus(int userID, String riskStatus)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                userDAO.updateRiskStatus(riskStatus, userID));
    }

    public String getRiskStatus(int userID) throws ExecutionException, InterruptedException
    {
        Callable<String> callable = new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                return userDAO.getRiskStatus(userID);
            }
        };

        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }
}
