package com.example.musicplayer.Data;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public interface ItemDrawable
{
    String getTitle();
    String getSubTitle();

    String getPicturePath();

    boolean isActive();
    void setActive(boolean state);

    void setObserver(LifecycleOwner owner, Observer<Boolean> observer);
}
