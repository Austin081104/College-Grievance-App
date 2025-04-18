package com.example.myapplicationp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationp.R;

import java.util.List;

public class LibraryComplaintAdapter extends RecyclerView.Adapter<LibraryComplaintAdapter.ComplaintViewHolder> {

    private List<LibraryComplaint> complaintList;

    public LibraryComplaintAdapter(List<LibraryComplaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        LibraryComplaint complaint = complaintList.get(position);
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

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView name, rollNo, email, className, department, issue, complaintText;

        public ComplaintViewHolder(@NonNull View itemView) {
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
