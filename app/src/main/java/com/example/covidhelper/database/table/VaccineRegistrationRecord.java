package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"))
public class VaccineRegistrationRecord {
    @PrimaryKey(autoGenerate = true)
    public int vaccineRegistrationRecordID;

    public int userID;
    public String state;
    public String postCode;

    public VaccineRegistrationRecord(int userID, String state, String postCode) {
        this.userID = userID;
        this.state = state;
        this.postCode = postCode;
    }
}
