package com.example.musicplayer.musicService;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.DirectOrder;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.MusicQueueOrganizer;

import java.util.List;

public class MusicRepository
{
    private final MutableLiveData<List<MusicFile>> tracks;
    private MusicQueueOrganizer organizer;

    public MusicRepository(MutableLiveData<List<MusicFile>> tracks) {
        this.tracks = tracks;
        organizer = new DirectOrder(tracks);
    }

    public MusicFile getNext() {
        return organizer.getNext();
    }

    public MusicFile getCurrent() {
        return organizer.getCurrent();
    }

    public MusicFile getPreviousByOrder()
    {
        return organizer.getPreviousByOrder();
    }

    public MusicFile getNextByOrder()
    {
        return organizer.getNextByOrder();
    }


    public MusicFile setCurrent(int index) {
        return organizer.setCurrent(index);
    }
}
