package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "state", unique = true))
public class EmergencyHotline
{
    @PrimaryKey(autoGenerate = true)
    public int emergencyHotlineID;

    public String state;
    public String hotlineNumber;

    public EmergencyHotline(String state, String hotlineNumber)
    {
        this.state = state;
        this.hotlineNumber = hotlineNumber;
    }
}
