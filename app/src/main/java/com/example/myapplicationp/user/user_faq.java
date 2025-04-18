package com.example.myapplicationp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class user_faq extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ListView faqListView;
    FirebaseFirestore db;
    ArrayList<String> faqList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_faq);
        drawerLayout=findViewById(R.id.drawer_layout);
        faqListView = findViewById(R.id.faqListView);
        db = FirebaseFirestore.getInstance();
        faqList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faqList);

        faqListView.setAdapter(adapter);

        loadFAQs();
    }

    private void loadFAQs() {
        db.collection("FAQs").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                faqList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String question = document.getString("question");
                    String answer = document.getString("answer");
                    faqList.add("Question: " + question + "\nAnswer: " + answer);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(user_faq.this,user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(user_faq.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(user_faq.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logout(View view){
        logoutMenu(user_faq.this);

    }
    private void logoutMenu(user_faq userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(user_faq.this, MainActivity.class);
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
