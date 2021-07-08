package com.example.stmeet.java_display;

public class JavaDisplayObject {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String rating;
    private String hourlyRate;

    public JavaDisplayObject(String userId, String name, String profileImageUrl, String rating, String hourlyRate){ //, RatingBar rating
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }

    public String getRating(){ return rating; }
    public void setRating(String rating){ this.rating = rating; }

    public String getHourlyRate(){ return hourlyRate; }
    public void setHourlyRate(String hourlyRate){ this.hourlyRate = hourlyRate; }
}
