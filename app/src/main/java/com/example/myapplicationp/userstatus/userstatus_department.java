package com.example.myapplicationp.userstatus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.example.myapplicationp.user.user_page;

public class userstatus_department extends AppCompatActivity {
    DrawerLayout drawerLayout;
   LinearLayout bscit,baf,bms,bbi,bmm,bfm,mcom,mscit,bcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstatus_department);
        drawerLayout=findViewById(R.id.drawer_layout);
        bscit=findViewById(R.id.bscit);
        baf=findViewById(R.id.baf);
        bms=findViewById(R.id.bms);
        bbi=findViewById(R.id.bbi);
        bmm=findViewById(R.id.bmm);
        bfm=findViewById(R.id.bfm);
        mcom=findViewById(R.id.mcom);
        mscit=findViewById(R.id.mscit);
        bcom=findViewById(R.id.bcom);


        bscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bscit.class);
                startActivity(intent);
            }
        });

        baf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_baf.class);
                startActivity(intent);
            }
        });

        bms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bms.class);
                startActivity(intent);
            }
        });

        bmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bmm.class);
                startActivity(intent);
            }
        });

        bbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bbi.class);
                startActivity(intent);
            }
        });

        bfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bfm.class);
                startActivity(intent);
            }
        });

        mcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_mcom.class);
                startActivity(intent);
            }
        });

        mscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_mscit.class);
                startActivity(intent);
            }
        });

        bcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userstatus_department.this, userstatus_complaint_bcom.class);
                startActivity(intent);
            }
        });


    }
    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(userstatus_department.this, user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(userstatus_department.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(userstatus_department.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);   startActivity(intent);
    }




    public void logout(View view){
        logoutMenu(userstatus_department.this);

    }
    private void logoutMenu(userstatus_department userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(userstatus_department.this, MainActivity.class);
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