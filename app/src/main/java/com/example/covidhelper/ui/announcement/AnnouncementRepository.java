package com.example.covidhelper.ui.announcement;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.AnnouncementDAO;
import com.example.covidhelper.database.table.Announcement;

import java.util.List;

public class AnnouncementRepository
{
    private final AnnouncementDAO announcementDAO;

    AnnouncementRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        announcementDAO = covidHelperDatabase.getAnnouncementDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Announcement>> getAllAnnouncement(int userID) {
        return announcementDAO.getAllAnnouncement(userID);
    }

    LiveData<List<Announcement>> getTaskAnnouncement(int userID) {
        return announcementDAO.getTypeAnnouncement(userID,"task");
    }

    LiveData<List<Announcement>> getInformationAnnouncement(int userID) {
        return announcementDAO.getTypeAnnouncement(userID,"information");
    }

    LiveData<List<Announcement>> getFakeNewsAnnouncement(int userID) {
        return announcementDAO.getTypeAnnouncement(userID,"fake news");
    }
}
