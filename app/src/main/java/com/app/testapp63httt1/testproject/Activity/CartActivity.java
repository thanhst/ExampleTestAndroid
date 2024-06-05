package com.app.testapp63httt1.testproject.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.testapp63httt1.testproject.Adapter.CartAdapter;
import com.app.testapp63httt1.testproject.Entity.Cart;
import com.app.testapp63httt1.testproject.Entity.User;
import com.app.testapp63httt1.testproject.R;
import com.app.testapp63httt1.testproject.Repository.CartRepository;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private User user;
    private ImageView settings;
    private ImageView back;
    private RecyclerView recyclerView;
    private CartRepository cartRepository;
    private TextView totalReal;
    private Button clearCart;
    private Button calculateCart;
    private EditText sale;
    private TextView percenSale;
    private TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cartRepository = new CartRepository(getApplication());
        recyclerView=findViewById(R.id.recycleviewCart);
        settings = findViewById(R.id.settings);
        back=findViewById(R.id.back);
        totalReal = findViewById(R.id.totalReal);
        clearCart = findViewById(R.id.deleteCart);
        calculateCart = findViewById(R.id.calcuteTotal);
        total = findViewById(R.id.totalReal);
        sale= findViewById(R.id.sale);
        percenSale = findViewById(R.id.percenSale);


        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartRepository.getCartByUsername(user.getUsername()).observe(CartActivity.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if(carts.size()!=0){
                    recyclerView.setAdapter(new CartAdapter(getApplication(),carts));
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSettings(CartActivity.this);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cartRepository.getTotal(user.getUsername(),CartActivity.this).observe(CartActivity.this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                totalReal.setText(String.valueOf(aFloat)+"$");
            }
        });

        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartRepository.deleteAllCartOfUser(user.getUsername());
            }
        });

        calculateCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }
    public void DialogSettings(Context context){
        try {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.setting);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.MATCH_PARENT );
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributesribute = window.getAttributes();
            windowAttributesribute.gravity= Gravity.LEFT;
            window.setAttributes(windowAttributesribute);
            dialog.setCancelable(true);

            TextView fullname = dialog.findViewById(R.id.fullname);
            fullname.setText(user.getFullName());
            TextView username = dialog.findViewById(R.id.username);
            username.setText(user.getUsername());
            TextView createDate = dialog.findViewById(R.id.createDate);
            createDate.setText(user.getCreateDate());
            TextView role = dialog.findViewById(R.id.role);
            role.setText(user.getRole());

            Button btnLogOut = dialog.findViewById(R.id.logout);
            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CartActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open settings dialog", Toast.LENGTH_SHORT).show();
        }
    }
}