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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class user_library extends AppCompatActivity {
    DrawerLayout drawerLayout;
    EditText name,rollno,email,complaint;
    Spinner spinnerclass,spinnerdepartment,spinnerissue;
    Button submit;
    FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_library);
        drawerLayout=findViewById(R.id.drawer_layout);

        submit=findViewById(R.id.btmsubmit);
        name=findViewById(R.id.comname);
        rollno=findViewById(R.id.comrollno);
        email=findViewById(R.id.comemail);
        complaint=findViewById(R.id.complaint);
        spinnerclass=findViewById(R.id.spinnerclass);
        spinnerissue=findViewById(R.id.spinnerissue);
        spinnerdepartment=findViewById(R.id.spinnerdep);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore



        String[] Class = {"Select Class", "First Year", "Second Year", "Third Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Class);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerclass.setAdapter(adapter);

        String[] department = {"Select Department", "BscIT", "BBI", "BAF", "BMS", "BMM","BFM","BCOM","MscIT","MCOM"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, department);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        spinnerdepartment.setAdapter(adapter1);

        String[] issue = {"Select issue", "Seating & Study Areas", "Missing Books", "Outdated Books", "Limited Copies", "Book Issuing & Return Issues ","Library Management","Library Wi-Fi Issues","Noise Complaints","Computer Access Problems"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, issue);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        spinnerissue.setAdapter(adapter2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComplaintToFirestore();
            }
        });
    }
    private void saveComplaintToFirestore() {
        String Name = name.getText().toString();
        String RollNo = rollno.getText().toString();
        String Email = email.getText().toString();
        String Complaint = complaint.getText().toString();
        String Class = spinnerclass.getSelectedItem().toString();
        String Department = spinnerdepartment.getSelectedItem().toString();
        String Issue = spinnerissue.getSelectedItem().toString();

        if (Name.isEmpty() || Email.isEmpty() || Complaint.isEmpty() || RollNo.isEmpty() ||
                Class.equals("Select Class") || Issue.equals("Select issue") || Department.equals("Select Department")) {
            Toast.makeText(user_library.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> complaintData = new HashMap<>();
            complaintData.put("name", Name);
            complaintData.put("rollNo", RollNo);
            complaintData.put("email", Email);
            complaintData.put("complaint", Complaint);
            complaintData.put("className", Class);  // Updated key to match model
            complaintData.put("department", Department);
            complaintData.put("issue", Issue);
            complaintData.put("timestamp", Timestamp.now()); // Set current timestamp
            db.collection("Library_complaints").add(complaintData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(user_library.this, "Complaint Submitted!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(user_library.this,user_complaint.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(user_library.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(user_library.this,user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(user_library.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(user_library.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    public void logout(View view){
        logoutMenu(user_library.this);

    }
    private void logoutMenu(user_library userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(user_library.this, MainActivity.class);
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