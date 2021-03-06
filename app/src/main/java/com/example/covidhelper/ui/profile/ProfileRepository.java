package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccinationCertificateDAO;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationCertificate;

import java.util.List;

public class ProfileRepository
{
    private final UserDAO userDAO;
    private final VaccinationCertificateDAO vaccineDose1RecordDAO;

    ProfileRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
        vaccineDose1RecordDAO = covidHelperDatabase.getVaccinationCertificateDAO();
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return userDAO.getUserInfo(userID);
    }

    LiveData<List<VaccinationCertificate>> getVaccinationCertificate(int userID) {
        return vaccineDose1RecordDAO.getVaccinationCertificate(userID);
    }


    void updateUserState(String state,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserState(state,userID));
    }

    void  updateUserPhone(String phone,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserPhone(phone,userID));
    }

    void  updateUserEmail(String email,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserEmail(email,userID));
    }

    void  updateUserPassword(String password,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserPassword(password,userID));
    }
}
