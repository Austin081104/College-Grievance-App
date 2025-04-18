package com.example.myapplicationp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplicationp.admin.admin_login;
import com.example.myapplicationp.user.user_login;

public class MainActivity extends AppCompatActivity {
    private Button adminButton;
    private Button userButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminButton = findViewById(R.id.adminButton);
        userButton = findViewById(R.id.userButton);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, admin_login.class);
                startActivity(intent);
            }
        });

        // User button click listener
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For now, show a Toast message (replace this with activity navigation if needed)
                Intent intent = new Intent(MainActivity.this,  user_login.class);
                startActivity(intent);
            }
        });

    }
}