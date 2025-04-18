package com.example.myapplicationp.userdepartment;

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
import com.example.myapplicationp.user.user_complaint;
import com.example.myapplicationp.user.user_page;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class user_complaint_bmm extends AppCompatActivity {
    DrawerLayout drawerLayout;
    EditText name,rollno,email,complaint;
    Spinner spinnerclass,spinnerissue;
    Button submit;
    FirebaseFirestore db; // Firestore instance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmm);
        drawerLayout=findViewById(R.id.drawer_layout);
        submit=findViewById(R.id.btmsub);
        name=findViewById(R.id.comname);
        rollno=findViewById(R.id.comrollno);
        email=findViewById(R.id.comemail);
        complaint=findViewById(R.id.complaint);
        spinnerclass=findViewById(R.id.spinnerclass);
        spinnerissue=findViewById(R.id.spinnerissue);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        String[] Class = {"Select Class", "First Year", "Second Year", "Third Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Class);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerclass.setAdapter(adapter);

        String[] issue = {"Select issue", "Teachers", "Classroom","Syllabus", "Exams & Results", "Attendance"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, issue);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        spinnerissue.setAdapter(adapter2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String RollNo=rollno.getText().toString();
                String Email=email.getText().toString();
                String Complaint=complaint.getText().toString();
                String Class=spinnerclass.getSelectedItem().toString();
                String Issue=spinnerissue.getSelectedItem().toString();
                if (Name.isEmpty() || Email.isEmpty() || Complaint.isEmpty() || RollNo.isEmpty() || Class.isEmpty()|| Issue.isEmpty()) {
                    Toast.makeText(user_complaint_bmm.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    saveComplaintToFirestore(Name, RollNo, Email, Complaint, Class, Issue);
                }


            }
        });
    }
    private void saveComplaintToFirestore(String name, String rollNo, String email, String complaint, String class1, String issue) {
        Map<String, Object> complaintData = new HashMap<>();
        complaintData.put("name", name);
        complaintData.put("rollNo", rollNo);
        complaintData.put("email", email);
        complaintData.put("complaint", complaint);
        complaintData.put("className", class1);
        complaintData.put("issue", issue);
        complaintData.put("timestamp", Timestamp.now()); // Set current timestamp

        db.collection("user_bmm_Complaints") // Firestore Collection
                .add(complaintData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(user_complaint_bmm.this, "Complaint submitted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(user_complaint_bmm.this, user_complaint.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(user_complaint_bmm.this, "Error submitting complaint", Toast.LENGTH_SHORT).show();
                });
    }

    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(user_complaint_bmm.this, user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(user_complaint_bmm.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(user_complaint_bmm.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    public void logout(View view){
        logoutMenu(user_complaint_bmm.this);

    }
    private void logoutMenu(user_complaint_bmm userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(user_complaint_bmm.this, MainActivity.class);
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