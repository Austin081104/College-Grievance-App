package com.example.myapplicationp.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationp.R;
import com.google.firebase.auth.FirebaseAuth;

public class user_login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private ImageView togglePassword;
    private Button loginButton;
    private TextView registerButton, forgotPasswordButton;
    private FirebaseAuth auth;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize UI components
        emailEditText = findViewById(R.id.emailText);
        passwordEditText = findViewById(R.id.passwordEditText);
        togglePassword = findViewById(R.id.togglePassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPasswordButton = findViewById(R.id.forgetButton);
        auth = FirebaseAuth.getInstance();

        // Password visibility toggle
        togglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide Password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye_closed);
            } else {
                // Show Password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.photo);
            }
            passwordEditText.setSelection(passwordEditText.length()); // Keep cursor at the end
            isPasswordVisible = !isPasswordVisible;
        });

        // Login button click
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(user_login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(user_login.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(user_login.this, user_page.class));
                    finish();
                } else {
                    Toast.makeText(user_login.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Register button click
        registerButton.setOnClickListener(v -> startActivity(new Intent(user_login.this, user_registration.class)));

        // Forgot password click
        forgotPasswordButton.setOnClickListener(v -> startActivity(new Intent(user_login.this, PasswordActivity.class)));
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
