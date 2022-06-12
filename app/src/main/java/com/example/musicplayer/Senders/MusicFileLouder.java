package com.example.musicplayer.Senders;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.restAPI.ApiClient;
import com.example.musicplayer.restAPI.ApiService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicFileLouder extends Thread
{
    private Context context;
    private MusicFile musicFile;
    private Runnable runnable;

    public MusicFileLouder(Context context, MusicFile musicFile, Runnable runnable) {
        this.context = context;
        this.musicFile = musicFile;
        this.runnable = runnable;
    }

    @Override
    public synchronized void run() {
        loadMusicURI();
    }

    public void loadMusicURI()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = preferences.getString("token", "");

        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);

        Call<ResponseBody> call = apiInterface.getMusic(musicFile.getPath() , "Bearer "+token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (!response.isSuccessful())
                {

                }
                else {
                    String responseJSON = null;
                    try {

                        if (response.body() != null) {
                            responseJSON = response.body().string();
                        } else {
                            responseJSON = "";
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    musicFile.setCashUri(Uri.parse(responseJSON));
                    runnable.run();
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
