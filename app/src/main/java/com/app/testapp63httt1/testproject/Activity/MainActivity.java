package com.app.testapp63httt1.testproject.Activity;

import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.testapp63httt1.testproject.Adapter.ProductAdapter;
import com.app.testapp63httt1.testproject.Entity.Product;
import com.app.testapp63httt1.testproject.Entity.User;
import com.app.testapp63httt1.testproject.R;
import com.app.testapp63httt1.testproject.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView username;
    private TextView fullname;
    private TextView createDate;
    private TextView role;
    private ImageView myCart;
    private RecyclerView recyclerView;
    private ProductRepository productRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = new Bundle();
        bundle = getIntent().getBundleExtra("bundle_data");
        User user = (User) bundle.getSerializable("user");

        productRepository = new ProductRepository(getApplication());
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        createDate = findViewById(R.id.createDate);
        role = findViewById(R.id.role);
        myCart = findViewById(R.id.myCart);
        recyclerView = findViewById(R.id.recycleViewProduct);

        username.setText(user.getUsername());
        fullname.setText(user.getFullName());
        createDate.setText("Create date: "+user.getCreateDate());
        role.setText("Role: "+user.getRole());
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        for(int i =1;i<11;i++){
            Product product = new Product();
            product.setName("Tên: Sản phẩm " +i);
            product.setDescription("Mô tả: sản phẩm mô tả " +i);
            product.setPrice((float)i);
            String uri = "android.resource://"+getPackageName()+"/"+R.drawable.cafe;
            product.setImagePath(uri);
            productRepository.insertProduct(product);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        productRepository.getProducts().observe(MainActivity.this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                recyclerView.setAdapter(new ProductAdapter(products));
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);
    }
}