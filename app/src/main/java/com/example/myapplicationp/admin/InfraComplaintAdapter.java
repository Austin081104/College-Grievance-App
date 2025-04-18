package com.example.myapplicationp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationp.R;

import java.util.List;

public class InfraComplaintAdapter extends RecyclerView.Adapter<InfraComplaintAdapter.InfraComplaintViewHolder> {

    private List<InfraComplaint> complaintList;

    public InfraComplaintAdapter(List<InfraComplaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public InfraComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infra_complaint, parent, false);
        return new InfraComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfraComplaintViewHolder holder, int position) {
        InfraComplaint complaint = complaintList.get(position);
        holder.name.setText("Name: " + complaint.getName());
        holder.rollNo.setText("Roll No: " + complaint.getRollNo());
        holder.email.setText("Email: " + complaint.getEmail());
        holder.className.setText("Class: " + complaint.getClassName());
        holder.department.setText("Department: " + complaint.getDepartment());
        holder.issue.setText("Issue: " + complaint.getIssue());
        holder.complaintText.setText("Complaint: " + complaint.getComplaint());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class InfraComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView name, rollNo, email, className, department, issue, complaintText;

        public InfraComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            rollNo = itemView.findViewById(R.id.tvRollNo);
            email = itemView.findViewById(R.id.tvEmail);
            className = itemView.findViewById(R.id.tvClass);
            department = itemView.findViewById(R.id.tvDepartment);
            issue = itemView.findViewById(R.id.tvIssue);
            complaintText = itemView.findViewById(R.id.tvComplaint);
        }
    }
}
