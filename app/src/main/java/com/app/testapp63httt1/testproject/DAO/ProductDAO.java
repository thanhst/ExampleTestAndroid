package com.app.testapp63httt1.testproject.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.testapp63httt1.testproject.Entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);
    @Delete
    void deleteProduct(Product product);
    @Query("Delete from products")
    void deleteAllProduct();

    @Query("Select * from products where name = :name")
    LiveData<Product> getProductByName(String name);

    @Query("Select * from products")
    LiveData<List<Product>> getProducts();

    @Query("Select * from products where id = :id")
    LiveData<Product> getProductById(int id);
}
