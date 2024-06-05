package com.app.testapp63httt1.testproject.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carts")
public class Cart {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private int idProduct;

    private int count;
    private String total;
}
