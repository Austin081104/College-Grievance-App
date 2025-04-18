package com.example.myapplicationp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class admin_view_canteen extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private CanteenComplaintAdapter complaintAdapter;
    private List<CanteenComplaint> complaintList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_view_canteen);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerViewCanteenComplaints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        complaintAdapter = new CanteenComplaintAdapter(complaintList);
        recyclerView.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        fetchComplaints();
    }
    private void fetchComplaints() {
        db.collection("canteen_complaints").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(admin_view_canteen.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                complaintList.clear();
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        CanteenComplaint complaint = document.toObject(CanteenComplaint.class);
                        if (complaint != null) {
                            complaintList.add(complaint);
                        }
                    }
                    complaintAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(admin_view_canteen.this, "No complaints found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view) {
        Intent intent = new Intent(admin_view_canteen.this, admin_page.class);
        startActivity(intent);
    }




    public void logout(View view) {
        logoutMenu(admin_view_canteen.this);
    }

    private void logoutMenu(admin_view_canteen adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(admin_view_canteen.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}