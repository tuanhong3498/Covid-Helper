package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.VaccinationRecord;

@Dao
public interface VaccinationRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccinationRecord vaccinationRecord);
}
