package com.example.musicplayer.Senders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.ArtistFile;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.restAPI.ApiClient;
import com.example.musicplayer.restAPI.ApiService;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeSender extends Thread
{
    public LikeSender(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    private Context context;
    private String token;
    private String id;

    @Override
    public synchronized void run()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        token = preferences.getString("token", "");

        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiInterface.setMusicReaction(id, "Bearer "+token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (!response.isSuccessful())
                {

                }
                else {
                    try {
                        String responseJSON = response.body().string();

                        System.out.println(responseJSON);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                t.printStackTrace();
            }
        });


    }

}
