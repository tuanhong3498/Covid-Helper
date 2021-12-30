package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public User getUser(String iCNumber, String password) throws ExecutionException, InterruptedException
    {
        Callable<User> callable = () -> userDAO.getCertainUser(iCNumber,password);

        Future<User> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
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
