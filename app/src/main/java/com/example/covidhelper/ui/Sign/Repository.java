package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class Repository
{
    private UserDAO userDAO;

    Repository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<User>> getCertainUser(String iCNumber, String password) {
        return userDAO.getCertainUser(iCNumber,password);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(User user) {
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }
}
