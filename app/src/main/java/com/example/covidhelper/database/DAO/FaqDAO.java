package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.covidhelper.database.table.FAQ;

import java.util.List;

@Dao
public interface FaqDAO
{
    @Insert
    void insert(FAQ faq);

    @Query("SELECT * FROM FAQ")
    LiveData<List<FAQ>> getAllFaq();
}
