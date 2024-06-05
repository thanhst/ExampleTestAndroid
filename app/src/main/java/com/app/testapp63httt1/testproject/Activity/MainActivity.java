package com.app.testapp63httt1.testproject.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView settings;
    private User user;


    @SuppressLint("MissingInflatedId")
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
        user = (User) bundle.getSerializable("user");


        productRepository = new ProductRepository(getApplication());
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        createDate = findViewById(R.id.createDate);
        role = findViewById(R.id.role);
        myCart = findViewById(R.id.myCart);
        recyclerView = findViewById(R.id.recycleViewProduct);
        settings=findViewById(R.id.settings);

        username.setText(user.getUsername());
        fullname.setText(user.getFullName());
        createDate.setText("Create date: "+user.getCreateDate());
        role.setText("Role: "+user.getRole());
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        Boolean initdata = sharedPreferences.getBoolean("initData",false);
        if(initdata==false){
            productRepository.initData(getApplication());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("initData",true);
            editor.apply();
        }
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        productRepository.getProducts().observe(MainActivity.this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                recyclerView.setAdapter(new ProductAdapter(products,getApplication(),user));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSettings(MainActivity.this);
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);
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
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
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