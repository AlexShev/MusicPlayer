package com.example.musicplayer.musicService;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.musicplayer.Activities.MainActivity;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.MusicFileLouder;

public class MusicPlayer
{
    public final MutableLiveData<MusicRepository> Repository;
    public final MutableLiveData<MusicFile> Current;

    private MediaPlayer mediaPlayer;
    private Context context;

    public MusicPlayer(MutableLiveData<MusicRepository> repository,Context context) {
        Repository = repository;
        Current = new MutableLiveData<>();
        this.context = context;
    }

    public void start() {
        MusicFile musicFile = Repository.getValue().getCurrent();

        MutableLiveData<String> stringUri = new MutableLiveData<>();

        new MusicFileLouder(context, stringUri, musicFile.getPath()).start();

        stringUri.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Uri uri = Uri.parse(s);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                mediaPlayer = MediaPlayer.create(context, uri);
                mediaPlayer.start();
            }
        });
    }

    public void resum()
    {
        mediaPlayer.start();
    }

    public void stop()
    {
        mediaPlayer.stop();
    }

    public void dispose()
    {
        mediaPlayer.stop();
    }

}
