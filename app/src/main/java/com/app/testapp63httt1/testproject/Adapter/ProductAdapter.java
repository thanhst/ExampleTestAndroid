package com.app.testapp63httt1.testproject.Adapter;

import android.app.Application;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.app.testapp63httt1.testproject.Entity.Cart;
import com.app.testapp63httt1.testproject.Entity.Product;
import com.app.testapp63httt1.testproject.Entity.User;
import com.app.testapp63httt1.testproject.R;
import com.app.testapp63httt1.testproject.Repository.CartRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private CartRepository cartRepository;
    private User user;
    public ProductAdapter(List<Product> productList, Application application, User user){
        this.productList = productList;
        this.cartRepository = new CartRepository(application);
        this.user = user;
    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] checked = {false};
                cartRepository.checkProduct(product.getId(), user.getUsername()).observeForever(new Observer<Cart>() {
                    @Override
                    public void onChanged(Cart cart) {
                        if(checked[0]==true){
                            return;
                        }
                        if(cart == null){
                            Cart cart1= new Cart();
                            cart1.setCount(1);
                            cart1.setIdProduct(product.getId());
                            cart1.setTotal(String.valueOf(1*product.getPrice()));
                            cart1.setUsername(user.getUsername());
                            cartRepository.insertCart(cart1);
                            checked[0]=true;
                            Toast.makeText(v.getContext(),"Added!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            cartRepository.upCountCart(user.getUsername(),cart.getId());
                            cartRepository.updateTotal(product.getPrice(),cart.getId());
                            Toast.makeText(v.getContext(),"Updated!",Toast.LENGTH_SHORT).show();
                            checked[0]=true;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView productName;
        private TextView productPrice;
        private TextView productDescription;
        private ImageView productImage;
        private FloatingActionButton addProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName= itemView.findViewById(R.id.productName);
            productPrice= itemView.findViewById(R.id.productPrice);
            productDescription= itemView.findViewById(R.id.productDescription);
            productImage= itemView.findViewById(R.id.productImage);
            addProduct = itemView.findViewById(R.id.addProduct);
        }
        public void bind(Product product){
            productName.setText(product.getName().toString());
            productPrice.setText(product.getPrice().toString() + "$");
            productDescription.setText(product.getDescription().toString());
            productImage.setImageURI(Uri.parse(product.getImagePath()));
        }
    }
}
