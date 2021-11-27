package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DailyNewCases
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int date;
    public float newCases;
    public float newCasesAvg;

    public DailyNewCases(int date, float newCases, float newCasesAvg) {
        this.date = date;
        this.newCases = newCases;
        this.newCasesAvg = newCasesAvg;
    }
}
