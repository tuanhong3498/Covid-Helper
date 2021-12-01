package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.CheckInRecord;

public interface CheckInRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CheckInRecord checkInRecord);
}
