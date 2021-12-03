package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "vaccineName", unique = true))
public class VaccineBrand
{
    @PrimaryKey(autoGenerate = false)
    public int vaccineBrandID;

    public String vaccineName;
    public String manufacturer;


    public VaccineBrand(int vaccineBrandID, String vaccineName, String manufacturer)
    {
        this.vaccineBrandID = vaccineBrandID;
        this.vaccineName = vaccineName;
        this.manufacturer = manufacturer;
    }
}
