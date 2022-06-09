package com.example.musicplayer.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MusicFile implements Serializable, ItemDrawable
{
    @SerializedName("id")
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
    private Boolean liked;

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
        return name;
    }

    @Override
    public String getSubTitle() {
        return author;
    }

    @Override
    public String getPicturePath() {
        return imagePath;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    //    public String getImage() { return imagePath; }
//    public String getAuthor() { return author; }
//    public String getName() { return name; }
//    public String getPath() { return path; }
//
//    public void setImage(int image) { this.image = image; }
//    public void setAuthor(String author) { this.author = author; }
//    public void setName(String name) { this.name = name; }
//    public void setPath(String path) { this.path = path; }
}
