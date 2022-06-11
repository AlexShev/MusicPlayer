package com.example.musicplayer.Senders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<String> uri;
    private String id;

    public MusicFileLouder(Context context, MutableLiveData<String> uri, String id) {
        this.context = context;
        this.uri = uri;
        this.id = id;
    }

    @Override
    public synchronized void run() {
        loadMusicURI(id);
    }

    public void loadMusicURI(String id)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = preferences.getString("token", "");

        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);

        Call<ResponseBody> call = apiInterface.getMusic(id , "Bearer "+token);

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

                        uri.postValue(responseJSON);
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
