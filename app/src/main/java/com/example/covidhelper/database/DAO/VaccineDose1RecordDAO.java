package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.VaccineDose1Record;

import java.util.List;

@Dao
public interface VaccineDose1RecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineDose1Record vaccineDose1Record);

    @Query("SELECT * from VaccineDose1Record WHERE userID=:userID")
    LiveData<List<VaccineDose1Record>> getVaccineDose1Record(int userID);
}
