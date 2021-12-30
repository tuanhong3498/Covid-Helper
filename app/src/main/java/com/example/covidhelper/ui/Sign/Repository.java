package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class Repository
{
    private final UserDAO userDAO;

    Repository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
    }

    User getCertainUser(String iCNumber, String password) {
        return userDAO.getCertainUser(iCNumber,password);
    }

    LiveData<Integer> checkUniquenessOfIC(String iCNumber){
//        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            return userDAO.checkUniquenessOfIC(iCNumber);
//        });
    }

    void insert(User user) {
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.insert(user));
    }
}
