package com.example.musicplayer.authorization;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class tokenResponse implements Serializable
{
    @SerializedName("token")
    private String token;

    public void setToken(String token) { this.token = token; }
    public String getToken() { return token; }

    public tokenResponse() { }
}
