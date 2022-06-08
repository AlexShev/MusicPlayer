package com.example.musicplayer.Activities.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ArtistFile  implements Serializable, ItemDrawable
{
    public ArtistFile() {}

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count = -1;

    @SerializedName("imgPath")
    private String imgPath;

    @Override
    public String getTitle() {
        return name != null ? getName() : "автор неизвестен";
    }

    @Override
    public String getSubTitle() {
        return count != -1 ? getCount() : "нет информации";
    }

    @Override
    public String getPicturePath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count == 0 || count >= 5 ? count + " треков" : count + " трека";
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "ArtistFile{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
