package com.app.testapp63httt1.testproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.app.testapp63httt1.testproject.Activity.CartActivity;
import com.app.testapp63httt1.testproject.Database.DatabaseRoom;
import com.app.testapp63httt1.testproject.Entity.Cart;
import com.app.testapp63httt1.testproject.Entity.Product;
import com.app.testapp63httt1.testproject.R;
import com.app.testapp63httt1.testproject.Repository.CartRepository;
import com.app.testapp63httt1.testproject.Repository.ProductRepository;
import com.app.testapp63httt1.testproject.Repository.UserRepository;
import android.app.Application;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> carts;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private Context context;

    public CartAdapter(Application application,List<Cart> carts){
        this.productRepository=new ProductRepository(application);
        this.cartRepository = new CartRepository(application);
        this.carts =carts;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart,parent,false);
        return new ViewHolder(itemView,productRepository);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.bind(cart);
        productRepository.getProductById(cart.getIdProduct()).observe((LifecycleOwner) context,new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                holder.upCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartRepository.upCountCart(cart.getUsername(), cart.getId());
                        holder.productCount.setText(String.valueOf(cart.getCount()));
                        cartRepository.updateTotal(product.getPrice(),cart.getId());
                    }
                });
                holder.downCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cart.getCount()>0){
                            cartRepository.downCountCart(cart.getUsername(), cart.getId());
                            holder.productCount.setText(String.valueOf(cart.getCount()));
                            cartRepository.updateTotal(product.getPrice(),cart.getId());
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageProductCart;
        private TextView productName;
        private TextView productPrice;
        private ImageView upCount;
        private ImageView downCount;
        private EditText productCount;
        private TextView productTotal;
        private ProductRepository productRepository;
        public ViewHolder(@NonNull View itemView,ProductRepository productRepository) {
            super(itemView);
            imageProductCart = itemView.findViewById(R.id.imageProductCart);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            upCount = itemView.findViewById(R.id.upCount);
            downCount = itemView.findViewById(R.id.downCount);
            productCount = itemView.findViewById(R.id.productCount);
            productTotal = itemView.findViewById(R.id.productTotal);
            this.productRepository = productRepository;
        }
        public void bind(Cart cart){
            productRepository.getProductById(cart.getIdProduct()).observe((LifecycleOwner) context,new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    if(product!=null){
                        productName.setText(product.getName());
                        productPrice.setText(String.valueOf(product.getPrice())+"$");
                        productCount.setText(String.valueOf(cart.getCount()));
                        productTotal.setText(cart.getTotal()+"$");
                        Log.d("product","Not null");
                    }
                    else{
                        Log.d("product","Null");
                    }
                }
            });
        }
    }
}
