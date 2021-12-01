package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.Announcement;

import java.util.List;

public interface AnnouncementDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Announcement announcement);

    @Query("SELECT announcementType,announcementTitle,announcementContent, announcementTime from Announcement INNER JOIN User ON Announcement.userID=User.userID")
    List<Announcement> getAllAnnouncement();
}
