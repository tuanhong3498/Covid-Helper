package com.example.covidhelper.ui.dashboard.tools.vaccine;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccinationCertificateDAO;
import com.example.covidhelper.database.DAO.VaccinationRecordDAO;
import com.example.covidhelper.database.DAO.VaccineRegistrationRecordDAO;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationCertificate;
import com.example.covidhelper.database.table.VaccinationRecord;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VaccinationRepository
{
    private final UserDAO userDAO;
    private final VaccineRegistrationRecordDAO vaccineRegistrationRecordDAO;
    private final VaccinationRecordDAO vaccinationRecordDAO;
    private final VaccinationCertificateDAO vaccinationCertificateDAO;

    VaccinationRepository (Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
        vaccineRegistrationRecordDAO = covidHelperDatabase.getVaccineRegistrationRecordDAO();
        vaccinationRecordDAO = covidHelperDatabase.getVaccinationRecordDAO();
        vaccinationCertificateDAO = covidHelperDatabase.getVaccinationCertificateDAO();
    }

    LiveData<User> getUserInfo(int userID)
    {
        return userDAO.getUser(userID);
    }

    LiveData<VaccineRegistrationRecord> getVaccineRegistrationRecord(int userID)
    {
        return vaccineRegistrationRecordDAO.getRegistrationRecord(userID);
    }

    void insertVaccineRegistration(VaccineRegistrationRecord vaccineRegistrationRecord)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                vaccineRegistrationRecordDAO.insert(vaccineRegistrationRecord));

    }

    LiveData<VaccinationRecord> getVaccinationRecord(int userID, int dosage)
    {
        return vaccinationRecordDAO.getVaccinationRecord(userID, dosage);
    }

    void updateVaccineRegistrationRecord(int userID, String state, String postcode)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                vaccineRegistrationRecordDAO.updateRegistrationRecord(userID, state, postcode));

    }

    void updateUserName(int userID, String username)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                userDAO.updateUserName(userID, username));

    }

    void updateICNumber(int userID, String ICNumber)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                userDAO.updateICNumber(userID, ICNumber));

    }

    void updateUserState(int userID, String state)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                userDAO.updateUserState(state, userID));
    }

    void updateAppointmentTime(int userID, int dose, long newVaccinationTime)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                vaccinationRecordDAO.updateAppointmentTime(userID, dose, newVaccinationTime));

    }

    void confirmAppointment(int userID, int dose)
    {
        CovidHelperDatabase.databaseWriteExecutor.execute(() ->
                vaccinationRecordDAO.updateVaccinationAppointmentConfirmed(userID, dose, true));
    }

    User getUserByIC(String icNumber)
    {
        return userDAO.getUserByIC(icNumber);
    }

    LiveData<List<VaccinationCertificate>> getVaccinationCertificate(int userID) {
        return vaccinationCertificateDAO.getVaccinationCertificate(userID);
    }

    VaccineRegistrationRecord checkVaccineRegistration (int userID) throws ExecutionException, InterruptedException
    {
        Callable<VaccineRegistrationRecord> callable = () -> vaccineRegistrationRecordDAO.checkRegistrationRecord(userID);

        Future<VaccineRegistrationRecord> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }
}
