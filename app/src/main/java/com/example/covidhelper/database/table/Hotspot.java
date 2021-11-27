package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hotspot {
    @PrimaryKey(autoGenerate = true)
    public int caseRecordID;

    public String PlaceName;
    public int caseNumber;
    public double latitude;
    public double longitude;

    public Hotspot(String PlaceName, int caseNumber, double latitude, double longitude) {
        this.PlaceName = PlaceName;
        this.caseNumber = caseNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
