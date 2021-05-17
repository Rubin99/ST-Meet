package com.example.stmeet.matches;

public class MatchesObject {
    private String userId;

    public MatchesObject(String userId){
        this.userId = userId;
    }

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId; }
}
