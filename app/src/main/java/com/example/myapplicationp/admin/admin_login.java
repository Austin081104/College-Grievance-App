package com.example.myapplicationp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplicationp.HOD_DEPARTMENT.baf_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bbi_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bcom_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bfm_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bmm_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bms_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.bscit_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.library_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.mcom_hod_page;
import com.example.myapplicationp.HOD_DEPARTMENT.mscit_hod_page;
import com.example.myapplicationp.R;

public class admin_login extends AppCompatActivity {
    private EditText AdminName,AdminPass;
    private Button AdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        AdminName=findViewById(R.id.adminname);
        AdminPass=findViewById(R.id.adminpassword);
        AdminLogin=findViewById(R.id.adminlogin);

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=AdminName.getText().toString();
                String pass=AdminPass.getText().toString();

                //admin login
                if (name.equals("admin") && pass.equals("admin123")){
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, admin_page.class);
                    startActivity(intent);

                } // Bscit HOD login
                else if (name.equals("bscit") && pass.equals("bscit123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bscit_hod_page.class);
                    startActivity(intent);
                } // BMS HOD login
                else if (name.equals("bms") && pass.equals("bms123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bms_hod_page.class);
                    startActivity(intent);
                }// BAF HOD login
                else if (name.equals("baf") && pass.equals("baf123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, baf_hod_page.class);
                    startActivity(intent);
                }// BBI HOD login
                else if (name.equals("bbi") && pass.equals("bbi123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bbi_hod_page.class);
                    startActivity(intent);
                } // BFM HOD login
                else if (name.equals("bfm") && pass.equals("bfm123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bfm_hod_page.class);
                    startActivity(intent);
                } // BMM HOD login
                else if (name.equals("bmm") && pass.equals("bmm123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bmm_hod_page.class);
                    startActivity(intent);
                } // BCOM HOD login
                else if (name.equals("bcom") && pass.equals("bcom123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, bcom_hod_page.class);
                    startActivity(intent);
                }  // MCOM HOD login
                else if (name.equals("mcom") && pass.equals("mcom123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, mcom_hod_page.class);
                    startActivity(intent);
                } // MSCIT HOD login
                else if (name.equals("mscit") && pass.equals("mscit123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, mscit_hod_page.class);
                    startActivity(intent);
                }  // LIBRARY HOD login
                else if (name.equals("library") && pass.equals("library123")) {
                    Toast.makeText(admin_login.this, "login succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login.this, library_hod_page.class);
                    startActivity(intent);
                } else if (name.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(admin_login.this, "login Unsuccesful", Toast.LENGTH_SHORT).show();

                } else if (name.isEmpty()) {
                    Toast.makeText(admin_login.this, "please fill the fields", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(admin_login.this, "please fill the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}