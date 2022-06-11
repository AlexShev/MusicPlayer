package com.example.musicplayer;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.musicplayer.Data.ArtistFile;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.musicService.MusicRepository;
import com.example.musicplayer.musicService.MusicPlayer;


import java.util.ArrayList;
import java.util.List;

public class ContentController
{
    public static final MutableLiveData<MusicRepository> Repository;
    public static final MutableLiveData<MusicPlayer> MusicPlayer;

    private static final MutableLiveData<List<MusicFile>> recommendedTracks;
    private static final MutableLiveData<List<ArtistFile>> recommendedAuthors;
    private static final MutableLiveData<List<MusicFile>> popularTracks;

    private static final MutableLiveData<List<MusicFile>> authorsTracks;

    private static ListType currType;

    public static ListType getCurrType() {
        return currType;
    }

    public static void setCurrType(ListType currType) {
        ContentController.currType = currType;
    }

    public enum ListType
    {
        recommendedTrack("musicList", "music/list"),
        recommendedAuthors("authorList", "author/list"),
        popularTracks("popularList","music/list/popular"),
        authorsTrack("musicList", "music/list/author");

        private String name;
        private String path;

        public String getType() { return name; }
        public String getPath() { return path; }


        ListType(String name, String path)
        {
            this.name = name;
            this.path = path;
        }

    }

    static {
        List<MusicFile> temp = new ArrayList<>(3);
        temp.add(new MusicFile());
        temp.add(new MusicFile());
        temp.add(new MusicFile());
        temp.add(new MusicFile());
        temp.add(new MusicFile());

        List<ArtistFile> tempArt = new ArrayList<>(3);
        tempArt.add(new ArtistFile());
        tempArt.add(new ArtistFile());
        tempArt.add(new ArtistFile());
        tempArt.add(new ArtistFile());
        tempArt.add(new ArtistFile());

        recommendedTracks = new MutableLiveData<>();
        recommendedTracks.setValue(temp);

        recommendedAuthors = new MutableLiveData<>();
        recommendedAuthors.setValue(tempArt);

        popularTracks = new MutableLiveData<>();
        popularTracks.setValue(temp);

        authorsTracks = new MutableLiveData<>();
        authorsTracks.setValue(new ArrayList<>());

        Repository = new MutableLiveData<>();
        MusicPlayer = new MutableLiveData<>();
    }

//    public static void observRepository(LifecycleOwner lifecycleOwner)
//    {
//        Repository.observe(lifecycleOwner, new Observer<MusicRepository>() {
//            @Override
//            public void onChanged(MusicRepository musicRepository) {
//
//                if (MusicPlayer.getValue() != null)
//                    MusicPlayer.getValue().dispose();
//
//                MusicPlayer.setValue(new MusicPlayer(Repository, (Context) lifecycleOwner));
//            }
//        });
//    }

    public static void postRecommendedTrack(List<MusicFile> date)
    {
        recommendedTracks.postValue(date);
    }

    public static void postRecommendedAuthors(List<ArtistFile> date)
    {
        recommendedAuthors.postValue(date);
    }

    public static void postPopularTracks(List<MusicFile> date)
    {
        popularTracks.postValue(date);
    }

    public static void postAuthorTracks(List<MusicFile> date)
    {
        authorsTracks.postValue(date);
    }

    public static MutableLiveData<List<MusicFile>> getRecommendedTracks()
    {
        return recommendedTracks;
    }

    public static MutableLiveData<List<ArtistFile>> getRecommendedAuthors()
    {
        return recommendedAuthors;
    }

    public static MutableLiveData<List<MusicFile>> getAuthorsTracks()
    {
        return authorsTracks;
    }

    public static MutableLiveData<List<MusicFile>> getPopularTracks()
    {
        return popularTracks;
    }

    public static void postSetList(ListType type, List list)
    {
        switch (type) {
            case recommendedTrack:
                postRecommendedTrack((List<MusicFile>)(list));
                break;
            case recommendedAuthors:
                postRecommendedAuthors((List<ArtistFile>)list);
                break;
            case popularTracks:
                postPopularTracks((List<MusicFile>)(list));
                break;
            case authorsTrack:
                postAuthorTracks((List<MusicFile>)(list));
                break;
        }
    }


}
