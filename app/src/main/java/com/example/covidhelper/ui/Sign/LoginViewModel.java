package com.example.covidhelper.ui.Sign;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel
{
    private Repository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    LiveData<List<User>> getCertainUser(String iCNumber, String password) {
        return mRepository.getCertainUser(iCNumber, password);
    }

    void insert(User user) {
        mRepository.insert(user);
    }
}
