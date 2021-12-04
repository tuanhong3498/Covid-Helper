package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.User;

import java.util.List;

@Dao
public interface UserDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM User WHERE iCNumber = :iCNumber AND password = :password ORDER BY userID DESC")
    LiveData<List<User>> getCertainUser(String iCNumber, String password);

    @Query("SELECT * FROM User WHERE userID = :userID")
    LiveData<List<User>> getUserInfo(int userID);
}
