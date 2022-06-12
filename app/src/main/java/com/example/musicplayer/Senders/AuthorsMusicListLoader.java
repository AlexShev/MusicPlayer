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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorsMusicListLoader extends Thread
{
//    private MutableLiveData<Boolean> isNormal;

    public AuthorsMusicListLoader(/*MutableLiveData<Boolean> isNormal, */Context context, String id) {
        // this.isNormal = isNormal;
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

        loadList();
    }

    private void loadList()
    {
        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);

        Call<ResponseBody> call = apiInterface.getList(ContentController.ListType.authorsTrack.getPath(), id ,"Bearer "+token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (!response.isSuccessful())
                {
//                    isNormal.setValue(false);
                }
                else {
                    try {
                        String responseJSON = response.body().string();

                        JsonParser jsonParser = new JsonParser();
                        JsonObject jo = (JsonObject) jsonParser.parse(responseJSON);
                        JsonArray jArray = jo.getAsJsonArray(ContentController.ListType.authorsTrack.getType()); // get json array

                        Gson gJson = new Gson();
                        List<MusicFile> jsonObjArrayList = gJson.fromJson(jArray, new TypeToken<ArrayList<MusicFile>>() {}.getType());

                        for (Object o : jsonObjArrayList)
                            System.out.println(o);

                        ContentController.postAuthorTracks(jsonObjArrayList);

//                        isNormal.setValue(true);
                    }
                    catch (IOException e)
                    {
//                        isNormal.setValue(false);
                        e.printStackTrace();
                        System.out.println("gbplf");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
//                isNormal.setValue(false);
                t.printStackTrace();
            }
        });
    }


}
