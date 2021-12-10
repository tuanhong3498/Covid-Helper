package com.example.covidhelper.ui.dashboard.tools.vaccine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;

public class VaccinationViewModel extends AndroidViewModel
{
    private VaccinationRepository vaccinationRepository;

    public VaccinationViewModel(@NonNull Application application)
    {
        super(application);
        vaccinationRepository = new VaccinationRepository(application);
    }

    LiveData<User> getUser(int userID)
    {
        return vaccinationRepository.getUserInfo(userID);
    }

    LiveData<VaccineRegistrationRecord> getVaccineRegistrationRecord(int userID)
    {
        return vaccinationRepository.getVaccineRegistrationRecord(userID);
    }
}