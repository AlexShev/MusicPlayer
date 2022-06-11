package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.Components.MusicLikeButton;
import com.example.musicplayer.Components.MusicPlayModeButton;
import com.example.musicplayer.Components.MusicPlayStopButton;
import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.Senders.LikeSender;
import com.example.musicplayer.Senders.MusicFileLouder;
import com.example.musicplayer.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class PlayMusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private TextView title;
    private TextView subtitle;

    private ImageView imageView;

    private MusicPlayModeButton musicPlayModeButton;
    private MusicLikeButton musicLikeButton;

    private MusicPlayStopButton musicPlayStopButton;
    private ImageButton prev;
    private ImageButton next;

    private SeekBar seekBar;
    private TextView durationPlayed;
    private TextView durationTotal;

    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private Thread playTread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();

        MusicFile file = ContentController.Repository.getValue().getCurrent();

        if (!file.isActive())
            getIntentMethod();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setMetaData();

        musicPlayModeButton.setOnClickListener(ContentController.Repository.getValue()::setMusicQueueOrganizer);

        musicLikeButton.setOnClickListener(new MusicLikeButton.IOnClickListener() {
            @Override
            public void onClick(MusicLikeButton.MusicLikeStates state) {
                MusicFile file = ContentController.Repository.getValue().getCurrent();

                switch (state) {
                    case like:
                        file.setLiked(true);
                        break;
                    case unlike:
                        file.setLiked(false);
                        break;
                }

                new LikeSender(PlayMusicActivity.this, file.getId()).start();
            }
        });

    //    playTreadBtn();
    }

    @Override
    protected void onResume() {
        playTreadBtn();
        prevTreadBtn();
        nextThreadBtn();

        super.onResume();
    }

    private void prevTreadBtn() {
        prevThread = new Thread(()-> {
            prev.setOnClickListener((view)->
            {
                freeMusicPlayer();
                ContentController.Repository.getValue().getPreviousByOrder();

                getIntentMethod();
                setMetaData();
            });
        });
        prevThread.start();
    }

    private void nextThreadBtn() {
        nextThread = new Thread(()-> {
            next.setOnClickListener((view)->
            {
                freeMusicPlayer();
                ContentController.Repository.getValue().getNextByOrder();

                getIntentMethod();
                setMetaData();
            });
        });
        nextThread.start();
    }

    private void freeMusicPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playTreadBtn() {
//        playTread = new Thread(()-> {
            musicPlayStopButton.setOnClickListener(state -> {
            if (mediaPlayer != null) {
                switch (state) {
                    case play:
                        mediaPlayer.start();
                        break;
                    case pause:
                        mediaPlayer.pause();
                        break;
                }
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
             }
        });
//        this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    int currPos = mediaPlayer.getCurrentPosition() / 1000;
//                    seekBar.setProgress(currPos);
//                    durationPlayed.setText(formatedText(currPos));
//                }
//                handler.postDelayed(this, 500);
//            }
//        });

//    });
//        playTread.start();
    }

    private void init() {
        ImageButton circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(circleButton);

        title = findViewById(R.id.music_title_play);
        subtitle = findViewById(R.id.music_author_play);
        imageView = findViewById(R.id.image);

        ImageButton loveButton = this.findViewById(R.id.music_like_play);
        musicLikeButton = new MusicLikeButton(loveButton, false);

        ImageButton playButton = this.findViewById(R.id.play_pause_button_play);

        if (mediaPlayer != null && mediaPlayer.isPlaying())
            musicPlayStopButton = new MusicPlayStopButton(playButton, MusicPlayStopButton.MusicPlayState.play);
        else
            musicPlayStopButton = new MusicPlayStopButton(playButton, MusicPlayStopButton.MusicPlayState.pause);

        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);

        seekBar = findViewById(R.id.seekbar);
        durationPlayed = findViewById(R.id.duration_played);
        durationTotal = findViewById(R.id.duration_total);
    }

    private void getIntentMethod() {
        freeMusicPlayer();

        MusicFile musicFile = ContentController.Repository.getValue().getCurrent();
        musicFile.setActive(true);

        if (musicFile.getCashUri() == null) {
            MutableLiveData<String> uri = new MutableLiveData<>();

            uri.observe(this, s -> {
                System.out.println(s);
                musicFile.setCashUri(Uri.parse(s));
                initMediaPlayer(musicFile);
            });

            new MusicFileLouder(PlayMusicActivity.this, uri, musicFile.getPath()).start();
        }
        else
        {
            initMediaPlayer(musicFile);
        }
    }

    private void initMediaPlayer(MusicFile musicFile) {
        new Thread(() -> {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), musicFile.getCashUri());
            System.out.println(mediaPlayer);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);

            runOnUiThread(() -> {
                durationTotal.setText(formatedText(mediaPlayer.getDuration() / 1000));
                musicPlayStopButton.performClick();
            });

//
//            if (musicPlayStopButton.getState() == MusicPlayStopButton.MusicPlayState.play) {
//                mediaPlayer.start();
//            }
        }).start();
    }

    private String formatedText(int currPos) {
        return String.format("%02d:%02d", currPos / 60, currPos % 60);
    }

    private void setMetaData()
    {
        MusicFile musicFile = ContentController.Repository.getValue().getCurrent();

        Picasso.get()
                .load(musicFile.getPicturePath())
                .placeholder(R.drawable.ic_music)
                .error(R.drawable.ic_music)
                .centerCrop()
                .resize(800, 800) // изменяем размер картинки до указанной ширины и высоты
                .transform(new RoundedCornersTransformation(10, 0)) // указываем градус, на который следует повернуть картинку
                .into(imageView);

        title.setText(musicFile.getTitle());
        subtitle.setText(musicFile.getSubTitle());

        musicLikeButton.changeState(musicFile.getLiked());
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        ContentController.Repository.getValue().getNext();
        getIntentMethod();
        setMetaData();
    }
}

