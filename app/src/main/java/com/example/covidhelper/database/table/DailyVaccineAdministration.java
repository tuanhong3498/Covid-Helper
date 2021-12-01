package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DailyVaccineAdministration
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int date;
    public float dose1;
    public float dose2;
    public float dosagesAvg;

    public DailyVaccineAdministration(int date, float dose1, float dose2, float dosagesAvg) {
        this.date = date;
        this.dose1 = dose1;
        this.dose2 = dose2;
        this.dosagesAvg = dosagesAvg;
    }
}
