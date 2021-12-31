package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class LoginViewModel extends AndroidViewModel
{
    private final Repository mRepository;

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    User getCertainUser(String iCNumber, String password) {
        return mRepository.getCertainUser(iCNumber, password);
    }

    public User getUser(String iCNumber, String password) throws ExecutionException, InterruptedException
    {
        return mRepository.getUser(iCNumber, password);
    }

    LiveData<Integer> checkUniquenessOfIC(String iCNumber){
        return mRepository.checkUniquenessOfIC(iCNumber);
    }

    void insert(User user) {
        mRepository.insert(user);
    }
}
