package com.example.myapplicationp.adminEdit;

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
import com.example.myapplicationp.admin.admin_page;

public class admin_edit_department extends AppCompatActivity {
    DrawerLayout drawerLayout;
   LinearLayout bscit,baf,bms,bbi,bmm,bfm,mcom,mscit,bcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_department);
        drawerLayout = findViewById(R.id.drawer_layout);

        bscit=findViewById(R.id.adminbscit);
        baf=findViewById(R.id.adminbaf);
        bms=findViewById(R.id.adminbms);
        bbi=findViewById(R.id.adminbbi);
        bmm=findViewById(R.id.adminbmm);
        bfm=findViewById(R.id.adminbfm);
        mcom=findViewById(R.id.adminmcom);
        mscit=findViewById(R.id.adminmscit);
        bcom=findViewById(R.id.adminbcom);


        bscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_bscit.class);
                startActivity(intent);
            }
        });

        baf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_baf.class);
                startActivity(intent);
            }
        });

        bms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this,adminedit_complaint_bms.class);
                startActivity(intent);
            }
        });

        bmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_bmm.class);
                startActivity(intent);
            }
        });

        bbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_bbi.class);
                startActivity(intent);
            }
        });

        bfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_bfm.class);
                startActivity(intent);
            }
        });

        mcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_mcom.class);
                startActivity(intent);
            }
        });

        mscit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_mscit.class);
                startActivity(intent);
            }
        });

        bcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_department.this, adminedit_complaint_bcom.class);
                startActivity(intent);
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
        Intent intent = new Intent(admin_edit_department.this, admin_page.class);
        startActivity(intent);
    }







    public void logout(View view) {
        logoutMenu(admin_edit_department.this);
    }

    private void logoutMenu(admin_edit_department adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(admin_edit_department.this, MainActivity.class);
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