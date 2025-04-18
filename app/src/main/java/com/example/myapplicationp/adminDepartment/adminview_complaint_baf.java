package com.example.myapplicationp.adminDepartment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.admin.admin_page;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class adminview_complaint_baf extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private BAFComplaintAdapter complaintAdapter;
    private List<BAFComplaint> complaintList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminview_complaint_baf);
        drawerLayout=findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerViewBAFComplaints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        complaintAdapter = new BAFComplaintAdapter(complaintList);
        recyclerView.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        fetchComplaints();
    }
    private void fetchComplaints() {
        db.collection("user_baf_Complaints").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(adminview_complaint_baf.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                complaintList.clear();
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        BAFComplaint complaint = document.toObject(BAFComplaint.class);
                        if (complaint != null) {
                            complaintList.add(complaint);
                        }
                    }
                    complaintAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(adminview_complaint_baf.this, "No complaints found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void Home(View view){
        Intent intent=new Intent(adminview_complaint_baf.this, admin_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  startActivity(intent);
    }


    public void logout(View view){
        logoutMenu(adminview_complaint_baf.this);

    }
    private void logoutMenu(adminview_complaint_baf adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(adminview_complaint_baf.this, MainActivity.class);
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