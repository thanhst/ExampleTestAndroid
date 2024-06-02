package com.app.testapp63httt1.testproject.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.testapp63httt1.testproject.DAO.ProductDAO;
import com.app.testapp63httt1.testproject.DAO.UserDAO;
import com.app.testapp63httt1.testproject.Entity.Product;
import com.app.testapp63httt1.testproject.Entity.User;

@Database(entities = {User.class, Product.class}, version = 2)
public abstract class DatabaseRoom extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();

    private static DatabaseRoom databaseRoom;

    public static synchronized DatabaseRoom getInstance(Context context) {
        if (databaseRoom == null) {
            databaseRoom = Room.databaseBuilder(context.getApplicationContext(), DatabaseRoom.class, "TestProject_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseRoom;
    }
}
