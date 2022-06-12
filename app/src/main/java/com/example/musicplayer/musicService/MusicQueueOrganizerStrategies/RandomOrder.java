package com.example.musicplayer.musicService.MusicQueueOrganizerStrategies;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer.Data.MusicFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

// надо доделать логику со следующим предыдущим
public class RandomOrder implements MusicQueueOrganizer
{
    private static Random RANDOM = new Random();

    private final MutableLiveData<List<MusicFile>> tracks;

    private final Stack<Integer> historyPrevious;
    private final Stack<Integer> historyNext;

    private int currentItemIndex;
    private int maxIndex;

    public RandomOrder(MutableLiveData<List<MusicFile>> tracks, int currentItemIndex) {
        this.tracks = tracks;

        maxIndex = tracks.getValue().size();
        this.currentItemIndex = currentItemIndex;
        historyPrevious = new Stack<>();
        historyNext = new Stack<>();
    }

    @Override
    public MusicFile getCurrent() {
        return tracks.getValue().get(currentItemIndex);
    }

    @Override
    public MusicFile getNext() {
        if (!historyNext.isEmpty())
        {
            MusicFile cur = getCurrent();
            cur.setActive(false);

            historyPrevious.add(currentItemIndex);
            currentItemIndex = historyNext.pop();

            getCurrent().setActive(true);
            return getCurrent();
        }
        else
        {
            MusicFile cur = getCurrent();
            cur.setActive(false);

            historyPrevious.add(currentItemIndex);
            currentItemIndex = RANDOM.nextInt(maxIndex);

            MusicFile file = getCurrent();
            file.setActive(true);
            return getCurrent();
        }
    }

    @Override
    public MusicFile getPreviousByOrder() {
        if (!historyPrevious.isEmpty())
        {
            historyNext.add(currentItemIndex);
            currentItemIndex = historyPrevious.pop();
        }

        getCurrent().setActive(true);
        return getCurrent();
    }

    @Override
    public MusicFile getNextByOrder() {
        return getNext();
    }

    @Override
    public void setCurrent(int index) {
        if (tracks.getValue().size() != 0 && currentItemIndex != index && currentItemIndex > -1) {
            MusicFile cur = getCurrent();
            cur.setActive(false);

            historyPrevious.add(currentItemIndex);
            historyNext.clear();
        }

        currentItemIndex = index;
    }

    @Override
    public int getCurrentIndex() {
        return currentItemIndex;
    }
}
