package com.app.testapp63httt1.testproject.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.testapp63httt1.testproject.Entity.Cart;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insertCart(Cart cart);
    @Delete
    void deleteCart(Cart cart);
    @Query("Delete from carts where username = :username")
    void deleteAllCartOfUser(String username);
    @Query("Delete from carts where username = :username and count =0")
    void deleteCartByUsernameAndCountLikeZero(String username);
    @Query("Select * from carts where username = :username")
    LiveData<List<Cart>> getCartByUsername(String username);
    @Query("Update carts set count = count+1 where username= :username and id = :id")
    void upCountCart(String username, int id);
    @Query("Update carts set count = count-1 where username= :username and id = :id")
    void downCountCart(String username, int id);
    @Query("Update carts set total = count * :price where id= :id")
    void updateTotal(float price,int id);

    @Query("Select * from carts where idProduct = :idProduct and username= :username")
    LiveData<Cart> getCartByIdProductAndUsername(int idProduct, String username);
}
