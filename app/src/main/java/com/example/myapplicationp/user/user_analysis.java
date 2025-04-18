package com.example.myapplicationp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplicationp.MainActivity;
import com.example.myapplicationp.R;
import com.example.myapplicationp.tab.tab_aboutus;
import com.example.myapplicationp.tab.tab_profile;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class user_analysis extends AppCompatActivity {

    private PieChart pieChart, departmentPieChart;
    private BarChart barChart;
    private LineChart lineChart;

    private TextView tvTotalComplaints, tvSolvedComplaints;
    private TextView tvNotStartedComplaints, tvInProgressComplaints, tvCompletionRate;
    private PieChart ratingPieChart;  // New PieChart for ratings
    private int oneStar = 0, twoStars = 0, threeStars = 0, fourStars = 0, fiveStars = 0;

    private FirebaseFirestore db;
    DrawerLayout drawerLayout;
    private int totalComplaints = 0;
    private int notStartedCount = 0;
    private int inProgressCount = 0;
    private int completedCount = 0;

    // Department counts
    private int bafCount = 0, bbiCount = 0, bcomCount = 0, bfmCount = 0;
    private int bmmCount = 0, bmsCount = 0, bscitCount = 0, mcomCount = 0, mscitCount = 0;

    private final String[] collections = {
            "canteen_complaints", "Infarstructure_complaints", "Library_complaints", "Other_complaints",
            "user_baf_Complaints", "user_bbi_Complaints", "user_bcom_Complaints", "user_bfm_Complaints",
            "user_bmm_Complaints", "user_bms_Complaints", "user_bscit_Complaints", "user_mcom_Complaints",
            "user_mscit_Complaints"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analysis);
        drawerLayout=findViewById(R.id.drawer_layout);
        tvTotalComplaints = findViewById(R.id.tv_total_complaints);
        tvSolvedComplaints = findViewById(R.id.tv_solved_complaints);

        tvNotStartedComplaints = findViewById(R.id.tv_not_started_complaints);
        tvInProgressComplaints = findViewById(R.id.tv_in_progress_complaints);
        tvCompletionRate = findViewById(R.id.tv_completion_rate);

        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        lineChart = findViewById(R.id.lineChart);
        departmentPieChart = findViewById(R.id.departmentPieChart);
        ratingPieChart = findViewById(R.id.ratingPieChart);


        db = FirebaseFirestore.getInstance();

        fetchAllComplaints();
        fetchFeedbackRatings();
    }



    private void fetchAllComplaints() {
        for (String collection : collections) {
            fetchComplaintsFromCollection(collection);
        }
    }
    private void fetchFeedbackRatings() {
        db.collection("UserFeedback").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String rating = document.getString("rating");
                    if (rating != null) {
                        switch (rating) {
                            case "⭐": oneStar++; break;
                            case "⭐⭐": twoStars++; break;
                            case "⭐⭐⭐": threeStars++; break;
                            case "⭐⭐⭐⭐": fourStars++; break;
                            case "⭐⭐⭐⭐⭐": fiveStars++; break;
                        }
                    }
                }
                setupRatingPieChart();
            }
        });
    }
    private void fetchComplaintsFromCollection(String collectionName) {
        db.collection(collectionName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    totalComplaints++;
                    countDepartment(collectionName);

                    String status = document.getString("status");
                    if (status != null) {
                        switch (status) {
                            case "Not Yet Started":
                                notStartedCount++;
                                break;
                            case "In Progress":
                                inProgressCount++;
                                break;
                            case "Completed":
                                completedCount++;
                                break;
                        }
                    }
                }
                updateUI();
            } else {
                Toast.makeText(this, "Failed to fetch data from " + collectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countDepartment(String collectionName) {
        switch (collectionName) {
            case "user_baf_Complaints": bafCount++; break;
            case "user_bbi_Complaints": bbiCount++; break;
            case "user_bcom_Complaints": bcomCount++; break;
            case "user_bfm_Complaints": bfmCount++; break;
            case "user_bmm_Complaints": bmmCount++; break;
            case "user_bms_Complaints": bmsCount++; break;
            case "user_bscit_Complaints": bscitCount++; break;
            case "user_mcom_Complaints": mcomCount++; break;
            case "user_mscit_Complaints": mscitCount++; break;
        }
    }

    private void updateUI() {
        int remainingComplaints = notStartedCount + inProgressCount;
        float completionRate = totalComplaints > 0 ? ((float) completedCount / totalComplaints) * 100 : 0;

        tvTotalComplaints.setText("Total Complaints: " + totalComplaints);
        tvSolvedComplaints.setText("Solved Complaints: " + completedCount);
        tvNotStartedComplaints.setText("Not Yet Started: " + notStartedCount);
        tvInProgressComplaints.setText("In Progress: " + inProgressCount);
        tvCompletionRate.setText(String.format("Overall Completion Rate: %.2f%%", completionRate));

        setupPieChart();
        setupBarChart();
        setupLineChart();
        setupDepartmentPieChart();
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(notStartedCount, "Not Yet Started"));
        entries.add(new PieEntry(inProgressCount, "In Progress"));
        entries.add(new PieEntry(completedCount, "Completed"));

        PieDataSet dataSet = new PieDataSet(entries, "Complaint Status");
        dataSet.setColors(android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.GREEN);
        dataSet.setValueTextColor(android.graphics.Color.BLACK);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setEntryLabelColor(android.graphics.Color.BLACK);
        pieChart.setData(data);
        pieChart.animateY(1500);
        pieChart.invalidate();
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, notStartedCount));
        entries.add(new BarEntry(1, inProgressCount));
        entries.add(new BarEntry(2, completedCount));

        BarDataSet dataSet = new BarDataSet(entries, "Complaint Status");
        dataSet.setColors(android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.GREEN);
        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.getXAxis().setValueFormatter((value, axis) -> {
            switch ((int) value) {
                case 0: return "Not Started";
                case 1: return "In Progress";
                case 2: return "Completed";
                default: return "";
            }
        });
        barChart.animateY(2000);
        barChart.invalidate();
    }

    private void setupLineChart() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, notStartedCount));
        entries.add(new Entry(1, inProgressCount));
        entries.add(new Entry(2, completedCount));

        LineDataSet dataSet = new LineDataSet(entries, "Complaint Status");
        dataSet.setColors(android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.GREEN);
        LineData data = new LineData(dataSet);

        lineChart.setData(data);
        lineChart.animateX(2500);
        lineChart.invalidate();
    }

    private void setupDepartmentPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(bafCount, "BAF"));
        entries.add(new PieEntry(bbiCount, "BBI"));
        entries.add(new PieEntry(bcomCount, "BCom"));
        entries.add(new PieEntry(bfmCount, "BFM"));
        entries.add(new PieEntry(bmmCount, "BMM"));
        entries.add(new PieEntry(bmsCount, "BMS"));
        entries.add(new PieEntry(bscitCount, "BScIT"));
        entries.add(new PieEntry(mcomCount, "MCom"));
        entries.add(new PieEntry(mscitCount, "MScIT"));

        PieDataSet dataSet = new PieDataSet(entries, "Complaints by Department");

        // Assign different colors for each department
        dataSet.setColors(new int[]{
                android.graphics.Color.parseColor("#FF5733"), // BAF - Orange Red
                android.graphics.Color.parseColor("#33FF57"), // BBI - Green
                android.graphics.Color.parseColor("#3380FF"), // BCom - Blue
                android.graphics.Color.parseColor("#FF33C4"), // BFM - Pink
                android.graphics.Color.parseColor("#FFFF33"), // BMM - Yellow
                android.graphics.Color.parseColor("#33FFF0"), // BMS - Cyan
                android.graphics.Color.parseColor("#A633FF"), // BScIT - Purple
                android.graphics.Color.parseColor("#FF8C33"), // MCom - Dark Orange
                android.graphics.Color.parseColor("#66FF33")  // MScIT - Lime Green
        });

        PieData data = new PieData(dataSet);
        dataSet.setValueTextColor(android.graphics.Color.BLACK); // Labels (values) inside slices
        dataSet.setValueTextSize(14f);

        departmentPieChart.setEntryLabelColor(android.graphics.Color.BLACK);  // Labels outside slices
        departmentPieChart.setData(data);
        departmentPieChart.animateY(2500);
        departmentPieChart.invalidate();
    }
    private void setupRatingPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        if (oneStar > 0) entries.add(new PieEntry(oneStar, "⭐"));
        if (twoStars > 0) entries.add(new PieEntry(twoStars, "⭐⭐"));
        if (threeStars > 0) entries.add(new PieEntry(threeStars, "⭐⭐⭐"));
        if (fourStars > 0) entries.add(new PieEntry(fourStars, "⭐⭐⭐⭐"));
        if (fiveStars > 0) entries.add(new PieEntry(fiveStars, "⭐⭐⭐⭐⭐"));

        PieDataSet dataSet = new PieDataSet(entries, "Ratings");
        dataSet.setColors(
                android.graphics.Color.RED,  // ⭐
                android.graphics.Color.YELLOW,  // ⭐⭐
                android.graphics.Color.BLUE,  // ⭐⭐⭐
                android.graphics.Color.GREEN,  // ⭐⭐⭐⭐
                android.graphics.Color.MAGENTA  // ⭐⭐⭐⭐⭐
        );
        dataSet.setValueTextColor(android.graphics.Color.BLACK);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        ratingPieChart.setEntryLabelColor(android.graphics.Color.BLACK);
        ratingPieChart.setData(data);
        ratingPieChart.animateY(2000);
        ratingPieChart.invalidate();
    }

    public void ClickMenu(View view) {opeDrawer(drawerLayout);}

    private void opeDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Home(View view){
        Intent intent=new Intent(user_analysis.this,user_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent=new Intent(user_analysis.this, tab_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void aboutus(View view){
        Intent intent=new Intent(user_analysis.this, tab_aboutus.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logout(View view){
        logoutMenu(user_analysis.this);

    }
    private void logoutMenu(user_analysis userPage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(userPage);
        builder.setTitle("LOGOUT");
        builder.setMessage(" Are you sure you want to logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(user_analysis.this, MainActivity.class);
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
