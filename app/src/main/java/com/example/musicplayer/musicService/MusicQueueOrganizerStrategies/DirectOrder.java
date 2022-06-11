package com.example.musicplayer.musicService.MusicQueueOrganizerStrategies;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Data.MusicFile;

import java.util.List;

public class DirectOrder implements MusicQueueOrganizer
{
    private final MutableLiveData<List<MusicFile>> tracks;

    private int currentItemIndex;
    private int maxIndex;

    public DirectOrder(MutableLiveData<List<MusicFile>> tracks, int currentItemIndex) {
        this.tracks = tracks;

        maxIndex = tracks.getValue().size() - 1;
        this.currentItemIndex = currentItemIndex;
    }

    @Override
    public MusicFile getCurrent() {
        return tracks.getValue().get(currentItemIndex);
    }

    @Override
    public MusicFile getNext() {
        getCurrent().setActive(false);
        if (currentItemIndex == maxIndex)
            currentItemIndex = 0;
        else
            currentItemIndex++;

        MusicFile file = getCurrent();
        file.setActive(true);
        return getCurrent();
    }

    @Override
    public MusicFile getPreviousByOrder() {
        getCurrent().setActive(false);
        if (currentItemIndex == 0)
            currentItemIndex = maxIndex;
        else
            currentItemIndex--;

        MusicFile file = getCurrent();
        file.setActive(true);
        return getCurrent();
    }

    @Override
    public MusicFile getNextByOrder() {
        return getNext();
    }

    @Override
    public void setCurrent(int index) {
        if (tracks.getValue().size() != 0) {
            if (currentItemIndex > -1 && currentItemIndex != index)
                getCurrent().setActive(false);
        }
        currentItemIndex = index;
    }

    @Override
    public int getCurrentIndex() {
        return currentItemIndex;
    }
}
