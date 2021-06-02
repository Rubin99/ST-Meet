package com.example.stmeet.java_display;

public class JavaDisplayObject {
    private String javaId;
    private String javaName;
    private String javaSubject;
    //private String javaProfileImageUrl;

    public JavaDisplayObject(String javaId, String javaName, String javaSubject){ //, String javaProfileImageUrl
        this.javaId = javaId;
        this.javaName = javaName;
        this.javaSubject = javaSubject;
        //this.javaProfileImageUrl = javaProfileImageUrl;
    }

    public String getJavaId(){ return javaId; }
    public void setJavaId(String javaId){ this.javaId = javaId; }

    public String getJavaName(){ return javaName; }
    public void setJavaName(String javaName){ this.javaName = javaName; }

    public String getJavaSubject() { return javaSubject; }
    public void setJavaSubject(String javaSubject) { this.javaSubject = javaSubject; }

    /*public String getJavaProfileImageUrl(){ return javaProfileImageUrl; }
    public void setJavaProfileImageUrl(String javaProfileImageUrl){ this.javaProfileImageUrl = javaProfileImageUrl; }*/
}
