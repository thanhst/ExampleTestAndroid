package com.app.testapp63httt1.testproject.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.app.testapp63httt1.testproject.Entity.User;
import com.app.testapp63httt1.testproject.R;
import com.app.testapp63httt1.testproject.Repository.UserRepository;

public class RegisterAcitivity extends AppCompatActivity {
    private TextView loginPage;
    private EditText username;
    private EditText fullname;
    private EditText password;
    private RadioButton employee;
    private RadioButton customer;
    private RadioButton active;
    private RadioButton inActive;
    private Button registerButton;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_acitivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userRepository = new UserRepository(getApplication());
        registerButton = findViewById(R.id.RegisterButton);
        username = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassworld);
        fullname = findViewById(R.id.fullNameEditText);
        employee = findViewById(R.id.RoleEmployee);
        customer = findViewById(R.id.RoleCustomer);
        active = findViewById(R.id.StatusActive);
        inActive = findViewById(R.id.StatusInActive);

        loginPage = findViewById(R.id.Login);
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                        || fullname.getText().toString().isEmpty() || (!employee.isChecked() && !customer.isChecked())
                        || (!active.isChecked() && !inActive.isChecked())) {
                    Toast.makeText(RegisterAcitivity.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else {
                    User userPut = new User();
                    userPut.setFullName(fullname.getText().toString());
                    userPut.setUsername(username.getText().toString());
                    userPut.setPassword(password.getText().toString());
                    if (employee.isChecked()) {
                        userPut.setRole(employee.getText().toString());
                    } else {
                        userPut.setRole(customer.getText().toString());
                    }
                    if (active.isChecked()) {
                        userPut.setStatus(active.getText().toString());
                    } else {
                        userPut.setStatus(inActive.getText().toString());
                    }
                    final boolean[] isInsertDone = {false};
                    try {
                        userRepository.getUserByUsername(username.getText().toString()).observe(RegisterAcitivity.this, new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                if (isInsertDone[0]) {
                                    return;
                                }
                                if(user == null){
                                    userRepository.insertUser(userPut);
                                    isInsertDone[0] =true;
                                    Toast.makeText(RegisterAcitivity.this, "Added successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(RegisterAcitivity.this, "Error, username had been used!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        userRepository.getUserByUsername(username.getText().toString()).removeObservers(RegisterAcitivity.this);
                    } catch (Exception e) {
                        Toast.makeText(RegisterAcitivity.this, "Error, please check your connect!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}