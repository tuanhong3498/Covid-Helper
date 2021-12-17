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

    @Query("SELECT count(*) FROM User WHERE iCNumber = :iCNumber")
    LiveData<Integer> checkUniquenessOfIC(String iCNumber);

    @Query("UPDATE User SET symptomStatus= :symptomStatus WHERE userID = :userID")
    void  updateSymptomStatus(String symptomStatus,int userID);

    @Query("UPDATE User SET livingState = :state WHERE userID = :userID")
    void  updateUserState(String state,int userID);

    @Query("UPDATE User SET phoneNumber = :phone WHERE userID = :userID")
    void  updateUserPhone(String phone,int userID);

    @Query("UPDATE User SET email = :email WHERE userID = :userID")
    void  updateUserEmail(String email,int userID);

    @Query("UPDATE User SET password = :password WHERE userID = :userID")
    void  updateUserPassword(String password,int userID);
}
