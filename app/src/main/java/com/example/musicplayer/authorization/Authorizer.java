package com.example.musicplayer.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.restAPI.ApiClient;
import com.example.musicplayer.restAPI.ApiService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorizer
{
    public static void autorotate(String login, String password, MutableLiveData<Boolean> isOK, Context context)
    {
        try {
            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<ResponseBody> srvLogin = service.getToken(login, password);
            srvLogin.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            String responseJSON = response.body().string();

                            Gson gson = new Gson();
                            tokenResponse obj = gson.fromJson(responseJSON, tokenResponse.class);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("login", login);
                            editor.putString("password", password);
                            editor.putString("token", obj.getToken());

                            editor.apply();

                            isOK.setValue(true);
                        } catch (IOException e) {
                            isOK.setValue(false);
                            e.printStackTrace();
                        }
                    }
                    else {
                        isOK.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    isOK.setValue(false);
                }
            });
        }
        catch (Exception e)
        {
            isOK.setValue(false);
            e.printStackTrace();
        }
    }
}
