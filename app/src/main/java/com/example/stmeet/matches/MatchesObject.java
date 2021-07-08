package com.example.stmeet.matches;

public class MatchesObject {
    private String userId;
    private String name;
    private String subject;
    private String profileImageUrl;
    private String hourlyRate;

    public MatchesObject(String userId, String name, String subject, String profileImageUrl, String hourlyRate){ //
        this.userId = userId;
        this.name = name;
        this.subject = subject;
        this.profileImageUrl = profileImageUrl;
        this.hourlyRate = hourlyRate;
    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getSubject(){ return subject; }
    public void setSubject(String subject){ this.subject = subject; }

    public String getHourlyRate(){ return hourlyRate; }
    public void setHourlyRate(String hourlyRate){ this.hourlyRate = hourlyRate; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }
}
