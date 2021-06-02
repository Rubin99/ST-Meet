package com.example.stmeet.student_requests;

public class StudentRequestObject {
    private String studentId;
    private String studentName;
    private String studentProfileImageUrl;

    public StudentRequestObject(String studentId, String studentName, String studentProfileImageUrl){
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentProfileImageUrl = studentProfileImageUrl;
    }

    public String getStudentId() { return  studentId; }
    public void setStudentId(String studentId){ this.studentId = studentId; }

    public String getStudentName() { return  studentName; }
    public void setStudentName(String studentName){ this.studentName = studentName; }

    public String getStudentProfileImageUrl() { return  studentProfileImageUrl; }
    public void setStudentProfileImageUrl(String studentProfileImageUrl){ this.studentProfileImageUrl = studentProfileImageUrl; }

}
