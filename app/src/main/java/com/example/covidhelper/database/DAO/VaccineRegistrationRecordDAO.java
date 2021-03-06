package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.covidhelper.database.table.VaccineRegistrationRecord;

@Dao
public interface VaccineRegistrationRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VaccineRegistrationRecord vaccineRegistrationRecord);

    @Query("SELECT * FROM VaccineRegistrationRecord WHERE userID = :userID")
    LiveData<VaccineRegistrationRecord>  getRegistrationRecord(int userID);

    @Query("SELECT * FROM VaccineRegistrationRecord WHERE userID = :userID")
    VaccineRegistrationRecord checkRegistrationRecord(int userID);

    @Update
    void update(VaccineRegistrationRecord vaccineRegistrationRecord);

    @Query("UPDATE VaccineRegistrationRecord SET state = :state, postcode = :postcode WHERE userID = :userID")
    void updateRegistrationRecord(int userID, String state, String postcode);
}
