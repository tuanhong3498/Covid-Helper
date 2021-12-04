package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.Announcement;

import java.util.List;

@Dao
public interface AnnouncementDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Announcement announcement);

    @Query("SELECT * from Announcement WHERE Announcement.userID=:userID")
    LiveData<List<Announcement>> getAllAnnouncement(int userID);

    @Query("SELECT * from Announcement WHERE Announcement.userID=:userID AND announcementType = :announcementType")
    LiveData<List<Announcement>> getTypeAnnouncement(int userID, String announcementType);
}
