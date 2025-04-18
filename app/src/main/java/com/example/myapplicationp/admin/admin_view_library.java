package com.example.myapplicationp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class admin_view_library extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LibraryComplaintAdapter complaintAdapter;
    private List<LibraryComplaint> complaintList;
    private FirebaseFirestore db;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_library);
        drawerLayout=findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerViewComplaints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        complaintAdapter = new LibraryComplaintAdapter(complaintList);
        recyclerView.setAdapter(complaintAdapter);

        db = FirebaseFirestore.getInstance();
        fetchComplaints();
    }

    private void fetchComplaints() {
        db.collection("Library_complaints").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(admin_view_library.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    return;
                }
                complaintList.clear();
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        LibraryComplaint complaint = document.toObject(LibraryComplaint.class);
                        if (complaint != null) {
                            complaintList.add(complaint);
                        }
                    }
                }
                complaintAdapter.notifyDataSetChanged();
            }
        });
    }

    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void Home(View view){
        Intent intent=new Intent(admin_view_library.this,admin_page.class);
        startActivity(intent);
    }




    public void logout(View view){
        logoutMenu(admin_view_library.this);

    }
    private void logoutMenu(admin_view_library adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(admin_view_library.this, MainActivity.class);
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
