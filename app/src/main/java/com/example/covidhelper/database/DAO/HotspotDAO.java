package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.Hotspot;

@Dao
public interface HotspotDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Hotspot hotspot);
}
