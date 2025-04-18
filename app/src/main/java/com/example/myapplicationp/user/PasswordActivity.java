package com.example.myapplicationp.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationp.R;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        email = findViewById(R.id.emailTextfr);
        reset = findViewById(R.id.ResetButton);
        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(v -> {
            String emailInput = email.getText().toString();
            if (emailInput.isEmpty()) {
                Toast.makeText(PasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            auth.sendPasswordResetEmail(emailInput).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(PasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PasswordActivity.this, user_login.class));
                } else {
                    Toast.makeText(PasswordActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
