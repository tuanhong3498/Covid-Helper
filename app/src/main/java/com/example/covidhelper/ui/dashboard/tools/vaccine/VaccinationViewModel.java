package com.example.covidhelper.ui.dashboard.tools.vaccine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationRecord;
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

    void insertVaccineRegistration(VaccineRegistrationRecord vaccineRegistrationRecord)
    {
        vaccinationRepository.insertVaccineRegistration(vaccineRegistrationRecord);
    }

    LiveData<VaccinationRecord> getVaccinationRecord(int userID, int dosage)
    {
        return vaccinationRepository.getVaccinationRecord(userID, dosage);
    }

    void updateVaccineRegistrationRecord(int userID, String state, String postcode)
    {
        vaccinationRepository.updateVaccineRegistrationRecord(userID, state, postcode);
    }

    void updateUserName(int userID, String username)
    {
        vaccinationRepository.updateUserName(userID, username);
    }

    void updateICNumber(int userID, String ICNumber)
    {
        vaccinationRepository.updateICNumber(userID, ICNumber);
    }

    void updateAppointmentTime(int userID, int dose, long newVaccinationTime)
    {
        vaccinationRepository.updateAppointmentTime(userID, dose, newVaccinationTime);
    }

    void confirmVaccineAppointment(int userID, int dose)
    {
        vaccinationRepository.confirmAppointment(userID, dose);
    }

    boolean isICConflict(int userID, String icNumber)
    {
        User user = vaccinationRepository.getUserByIC(icNumber);
        if (user == null)
            return false;
        // check if the ic number conflict with that of the other user
        // if that ic number belongs to other user, then there is a conflict
        return user.userID != userID;
    }
}