package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"),
        indices = {@Index(value = {"userID"})})
public class Announcement {
    @PrimaryKey(autoGenerate = true)
    public int announcementID;

    public int userID;
    public String announcementType;
    public String announcementTitle;
    public String announcementContent;
    public String announcementImage;
    public int announcementTime;
    public boolean isRead;

    public Announcement(int userID, String announcementType, String announcementTitle, String announcementContent, String announcementImage, int announcementTime) {
        this.userID = userID;
        this.announcementType = announcementType;
        this.announcementTitle = announcementTitle;
        this.announcementContent = announcementContent;
        this.announcementImage = announcementImage;
        this.announcementTime = announcementTime;
        this.isRead = false;
    }
}
