package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DailyNewDeaths
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int date;
    public float newDeath;
    public float newDeathAvg;

    public DailyNewDeaths(int date, float newDeath, float newDeathAvg) {
        this.date = date;
        this.newDeath = newDeath;
        this.newDeathAvg = newDeathAvg;
    }
}
