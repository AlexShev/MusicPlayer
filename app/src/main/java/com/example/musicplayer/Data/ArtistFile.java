package com.example.musicplayer.Data;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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

    @SerializedName("_id")
    private String id;

    @Override
    public String getTitle() {
        return name != null ? getName() : "аноним";
    }

    @Override
    public String getSubTitle() {
        return count != -1 ? getCount() : "нет треков";
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public final MutableLiveData<Boolean> isChosen = new MutableLiveData<>(false);

    @Override
    public boolean isActive() {
        return isChosen.getValue();
    }

    @Override
    public void setActive(boolean state) {
        isChosen.setValue(state);
    }

    @Override
    public void setObserver(LifecycleOwner owner, Observer<Boolean> observer)
    {
        isChosen.observe(owner, observer);
    }
}
