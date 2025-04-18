package com.example.myapplicationp.adminDepartment;

public class MSCITComplaint {
    private String name;
    private String rollNo;
    private String email;
    private String complaint;
    private String className;
    private String issue;

    public MSCITComplaint() {}

    public MSCITComplaint(String name, String rollNo, String email, String complaint, String className, String issue) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.complaint = complaint;
        this.className = className;
        this.issue = issue;
    }

    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public String getComplaint() { return complaint; }
    public String getClassName() { return className; }
    public String getIssue() { return issue; }
}