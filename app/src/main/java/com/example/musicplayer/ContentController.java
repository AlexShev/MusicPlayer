package com.example.musicplayer;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Data.ArtistFile;
import com.example.musicplayer.Data.MusicFile;

import java.util.ArrayList;
import java.util.List;

public class ContentController
{
    private static final MutableLiveData<List<MusicFile>> recommendedTracks;
    private static final MutableLiveData<List<ArtistFile>> recommendedAuthors;
    private static final MutableLiveData<List<MusicFile>> popularTracks;

    enum ListType
    {
        recommendedTrack("musicList", "music/list"),
        recommendedAuthors("authorList", "author/list"),
        popularTracks("popularList","music/list/popular");

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
        popularTracks.setValue(new ArrayList<>(temp));
    }

    public static void postRecommendedTrack(List<MusicFile> date)
    {
        recommendedTracks.postValue(date);
    }

    public static void postRecommendedAuthors(List<ArtistFile> date)
    {
        recommendedAuthors.postValue(date);
    }

    public static void postPopularTrack(List<MusicFile> date)
    {
        popularTracks.postValue(date);
    }

    public static MutableLiveData<List<MusicFile>> getRecommendedTracks()
    {
        return recommendedTracks;
    }

    public static MutableLiveData<List<ArtistFile>> getRecommendedAuthors()
    {
        return recommendedAuthors;
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
                postPopularTrack((List<MusicFile>)(list));
        }
    }
}
