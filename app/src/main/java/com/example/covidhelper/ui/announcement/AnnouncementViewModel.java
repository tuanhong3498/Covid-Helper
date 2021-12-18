package com.example.covidhelper.ui.announcement;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.Announcement;

import java.util.List;

public class AnnouncementViewModel extends AndroidViewModel
{
    private final AnnouncementRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public AnnouncementViewModel(Application application) {
        super(application);
        mRepository = new AnnouncementRepository(application);
    }

    LiveData<List<Announcement>> getAllAnnouncement(int userID) {
        return mRepository.getAllAnnouncement(userID);
    }

    LiveData<List<Announcement>> getTaskAnnouncement(int userID) {
        return mRepository.getTaskAnnouncement(userID);
    }

    LiveData<List<Announcement>> getInformationAnnouncement(int userID) {
        return mRepository.getInformationAnnouncement(userID);
    }

    LiveData<List<Announcement>> getFakeNewsAnnouncement(int userID) {
        return mRepository.getFakeNewsAnnouncement(userID);
    }
}
