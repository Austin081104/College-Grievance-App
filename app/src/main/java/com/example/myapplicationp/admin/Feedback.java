package com.example.myapplicationp.admin;

public class Feedback {
    private String name, rollNo, email, feedback, className, department,rating;

    public Feedback() {
        // Required for Firebase
    }

    public Feedback(String name, String rollNo, String email, String feedback, String className, String department,String rating) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.feedback = feedback;
        this.className = className;
        this.department = department;
        this.rating=rating;
    }

    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public String getFeedback() { return feedback; }
    public String getClassName() { return className; }
    public String getDepartment() { return department; }
    public String getRating() { return rating; }
}
