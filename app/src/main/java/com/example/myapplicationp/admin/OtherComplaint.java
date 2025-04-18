package com.example.myapplicationp.admin;

public class OtherComplaint {
    private String name;
    private String rollNo;
    private String email;
    private String complaint;
    private String className;
    private String department;
    private String issue;

    // Empty constructor required for Firestore
    public OtherComplaint() {}

    public OtherComplaint(String name, String rollNo, String email, String complaint, String className, String department, String issue) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.complaint = complaint;
        this.className = className;
        this.department = department;
        this.issue = issue;
    }

    // Getters
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public String getComplaint() { return complaint; }
    public String getClassName() { return className; }
    public String getDepartment() { return department; }
    public String getIssue() { return issue; }
}
