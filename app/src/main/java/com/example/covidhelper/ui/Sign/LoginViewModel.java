package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel
{
    private final Repository mRepository;

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    LiveData<List<User>> getCertainUser(String iCNumber, String password) {
        return mRepository.getCertainUser(iCNumber, password);
    }

    LiveData<Integer> checkUniquenessOfIC(String iCNumber){
        return mRepository.checkUniquenessOfIC(iCNumber);
    }

    void insert(User user) {
        mRepository.insert(user);
    }
}
