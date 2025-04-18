package com.example.myapplicationp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class user_feedback extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button btnfeedback;
    Spinner spinnerclass,spinnerdepartment,spinnerrating;
    EditText name, rollno, email, feedback;
    FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        drawerLayout = findViewById(R.id.drawer_layout);
        btnfeedback = findViewById(R.id.buttonFeedback);
        name = findViewById(R.id.feedName);
        rollno = findViewById(R.id.feedRollNo);
        email = findViewById(R.id.feedEmail);
        feedback = findViewById(R.id.feedFeedback);
        spinnerclass = findViewById(R.id.spinnerclass);
        spinnerdepartment = findViewById(R.id.spinnerdep);
        spinnerrating = findViewById(R.id.spinnerrating);

        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        String[] Class = {"Select Class", "First Year", "Second Year", "Third Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Class);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerclass.setAdapter(adapter);

        String[] department = {"Select Department", "BscIT", "BBI", "BAF", "BMS", "BMM","BFM","BCOM","MscIT","MCOM"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, department);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        spinnerdepartment.setAdapter(adapter1);

        String[] rating = {"Select Rating","⭐", "⭐⭐", "⭐⭐⭐", "⭐⭐⭐⭐", "⭐⭐⭐⭐⭐"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item, rating);
        adapter3.setDropDownViewResource(R.layout.spinner_item);
        spinnerrating.setAdapter(adapter3);

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String RollNo = rollno.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Feedback = feedback.getText().toString().trim();
                String Class = spinnerclass.getSelectedItem().toString().trim();
                String Department = spinnerdepartment.getSelectedItem().toString().trim();
                String Rating = spinnerrating.getSelectedItem().toString().trim();
                if (Name.isEmpty() || Email.isEmpty() || Feedback.isEmpty() || RollNo.isEmpty() || Class.isEmpty() || Department.isEmpty() || Rating.isEmpty()) {
                    Toast.makeText(user_feedback.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveFeedbackToFirestore(Name, RollNo, Email, Feedback, Class, Department, Rating);
                }
            }
        });
    }

    private void saveFeedbackToFirestore(String name, String rollNo, String email, String feedback, String class1, String department, String rating) {
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("name", name);
        feedbackData.put("rollNo", rollNo);
        feedbackData.put("email", email);
        feedbackData.put("feedback", feedback);
        feedbackData.put("className", class1);  // Updated key to match model
        feedbackData.put("department", department);
        feedbackData.put("rating",rating);

        db.collection("UserFeedback") // Firestore collection
                .add(feedbackData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(user_feedback.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(user_feedback.this, user_page.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(user_feedback.this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
                });
    }
    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(user_feedback.this, user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(user_feedback.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(user_feedback.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logout(View view){
        logoutMenu(user_feedback.this);

    }
    private void logoutMenu(user_feedback userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(user_feedback.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ;

            }
        });
        builder.show();

    }
}
