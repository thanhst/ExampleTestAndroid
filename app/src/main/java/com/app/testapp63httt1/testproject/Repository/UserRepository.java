package com.app.testapp63httt1.testproject.Repository;

import com.app.testapp63httt1.testproject.DAO.UserDAO;
import com.app.testapp63httt1.testproject.Database.DatabaseRoom;
import com.app.testapp63httt1.testproject.Entity.User;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDAO userDAO;
    private DatabaseRoom databaseRoom;
    private ExecutorService dataserExecutorService;

    public UserRepository(Application application) {
        databaseRoom = DatabaseRoom.getInstance(application);
        userDAO = databaseRoom.userDAO();
        dataserExecutorService = Executors.newSingleThreadExecutor();
    }

    public void insertUser(User user) {
        dataserExecutorService.execute(() -> {
                    userDAO.insertUser(user);
                }
        );
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
