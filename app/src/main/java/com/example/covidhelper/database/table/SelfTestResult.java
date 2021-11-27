package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"))
public class SelfTestResult {
    @PrimaryKey(autoGenerate = true)
    public int testID;

    public int userID;
    public String testResult;

    public SelfTestResult(int userID, String testResult) {
        this.userID = userID;
        this.testResult = testResult;
    }
}
