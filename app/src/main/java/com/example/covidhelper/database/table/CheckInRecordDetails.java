package com.example.covidhelper.database.table;

import androidx.room.PrimaryKey;

public class CheckInRecordDetails
{
    @PrimaryKey
    public int recordID;

    public int userID;
    public String recordDate;
    public String recordPlace;
    public String recordAddress;
    public int recordTime;

    public CheckInRecordDetails (int userID, String recordDate, String recordPlace, String recordAddress, int recordTime) {
        this.userID = userID;
        this.recordDate = recordDate;
        this.recordPlace = recordPlace;
        this.recordAddress = recordAddress;
        this.recordTime = recordTime;
    }
}
