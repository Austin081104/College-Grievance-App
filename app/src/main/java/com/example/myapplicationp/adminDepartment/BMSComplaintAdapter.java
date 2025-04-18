package com.example.myapplicationp.adminDepartment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationp.R;

import java.util.List;

public class BMSComplaintAdapter extends RecyclerView.Adapter<BMSComplaintAdapter.BMSComplaintViewHolder> {

    private List<BMSComplaint> complaintList;

    public BMSComplaintAdapter(List<BMSComplaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public BMSComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bms_complaint, parent, false);
        return new BMSComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BMSComplaintViewHolder holder, int position) {
        BMSComplaint complaint = complaintList.get(position);
        holder.name.setText("Name: " + complaint.getName());
        holder.rollNo.setText("Roll No: " + complaint.getRollNo());
        holder.email.setText("Email: " + complaint.getEmail());
        holder.className.setText("Class: " + complaint.getClassName());
        holder.issue.setText("Issue: " + complaint.getIssue());
        holder.complaintText.setText("Complaint: " + complaint.getComplaint());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class BMSComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView name, rollNo, email, className, issue, complaintText;

        public BMSComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            rollNo = itemView.findViewById(R.id.tvRollNo);
            email = itemView.findViewById(R.id.tvEmail);
            className = itemView.findViewById(R.id.tvClass);
            issue = itemView.findViewById(R.id.tvIssue);
            complaintText = itemView.findViewById(R.id.tvComplaint);
        }
    }
}
