package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"),
        indices = {@Index(value = {"userID"})})
public class VaccineRegistrationRecord {
    @PrimaryKey(autoGenerate = true)
    public int vaccineRegistrationRecordID;

    public int userID;
    public String state;
    public String postcode;

    public VaccineRegistrationRecord(int userID, String state, String postcode) {
        this.userID = userID;
        this.state = state;
        this.postcode = postcode;
    }
}
