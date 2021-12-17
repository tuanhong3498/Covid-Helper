package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationCertificate;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel
{
    private final ProfileRepository mRepository;

    public ProfileViewModel(Application application) {
        super(application);
        mRepository = new ProfileRepository(application);
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return mRepository.getUserInfo(userID);
    }

    LiveData<List<VaccinationCertificate>> getVaccinationCertificate(int userID) {
        return mRepository.getVaccinationCertificate(userID);
    }

    void  updateUserInformation(String state,String phone,String email,int userID){
        if(!state.equals("")) {
            mRepository.updateUserState(state, userID);
        }
        if(!phone.equals("")) {
            mRepository.updateUserPhone(phone, userID);
        }
        if(!email.equals("")) {
            mRepository.updateUserEmail(email, userID);
        }
    }

    void  updateUserPassword(String password,int userID){
        mRepository.updateUserPassword(password, userID);
    }

}
