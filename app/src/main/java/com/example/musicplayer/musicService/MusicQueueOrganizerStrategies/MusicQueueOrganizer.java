package com.example.musicplayer.musicService.MusicQueueOrganizerStrategies;

import com.example.musicplayer.Data.MusicFile;

public interface MusicQueueOrganizer
{
    // по логике
    MusicFile getCurrent();
    MusicFile getNext();

    // следующий при нажатии на кнопку
    MusicFile getPreviousByOrder();
    MusicFile getNextByOrder();


    void setCurrent(int index);
}

