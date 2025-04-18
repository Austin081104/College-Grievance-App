package com.example.myapplicationp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.myapplicationp.R;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<Feedback> feedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.name.setText("Name: " + feedback.getName());
        holder.rollNo.setText("Roll No: " + feedback.getRollNo());
        holder.email.setText("Email: " + feedback.getEmail());
        holder.className.setText("Class: " + feedback.getClassName());
        holder.department.setText("Department: " + feedback.getDepartment());
        holder.feedback.setText("Feedback: " + feedback.getFeedback());
        holder.rating.setText("Rating: " + feedback.getRating());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rollNo, email, className, department, feedback, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            rollNo = itemView.findViewById(R.id.txtRollNo);
            email = itemView.findViewById(R.id.txtEmail);
            className = itemView.findViewById(R.id.txtClass);
            department = itemView.findViewById(R.id.txtDepartment);
            feedback = itemView.findViewById(R.id.txtFeedback);
            rating = itemView.findViewById(R.id.txtRating);
        }
    }
}
