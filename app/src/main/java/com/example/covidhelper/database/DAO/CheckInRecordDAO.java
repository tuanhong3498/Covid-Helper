package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.CheckInRecord;

import java.util.List;

@Dao
public interface CheckInRecordDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CheckInRecord checkInRecord);

    @Query("SELECT * from (SELECT * from CheckInRecord WHERE CheckInRecord.userID=:userID ORDER BY CheckInRecord.recordTime DESC) t GROUP BY t.recordDate ORDER BY t.recordDate DESC")
    LiveData<List<CheckInRecord>> getCheckInDate(int userID);
}
