package com.example.stmeet.java_display;

import android.widget.RatingBar;

public class JavaDisplayObject {
    private String userId;
    private String name;
    private String subject;
    private String profileImageUrl;
    private String rating;

    public JavaDisplayObject(String userId, String name, String subject, String profileImageUrl, String rating){ //, RatingBar rating
        this.userId = userId;
        this.name = name;
        this.subject = subject;
        this.profileImageUrl = profileImageUrl;
        this.rating = rating;
    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getSubject(){ return subject; }
    public void setSubject(String subject){ this.subject = subject; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }

    public String getRating(){ return rating; }
    public void setRating(String rating){ this.rating = rating; }
}
