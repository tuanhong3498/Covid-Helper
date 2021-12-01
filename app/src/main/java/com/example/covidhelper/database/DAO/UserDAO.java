package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.User;

public interface UserDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
}