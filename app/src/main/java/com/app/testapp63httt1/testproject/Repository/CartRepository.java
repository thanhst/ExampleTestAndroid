package com.app.testapp63httt1.testproject.Repository;

import android.content.pm.ApplicationInfo;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import android.app.Application;

import com.app.testapp63httt1.testproject.DAO.CartDAO;
import com.app.testapp63httt1.testproject.Database.DatabaseRoom;
import com.app.testapp63httt1.testproject.Entity.Cart;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepository {
    private DatabaseRoom databaseRoom;
    private CartDAO cartDAO;
    private ExecutorService executorService;

    public CartRepository(Application application) {
        this.databaseRoom = DatabaseRoom.getInstance(application);
        this.cartDAO = databaseRoom.cartDAO();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void insertCart(Cart cart) {
        executorService.execute(() -> {
            cartDAO.insertCart(cart);
        });
    }

    public void deleteAllCartOfUser(String username) {
        executorService.execute(() -> {
            cartDAO.deleteAllCartOfUser(username);
        });
    }
    public void deleteCartByUsernameAndCountLikeZero(String username){
        executorService.execute(() -> {
            cartDAO.deleteCartByUsernameAndCountLikeZero(username);
        });
    }
    public void upCountCart(String username,int id){
        executorService.execute(() -> {
            cartDAO.upCountCart(username,id);
        });
    }
    public void downCountCart(String username,int id){
        executorService.execute(() -> {
            cartDAO.downCountCart(username,id);
        });
    }
    public LiveData<List<Cart>> getCartByUsername(String username){
        return cartDAO.getCartByUsername(username);
    }
    public void updateTotal(float price ,int id){
        executorService.execute(() -> {
            cartDAO.updateTotal(price,id);
        });
    }

    public LiveData<Cart> checkProduct(int idProduct,String username){
        return cartDAO.getCartByIdProductAndUsername(idProduct,username);
    }
}
