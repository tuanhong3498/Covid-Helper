package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.DailyVaccineAdministration;

import java.util.List;

@Dao
public interface DailyVaccineAdministrationDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyVaccineAdministration dailyVaccineAdministration);

    @Query("SELECT * FROM DailyVaccineAdministration ORDER BY date DESC LIMIT :days")
    LiveData<List<DailyVaccineAdministration>> getRecentVacData(int days);

    @Query("SELECT SUM(dose1) FROM DailyVaccineAdministration")
    LiveData<Float> getAccumulativeDose1();

    @Query("SELECT SUM(dose2) FROM DailyVaccineAdministration")
    LiveData<Float> getAccumulativeDose2();
}
