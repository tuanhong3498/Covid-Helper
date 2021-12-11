package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.User;
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
