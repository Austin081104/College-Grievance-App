package com.example.myapplicationp.adminEdit;

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
import com.example.myapplicationp.admin.admin_page;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminedit_complaint_bbi extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ListView listView;
    ComplaintAdapter adapter;
    ArrayList<String> complaintList;
    ArrayList<String> complaintIds;
    ArrayList<String> complaintStatuses;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminedit_complaint_bbi);

        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.listViewBbiComplaints);

        db = FirebaseFirestore.getInstance();
        complaintList = new ArrayList<>();
        complaintIds = new ArrayList<>();
        complaintStatuses = new ArrayList<>();

        adapter = new ComplaintAdapter();
        listView.setAdapter(adapter);

        fetchComplaints();

        listView.setOnItemClickListener((parent, view, position, id) -> showStatusDialog(position));
    }

    private void fetchComplaints() {
        db.collection("user_bbi_Complaints")
                .orderBy("timestamp", Query.Direction.DESCENDING)  // Order complaints by latest first
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(adminedit_complaint_bbi.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        complaintList.clear();
                        complaintIds.clear();
                        complaintStatuses.clear();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String name = doc.getString("name");
                            String email = doc.getString("email");
                            String rollNo = doc.getString("rollNo");
                            String className = doc.getString("className");
                            String issue = doc.getString("issue");
                            String complaint = doc.getString("complaint");
                            String status = doc.contains("status") ? doc.getString("status") : "Not Yet Started";

                            String details = "Name: " + name + "\n" +
                                    "Email: " + email + "\n" +
                                    "Roll No: " + rollNo + "\n" +
                                    "Class: " + className + "\n" +
                                    "Issue: " + issue + "\n" +
                                    "Complaint: " + complaint;

                            complaintList.add(details);
                            complaintIds.add(doc.getId());
                            complaintStatuses.add(status);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void showStatusDialog(int position) {
        final String[] statusOptions = {"Not Yet Started", "In Progress", "Completed"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Status");
        builder.setItems(statusOptions, (dialog, which) -> updateStatus(complaintIds.get(position), statusOptions[which]));
        builder.show();
    }

    private void updateStatus(String docId, String newStatus) {
        DocumentReference docRef = db.collection("user_bbi_Complaints").document(docId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", newStatus);

        docRef.update(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(adminedit_complaint_bbi.this, "Status Updated to " + newStatus, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(adminedit_complaint_bbi.this, "Error updating status", Toast.LENGTH_SHORT).show());
    }

    class ComplaintAdapter extends ArrayAdapter<String> {

        ComplaintAdapter() {
            super(adminedit_complaint_bbi.this, R.layout.list_item_complaint, complaintList);
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
        openDrawer(drawerLayout);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view) {
        startActivity(new Intent(adminedit_complaint_bbi.this, admin_page.class));
    }

    public void logout(View view) {
        logoutMenu(this);
    }

    private void logoutMenu(adminedit_complaint_bbi adminPage) {
        new AlertDialog.Builder(adminPage)
                .setTitle("LOGOUT")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(adminedit_complaint_bbi.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
