package com.example.myapplicationp.userstatus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.example.myapplicationp.user.user_page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class userstatus_complaint_mscit extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ListView listView;
    ComplaintAdapter adapter;
    ArrayList<String> complaintList;
    ArrayList<String> complaintStatuses;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstatus_complaint_mscit);

        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.listViewMscitComplaints);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        complaintList = new ArrayList<>();
        complaintStatuses = new ArrayList<>();

        adapter = new ComplaintAdapter();
        listView.setAdapter(adapter);

        fetchUserComplaints();
    }

    private void fetchUserComplaints() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userEmail = currentUser.getEmail();

        db.collection("user_mscit_Complaints").whereEqualTo("email", userEmail)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(userstatus_complaint_mscit.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        complaintList.clear();
                        complaintStatuses.clear();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String name = doc.getString("name");
                            String rollNo = doc.getString("rollNo");
                            String className = doc.getString("className");
                            String issue = doc.getString("issue");
                            String complaint = doc.getString("complaint");
                            String status = doc.contains("status") ? doc.getString("status") : "Not Yet Started";

                            String details = "Name: " + name + "\n" +
                                    "Roll No: " + rollNo + "\n" +
                                    "Class: " + className + "\n" +
                                    "Issue: " + issue + "\n" +
                                    "Complaint: " + complaint;

                            complaintList.add(details);
                            complaintStatuses.add(status);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    class ComplaintAdapter extends ArrayAdapter<String> {
        ComplaintAdapter() {
            super(userstatus_complaint_mscit.this, R.layout.list_item_complaint, complaintList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_complaint, parent, false);
            }

            TextView complaintDetails = convertView.findViewById(R.id.complaintDetails);
            TextView statusText = convertView.findViewById(R.id.statusText);

            complaintDetails.setText(complaintList.get(position));

            String status = complaintStatuses.get(position);
            statusText.setText("Status: " + status);

            switch (status) {
                case "Completed":
                    statusText.setTextColor(Color.parseColor("#4CAF50"));  // Green
                    break;
                case "In Progress":
                    statusText.setTextColor(Color.parseColor("#FFC107"));  // Yellow
                    break;
                default:
                    statusText.setTextColor(Color.parseColor("#F44336"));  // Red
                    break;
            }

            return convertView;
        }
    }

    public void ClickMenu(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view) {
        Intent intent = new Intent(userstatus_complaint_mscit.this, user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(userstatus_complaint_mscit.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void aboutus(View view) {
        Intent intent = new Intent(userstatus_complaint_mscit.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        logoutMenu(userstatus_complaint_mscit.this);
    }

    private void logoutMenu(userstatus_complaint_mscit userPage) {
        new AlertDialog.Builder(userPage)
                .setTitle("LOGOUT")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(userstatus_complaint_mscit.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
