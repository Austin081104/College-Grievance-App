package com.example.myapplicationp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class admin_edit_user extends AppCompatActivity {
    DrawerLayout drawerLayout;
    EditText name, email, phone, password;
    Button btnInsert, btnUpdate, btnDelete;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        drawerLayout = findViewById(R.id.drawer_layout);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        phone = findViewById(R.id.editPhone);
        password = findViewById(R.id.editPassword);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = FirebaseFirestore.getInstance();

        btnInsert.setOnClickListener(v -> insertUser());
        btnUpdate.setOnClickListener(v -> updateUser());
        btnDelete.setOnClickListener(v -> deleteUser());
    }

    private void insertUser() {
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Phone = phone.getText().toString();
        String Password = password.getText().toString();

        if (Name.isEmpty() || Email.isEmpty() || Phone.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("name", Name);
        user.put("email", Email);
        user.put("phone", Phone);
        user.put("password", Password);

        db.collection("users").document(Email).set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "User inserted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT).show());
    }

    private void updateUser() {
        String Email = email.getText().toString();

        if (Email.isEmpty()) {
            Toast.makeText(this, "Enter email to update", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        if (!name.getText().toString().isEmpty()) updates.put("name", name.getText().toString());
        if (!phone.getText().toString().isEmpty()) updates.put("phone", phone.getText().toString());
        if (!password.getText().toString().isEmpty()) updates.put("password", password.getText().toString());

        db.collection("users").document(Email).update(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
    }

    private void deleteUser() {
        String Email = email.getText().toString();

        if (Email.isEmpty()) {
            Toast.makeText(this, "Enter email to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(Email).delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show());
    }

    public void ClickMenu(View view) {
        opeDrawer(drawerLayout);
    }

    private void opeDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view) {
        Intent intent = new Intent(admin_edit_user.this, admin_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
    }



    public void logout(View view) {
        logoutMenu(admin_edit_user.this);
    }

    private void logoutMenu(admin_edit_user adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(admin_edit_user.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }
}
