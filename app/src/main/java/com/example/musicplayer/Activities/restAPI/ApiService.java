package com.example.musicplayer.Activities.restAPI;

import com.example.musicplayer.Activities.Data.MusicFile;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService
{
    @FormUrlEncoded
    @POST("auth/login/")
    @Headers({
            "Accept: */*",
            "Content-Type: application/x-www-form-urlencoded",
    })
    Call<ResponseBody> getToken(@Field("login") String login, @Field("password") String password);

    @GET("{path}/")
    @Headers({
            "Accept: */*",
            "Content-Type: application/x-www-form-urlencoded",
           // "Authorization: Bearer {token}",
    })
    Call<ResponseBody> getList(@Path(value = "path", encoded = true) String path, @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("music/file/")
    @Headers({
            "Accept: */*",
            "Content-Type: application/x-www-form-urlencoded",
            // "Authorization: Bearer {token}",
    })
    Call<ResponseBody> getMusic(@Field("id") String id, @Header("Authorization") String auth);


}
