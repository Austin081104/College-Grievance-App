package com.example.myapplicationp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.example.myapplicationp.user.user_page;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_edit_faq extends AppCompatActivity {

    private EditText faqQuestion, faqAnswer;
    private ListView adminFaqListView;
    private FirebaseFirestore db;
    private List<String> faqList;
    private List<String> faqDocIds;  // To store Firestore document IDs for deletion
    private ArrayAdapter<String> adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_faq);

        drawerLayout = findViewById(R.id.drawer_layout);
        faqQuestion = findViewById(R.id.faqQuestion);
        faqAnswer = findViewById(R.id.faqAnswer);
        adminFaqListView = findViewById(R.id.adminFaqListView);

        db = FirebaseFirestore.getInstance();
        faqList = new ArrayList<>();
        faqDocIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faqList);
        adminFaqListView.setAdapter(adapter);

        // Notify admin on how to delete
        Toast.makeText(this, "Long press on a FAQ to delete it.", Toast.LENGTH_LONG).show();

        loadFAQs();

        findViewById(R.id.btnAddFAQ).setOnClickListener(v -> {
            String question = faqQuestion.getText().toString().trim();
            String answer = faqAnswer.getText().toString().trim();

            if (!question.isEmpty() && !answer.isEmpty()) {
                addFAQ(question, answer);
            } else {
                Toast.makeText(admin_edit_faq.this, "Please enter both question and answer.", Toast.LENGTH_SHORT).show();
            }
        });

        adminFaqListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteConfirmationDialog(position);
            return true;
        });
    }

    private void loadFAQs() {
        db.collection("FAQs").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                faqList.clear();
                faqDocIds.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String question = document.getString("question");
                    String answer = document.getString("answer");
                    if (question != null && answer != null) {
                        faqList.add(question + "\n" + answer);
                        faqDocIds.add(document.getId());  // Store document ID for deletion
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(admin_edit_faq.this, "Failed to load FAQs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFAQ(String question, String answer) {
        Map<String, Object> faq = new HashMap<>();
        faq.put("question", question);
        faq.put("answer", answer);

        db.collection("FAQs").add(faq).addOnSuccessListener(documentReference -> {
            Toast.makeText(admin_edit_faq.this, "FAQ added successfully", Toast.LENGTH_SHORT).show();
            faqQuestion.setText("");
            faqAnswer.setText("");
            loadFAQs();
        }).addOnFailureListener(e -> {
            Toast.makeText(admin_edit_faq.this, "Failed to add FAQ", Toast.LENGTH_SHORT).show();
        });
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete FAQ");
        builder.setMessage("Are you sure you want to delete this FAQ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteFAQ(faqDocIds.get(position));
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    private void deleteFAQ(String docId) {
        db.collection("FAQs").document(docId).delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(admin_edit_faq.this, "FAQ deleted", Toast.LENGTH_SHORT).show();
            loadFAQs();
        }).addOnFailureListener(e -> {
            Toast.makeText(admin_edit_faq.this, "Failed to delete FAQ", Toast.LENGTH_SHORT).show();
        });
    }

    // Drawer Navigation
    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view) {
        Intent intent = new Intent(admin_edit_faq.this, admin_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(admin_edit_faq.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void aboutus(View view) {
        Intent intent = new Intent(admin_edit_faq.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        logoutMenu(admin_edit_faq.this);
    }

    private void logoutMenu(admin_edit_faq adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Intent intent = new Intent(admin_edit_faq.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }
}
