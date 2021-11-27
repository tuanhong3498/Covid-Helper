package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.SOP;

public interface SOPDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SOP sop);
}
