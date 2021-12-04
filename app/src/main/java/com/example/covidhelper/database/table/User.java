package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = SOP.class,
                        parentColumns = "livingState",
                        childColumns = "livingState"),
indices = {@Index(value = {"userID"},unique = true), @Index("livingState")})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userID;

    public String iCNumber;
    public String fullName;
    public String phoneNumber;
    public String email;
    public String livingState;
    public String password;
    public String riskStatus;
    public String symptomStatus;
    public String vaccinationStage;

    public User(String iCNumber, String fullName, String phoneNumber, String email, String livingState, String password, String riskStatus, String symptomStatus, String vaccinationStage) {
        this.iCNumber = iCNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.livingState = livingState;
        this.password = password;
        this.riskStatus = riskStatus;
        this.symptomStatus = symptomStatus;
        this.vaccinationStage = vaccinationStage;
    }
}
