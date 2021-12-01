package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.VaccineDose2Record;

public interface VaccineDose2RecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineDose2Record vaccineDose2Record);
}
