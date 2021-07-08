package com.example.stmeet.db_display;

public class DbDisplayObject {
    private String userId;
    private String name;
    private String hourlyRate;
    private String profileImageUrl;
    private String rating;

    public DbDisplayObject(String userId, String name, String hourlyRate, String profileImageUrl, String rating){ //, RatingBar rating
        this.userId = userId;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.profileImageUrl = profileImageUrl;
        this.rating = rating;
    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getHourlyRate(){ return hourlyRate; }
    public void setHourlyRate(String hourlyRate){ this.hourlyRate = hourlyRate; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }

    public String getRating(){ return rating; }
    public void setRating(String rating){ this.rating = rating; }
}
