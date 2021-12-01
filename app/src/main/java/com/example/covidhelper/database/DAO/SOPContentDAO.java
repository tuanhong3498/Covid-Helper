package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.SOPContent;

public interface SOPContentDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SOPContent sopContent);
}
