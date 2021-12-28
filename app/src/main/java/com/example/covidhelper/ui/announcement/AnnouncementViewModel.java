package com.example.covidhelper.ui.announcement;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.table.Announcement;

import java.util.List;

public class AnnouncementViewModel extends AndroidViewModel
{
    private final AnnouncementRepository mRepository;

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

    LiveData<Integer> getAnnouncementNumber(int userID, String announcementType) {
        return mRepository.getAnnouncementNumber(userID, announcementType);
    }

    LiveData<Integer> getAnnouncementNumberInAll(int userID) {
        return mRepository.getAnnouncementNumberInAll(userID);
    }

    void updateIsRead(int userID, int announcementID) {
        mRepository.updateIsRead(userID, announcementID);
    }
}
