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
import com.example.myapplicationp.adminEdit.admin_edit_canteen;
import com.example.myapplicationp.adminEdit.admin_edit_department;
import com.example.myapplicationp.adminEdit.admin_edit_infra;
import com.example.myapplicationp.adminEdit.admin_edit_library;
import com.example.myapplicationp.adminEdit.admin_edit_other;

public class admin_edit_status extends AppCompatActivity {
    DrawerLayout drawerLayout;
    LinearLayout dep,library,infra,canteen,others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_status);
        drawerLayout=findViewById(R.id.drawer_layout);
        dep=findViewById(R.id.admindepartment);
        library=findViewById(R.id.adminlibrary);
        infra=findViewById(R.id.admininfra);
        canteen=findViewById(R.id.admincanteen);
        others=findViewById(R.id.adminother);

        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_status.this, admin_edit_department.class);
                startActivity(intent);
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_status.this, admin_edit_library.class);
                startActivity(intent);
            }
        });

        infra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_status.this, admin_edit_infra.class);
                startActivity(intent);
            }
        });

        canteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_status.this, admin_edit_canteen.class);
                startActivity(intent);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_edit_status.this, admin_edit_other.class);
                startActivity(intent);
            }
        });
    }
    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void Home(View view){
        Intent intent=new Intent(admin_edit_status.this,admin_page.class);
        startActivity(intent);
    }



    public void logout(View view){
        logoutMenu(admin_edit_status.this);

    }
    private void logoutMenu(admin_edit_status adminPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(admin_edit_status.this, MainActivity.class);
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