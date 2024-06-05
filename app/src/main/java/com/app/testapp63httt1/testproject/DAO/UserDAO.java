package com.app.testapp63httt1.testproject.DAO;

import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.testapp63httt1.testproject.Entity.User;

@Dao
public interface UserDAO{
    @Insert
    void insertUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("Delete from users")
    void deleteAllUser();
    @Query("Select * from users where username = :username")
    LiveData<User> getUserByUsername(String username);
}
