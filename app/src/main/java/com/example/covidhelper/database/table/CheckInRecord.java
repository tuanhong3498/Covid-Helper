package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"),
        @ForeignKey(entity = CheckInPlace.class,
                parentColumns = "placeID",
                childColumns = "placeID")
},
        indices = {@Index(value = {"userID"}),@Index(value = {"placeID"})})
public class CheckInRecord {
    @PrimaryKey(autoGenerate = true)
    public int recordID;

    public int userID;
    public String recordDate;
    public int recordTime;
    public int placeID;

    public CheckInRecord(int userID, String recordDate, int recordTime, int placeID) {
        this.userID = userID;
        this.recordDate = recordDate;
        this.recordTime = recordTime;
        this.placeID = placeID;
    }
}
