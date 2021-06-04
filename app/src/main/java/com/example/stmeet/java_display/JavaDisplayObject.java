package com.example.stmeet.java_display;

public class JavaDisplayObject {
    public String userId;
    public String name;
    public String subject;
    private String profileImageUrl;

    public JavaDisplayObject(){

    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }
}
