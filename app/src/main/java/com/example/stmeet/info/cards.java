package com.example.stmeet.info;

public class cards {
    private String userId;
    private String name;
    private String subject;
    private String profileImageUrl;

    public cards(String userId, String name, String subject, String profileImageUrl){
        this.userId = userId;
        this.name= name;
        this.subject = subject;
        this.profileImageUrl = profileImageUrl;

    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getSubject(){ return subject; }
    public void setSubject(String subject){ this.subject = subject; }

    public String getProfileImageUrl(){ return  profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }
}
