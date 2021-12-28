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

    @Query("SELECT COUNT(*) from Announcement WHERE Announcement.userID=:userID AND announcementType = :announcementType AND isRead = 0")
    LiveData<Integer> getAnnouncementNumber(int userID, String announcementType);

    @Query("SELECT COUNT(*) from Announcement WHERE Announcement.userID=:userID AND isRead = 0")
    LiveData<Integer> getAnnouncementNumberInAll(int userID);

    @Query("UPDATE Announcement SET isRead = 1 WHERE announcementID = :announcementID AND userID = :userID")
    void updateIsRead(int userID, int announcementID);
}
