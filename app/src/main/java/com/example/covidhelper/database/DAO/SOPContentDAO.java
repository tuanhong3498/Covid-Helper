package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.SOPContent;

@Dao
public interface SOPContentDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SOPContent sopContent);

    @Query("SELECT * FROM SOPContent WHERE phaseType = (SELECT phaseType FROM SOP WHERE livingState = :state)")
    SOPContent getSOP(String state);
}
