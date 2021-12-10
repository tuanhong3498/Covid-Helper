package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
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
    LiveData<VaccinationRecord> getVaccinationRecord(int userID, int dose);

    @Query("UPDATE VaccinationRecord SET vaccinationTime = :newVaccinationTime WHERE userID = :userID and dosage = :dose")
    void updateAppointmentTime(int userID, int dose, long newVaccinationTime);

    @Query("UPDATE VaccinationRecord SET appointmentConfirmed = :appointmentConfirmed WHERE userID = :userID and dosage = :dose")
    void updateVaccinationAppointmentConfirmed(int userID, int dose, boolean appointmentConfirmed);
}
