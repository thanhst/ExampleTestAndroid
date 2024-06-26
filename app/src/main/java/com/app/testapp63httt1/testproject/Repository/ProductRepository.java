package com.app.testapp63httt1.testproject.Repository;

import com.app.testapp63httt1.testproject.DAO.ProductDAO;
import com.app.testapp63httt1.testproject.Database.DatabaseRoom;
import com.app.testapp63httt1.testproject.Entity.Product;
import com.app.testapp63httt1.testproject.R;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private DatabaseRoom databaseRoom;
    private ProductDAO productDAO;
    private ExecutorService dataserExecutorService;
    private Application app;

    public ProductRepository(Application application){
        app = application;
        databaseRoom = DatabaseRoom.getInstance(app);
        productDAO = databaseRoom.productDAO();
        dataserExecutorService = Executors.newSingleThreadExecutor();
    }
    public void insertProduct(Product product){
        dataserExecutorService.execute(()->{
            productDAO.insertProduct(product);
        });
    }
    public void deleteAllProduct(){
        dataserExecutorService.execute(()->{
            productDAO.deleteAllProduct();
        });
    }
    public LiveData<Product> getProductByName(String name){
        return productDAO.getProductByName(name);
    }
    public LiveData<List<Product>> getProducts(){
        return productDAO.getProducts();
    }

    public void initData(Application application){
        deleteAllProduct();
        for(int i =1;i<11;i++){
            Product product = new Product();
            product.setId(i);
            product.setName("Sản phẩm " +i);
            product.setDescription("sản phẩm mô tả " +i);
            product.setPrice((float)i);
            String uri = "android.resource://"+application.getPackageName()+"/"+ R.drawable.cafe;
            product.setImagePath(uri);
            insertProduct(product);
        }
    }
    public LiveData<Product> getProductById(int id){
        return productDAO.getProductById(id);
    }


}
