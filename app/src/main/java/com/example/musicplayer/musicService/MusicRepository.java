package com.example.musicplayer.musicService;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Components.MusicPlayModeButton;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.CircleOrder;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.DirectOrder;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.MusicQueueOrganizer;
import com.example.musicplayer.musicService.MusicQueueOrganizerStrategies.RandomOrder;

import java.util.List;

public class MusicRepository
{
    private final MutableLiveData<List<MusicFile>> tracks;
    private MusicQueueOrganizer organizer;

    private MusicPlayModeButton.PlayModeStates organizerMode;

    public MusicRepository(MutableLiveData<List<MusicFile>> tracks) {
        this.tracks = tracks;
        organizerMode = MusicPlayModeButton.PlayModeStates.normal;
        organizer = new DirectOrder(tracks, -1);
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

    public void setCurrent(int index) {
        organizer.setCurrent(index);
    }

    public int getCurrentIndex() {
        return organizer.getCurrentIndex();
    }

    public void setMusicQueueOrganizer(MusicPlayModeButton.PlayModeStates mode)
    {
        int currentItemIndex = getCurrentIndex();
        switch (mode) {
            case random:
                organizer = new RandomOrder(tracks, currentItemIndex);
                organizerMode = MusicPlayModeButton.PlayModeStates.random;
                break;
            case circle:
                organizer = new CircleOrder(tracks, currentItemIndex);
                organizerMode = MusicPlayModeButton.PlayModeStates.circle;
                break;
            case normal:
                organizer = new DirectOrder(tracks, currentItemIndex);
                organizerMode = MusicPlayModeButton.PlayModeStates.normal;
                break;
        }
    }

    public MusicPlayModeButton.PlayModeStates getOrganizerMode() {
        return organizerMode;
    }
}
