package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.VaccineDose1Record;

public interface VaccineDose1RecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineDose1Record vaccineDose1Record);
}
