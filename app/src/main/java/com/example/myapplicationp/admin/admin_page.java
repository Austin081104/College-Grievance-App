package com.example.myapplicationp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;


public class admin_page extends AppCompatActivity {
    DrawerLayout drawerLayout;

  LinearLayout ViewComplaint,EditStatus,ViewFeedback,AddUser,Viewuser,editfaq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);


        drawerLayout=findViewById(R.id.drawer_layout);
        ViewComplaint=findViewById(R.id.viewcomplaint);
        EditStatus=findViewById(R.id.editstatus);
        ViewFeedback=findViewById(R.id.viewfeedback);
        AddUser=findViewById(R.id.adduser);
        Viewuser=findViewById(R.id.viewuser);
        editfaq=findViewById(R.id.editFAQ);


        //complaint button
        ViewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_view_complaint.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);    startActivity(intent);

            }
        });

        EditStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_edit_status.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
            }
        });
        ViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_view_feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
            }
        });
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_edit_user.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);    startActivity(intent);
            }
        });

        Viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_view_user.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
            }
        });

        editfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, admin_edit_faq.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
            }
        });

    }
    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void Home(View view){
        Intent intent=new Intent(admin_page.this,admin_page.class);
        startActivity(intent);
    }




    public void logout(View view){
        logoutMenu(admin_page.this);

    }
    private void logoutMenu(admin_page adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(admin_page.this, MainActivity.class);
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