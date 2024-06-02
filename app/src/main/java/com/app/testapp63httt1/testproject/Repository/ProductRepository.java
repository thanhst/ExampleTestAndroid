package com.app.testapp63httt1.testproject.Repository;

import com.app.testapp63httt1.testproject.DAO.ProductDAO;
import com.app.testapp63httt1.testproject.Database.DatabaseRoom;
import com.app.testapp63httt1.testproject.Entity.Product;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private DatabaseRoom databaseRoom;
    private ProductDAO productDAO;
    private ExecutorService dataserExecutorService;

    public ProductRepository(Application application){
        databaseRoom = DatabaseRoom.getInstance(application);
        productDAO = databaseRoom.productDAO();
        dataserExecutorService = Executors.newSingleThreadExecutor();
    }
    public void insertProduct(Product product){
        dataserExecutorService.execute(()->{
            productDAO.insertProduct(product);
        });
    }
    public LiveData<Product> getProductByName(String name){
        return productDAO.getProductByName(name);
    }
    public LiveData<List<Product>> getProducts(){
        return productDAO.getProducts();
    }
}