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

    private final Queue<MusicFile> historyPrevious;
    private final Stack<MusicFile> historyNext;

    private int currentItemIndex = 0;
    private int maxIndex;

    public RandomOrder(MutableLiveData<List<MusicFile>> tracks) {
        this.tracks = tracks;

        maxIndex = tracks.getValue().size();

        historyPrevious = new LinkedList<>();
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

            MusicFile next = historyNext.pop();
            historyPrevious.add(next);
            next.setActive(true);
            return next;
        }
        else
        {
            return getNextByOrder();
        }
    }

    @Override
    public MusicFile getPreviousByOrder() {
        if (!historyPrevious.isEmpty())
        {
            MusicFile prev = historyPrevious.poll();
            historyNext.add(prev);

            return prev;
        }
        else
        {
            return getCurrent();
        }
    }

    @Override
    public MusicFile getNextByOrder() {
        MusicFile cur = getCurrent();
        cur.setActive(false);
        historyPrevious.add(cur);

        currentItemIndex = RANDOM.nextInt(maxIndex);

        MusicFile file = getCurrent();
        file.setActive(true);
        return getCurrent();
    }

    @Override
    public void setCurrent(int index) {
        if (tracks.getValue().size() != 0) {
            MusicFile cur = getCurrent();
            cur.setActive(false);
            historyPrevious.add(cur);
            historyNext.clear();
        }

        currentItemIndex = index;
    }
}
