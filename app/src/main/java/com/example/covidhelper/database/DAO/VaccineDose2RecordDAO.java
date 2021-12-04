package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.VaccineDose1Record;
import com.example.covidhelper.database.table.VaccineDose2Record;

import java.util.List;

@Dao
public interface VaccineDose2RecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineDose2Record vaccineDose2Record);

    @Query("SELECT * from VaccineDose2Record WHERE userID=:userID")
    LiveData<List<VaccineDose2Record>> getVaccineDose2Record(int userID);
}
