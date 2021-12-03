package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.covidhelper.database.table.VaccinationRecord;

@Dao
public interface VaccinationRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccinationRecord vaccinationRecord);

    @Update
    void update(VaccinationRecord vaccinationRecord);

    @Query("SELECT * FROM VaccinationRecord WHERE userID = :userID and dosage = :dose")
    VaccinationRecord getVaccinationRecord(int userID, int dose);
}
