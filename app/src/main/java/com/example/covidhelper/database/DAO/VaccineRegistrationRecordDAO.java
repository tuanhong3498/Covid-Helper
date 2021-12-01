package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.VaccineRegistrationRecord;

public interface VaccineRegistrationRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineRegistrationRecord vaccineRegistrationRecord);
}
