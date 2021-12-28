package com.example.covidhelper.ui.announcement;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

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

    LiveData<Integer> getAnnouncementNumber(int userID, String announcementType) {
        return announcementDAO.getAnnouncementNumber(userID, announcementType);
    }

    LiveData<Integer> getAnnouncementNumberInAll(int userID) {
        return announcementDAO.getAnnouncementNumberInAll(userID);
    }

    void updateIsRead(int userID, int announcementID) {
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> announcementDAO.updateIsRead(userID, announcementID));
    }

}
