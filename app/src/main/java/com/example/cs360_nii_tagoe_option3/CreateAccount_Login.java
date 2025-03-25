package com.example.cs360_nii_tagoe_option3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount_Login extends AppCompatActivity {
    DataBase db;
    EditText etUsername, etPassword;
    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout file: activity_main.xml

        db = new DataBase(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Login button logic.
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(CreateAccount_Login.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean valid = db.checkUser(username, password);
                if(valid){
                    Toast.makeText(CreateAccount_Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Proceed to MainActivity.
                    Intent intent = new Intent(CreateAccount_Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateAccount_Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sign-up button logic.
        btnSignUp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(CreateAccount_Login.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = db.insertUser(username, password);
                if(inserted){
                    Toast.makeText(CreateAccount_Login.this, "Account Created, please log in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccount_Login.this, "User already exists or error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
