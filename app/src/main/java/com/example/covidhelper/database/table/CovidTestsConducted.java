package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CovidTestsConducted
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int date;
    public float rtk;
    public float pcr;
    public float positiveRate;

    public CovidTestsConducted(int date, float rtk, float pcr, float positiveRate) {
        this.date = date;
        this.rtk = rtk;
        this.pcr = pcr;
        this.positiveRate = positiveRate;
    }
}
