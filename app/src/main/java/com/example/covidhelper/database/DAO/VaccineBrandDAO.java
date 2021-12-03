package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.VaccineBrand;

@Dao
public interface VaccineBrandDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineBrand vaccineBrand);
}
