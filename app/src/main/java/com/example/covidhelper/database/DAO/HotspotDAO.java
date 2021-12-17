package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.Hotspot;

import java.util.List;

@Dao
public interface HotspotDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Hotspot hotspot);

    @Query("SELECT * FROM Hotspot")
    LiveData<List<Hotspot>> getHotspotData();
}
