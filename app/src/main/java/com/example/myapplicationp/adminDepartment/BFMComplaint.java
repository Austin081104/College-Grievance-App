package com.example.myapplicationp.adminDepartment;

public class BFMComplaint {
    private String name;
    private String rollNo;
    private String email;
    private String complaint;
    private String className;
    private String issue;

    // Empty constructor required for Firestore
    public BFMComplaint() {}

    public BFMComplaint(String name, String rollNo, String email, String complaint, String className, String issue) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.complaint = complaint;
        this.className = className;
        this.issue = issue;
    }

    // Getters
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public String getComplaint() { return complaint; }
    public String getClassName() { return className; }
    public String getIssue() { return issue; }
}
