package com.example.myapplicationp.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplicationp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class user_registration extends AppCompatActivity {
    EditText name, email, password, confirmpassword, phone;
    Button btnRegister;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.cpassword);
        phone = findViewById(R.id.phone);
        btnRegister = findViewById(R.id.btn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String ConfirmPassword = confirmpassword.getText().toString();
                String Phone = phone.getText().toString();

                if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty() || Phone.isEmpty()) {
                    Toast.makeText(user_registration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(Email)) {
                    Toast.makeText(user_registration.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidMobile(Phone)) {
                    Toast.makeText(user_registration.this, "Please enter a valid 10-digit mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!Password.equals(ConfirmPassword)) {
                    Toast.makeText(user_registration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", Name);
                            userData.put("email", Email);
                            userData.put("phone", Phone);

                            db.collection("users").document(user.getUid()).set(userData).addOnSuccessListener(aVoid -> {
                                Toast.makeText(user_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(user_registration.this, user_login.class));
                                finish();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(user_registration.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                            });
                        }
                    } else {
                        Toast.makeText(user_registration.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{10}");
    }
}
