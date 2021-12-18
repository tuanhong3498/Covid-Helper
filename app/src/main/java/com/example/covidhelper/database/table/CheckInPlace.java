package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"placeID"})})
public class CheckInPlace
{
    @PrimaryKey(autoGenerate = true)
    public int placeID;

    public String recordPlace;
    public String recordAddress;

    public CheckInPlace(String recordPlace, String recordAddress) {
        this.recordPlace = recordPlace;
        this.recordAddress = recordAddress;
    }
}
