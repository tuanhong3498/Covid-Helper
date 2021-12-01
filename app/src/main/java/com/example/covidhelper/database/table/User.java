package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
    public String vaccinationStage;

    public User(String iCNumber, String fullName, String phoneNumber, String email, String livingState, String password, String riskStatus, String vaccinationStage) {
        this.iCNumber = iCNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.livingState = livingState;
        this.password = password;
        this.riskStatus = riskStatus;
        this.vaccinationStage = vaccinationStage;
    }
}
