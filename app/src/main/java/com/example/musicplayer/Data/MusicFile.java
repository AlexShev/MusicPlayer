package com.example.musicplayer.Data;

import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MusicFile implements Serializable, ItemDrawable
{
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("file")
    private String path;

    @SerializedName("author")
    private String author;

    @SerializedName("imgPath")
    private String imagePath;

    @SerializedName("kind")
    private String kind;

    @SerializedName("liked")
    private boolean liked;

    public MusicFile() { }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "MusicFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", author='" + author + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", kind='" + kind + '\'' +
                ", liked=" + liked +
                '}';
    }

    @Override
    public String getTitle() {
        return name != null ? name : "без названия";
    }

    @Override
    public String getSubTitle() { return author != null ? author : "аноним"; }

    @Override
    public String getPicturePath() {
        return imagePath;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public final MutableLiveData<Boolean> isPlaying = new MutableLiveData<>(false);

    @Override
    public boolean isActive() {
        return isPlaying.getValue();
    }

    @Override
    public void setActive(boolean state) {
        isPlaying.setValue(state);
    }

    @Override
    public void setObserver(LifecycleOwner owner, Observer<Boolean> observer)
    {
        isPlaying.observe(owner, observer);
    }

    private Uri cashUri = null;

    public Uri getCashUri() {
        return cashUri;
    }

    public void setCashUri(Uri cashUri) {
        this.cashUri = cashUri;
    }


}
