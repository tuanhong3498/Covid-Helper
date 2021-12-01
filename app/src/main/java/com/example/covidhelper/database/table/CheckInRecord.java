package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"))
public class CheckInRecord {
    @PrimaryKey(autoGenerate = true)
    public int recordID;

    public int userID;
    public int recordDate;
    public String recordPlace;
    public String recordAddress;
    public String recordTime;

    public CheckInRecord(int userID, int recordDate, String recordPlace, String recordAddress, String recordTime) {
        this.userID = userID;
        this.recordDate = recordDate;
        this.recordPlace = recordPlace;
        this.recordAddress = recordAddress;
        this.recordTime = recordTime;
    }
}
