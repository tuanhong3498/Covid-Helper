package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineDose1Record;
import com.example.covidhelper.database.table.VaccineDose2Record;
import com.example.covidhelper.ui.Sign.Repository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel
{
    private ProfileRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public ProfileViewModel(Application application) {
        super(application);
        mRepository = new ProfileRepository(application);
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return mRepository.getUserInfo(userID);
    }

    LiveData<List<VaccineDose1Record>> getVaccineDose1Record(int userID)
    {
        return mRepository.getVaccineDose1Record(userID);
    }

    LiveData<List<VaccineDose2Record>> getVaccineDose2Record(int userID)
    {
        return mRepository.getVaccineDose2Record(userID);
    }
}
