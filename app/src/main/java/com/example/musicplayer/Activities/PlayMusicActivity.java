package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.MutableLiveData;

import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.musicplayer.Components.MusicLikeButton;
import com.example.musicplayer.Components.MusicPlayModeButton;
import com.example.musicplayer.Components.MusicPlayStopButton;
import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.Senders.LikeSender;
import com.example.musicplayer.Senders.MusicFileLouder;
import com.example.musicplayer.R;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class PlayMusicActivity extends AppCompatActivity {

    private TextView title;
    private TextView subtitle;

    private GestureDetectorCompat lSwipeDetector;

    private ImageView imageView;

    private MusicPlayModeButton musicPlayModeButton;
    private MusicLikeButton musicLikeButton;

    private MusicPlayStopButton musicPlayStopButton;
    private ImageButton prev;
    private ImageButton next;

    private ViewSwitcher viewSwitcher;
    Animation slide_in_left, slide_out_right;
    Animation slide_out_left, slide_in_right;

    private SeekBar seekBar;
    private TextView durationPlayed;
    private TextView durationTotal;

    static MediaPlayer mediaPlayer;
    static Thread loaded;

    private Timer timer = new Timer();

    private boolean byOrder = true;
    int i;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;


    private MutableLiveData<String> currDer = new MutableLiveData<>();
    private MutableLiveData<String> maxDer = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();

        currDer.observe(this, s -> durationPlayed.setText(s));
        maxDer.observe(this, s -> durationTotal.setText(s));
        position.observe(this, curr -> seekBar.setProgress(curr));

        MusicFile file = ContentController.Repository.getValue().getCurrent();

        if (!file.isActive())
            getIntentMethod();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        setMetaData();

        musicPlayModeButton.setOnClickListener(ContentController.Repository.getValue()::setMusicQueueOrganizer);

        musicLikeButton.setOnClickListener(state -> {
            MusicFile file1 = ContentController.Repository.getValue().getCurrent();

            switch (state) {
                case like:
                    file1.setLiked(true);
                    break;
                case unlike:
                    file1.setLiked(false);
                    break;
            }

            new LikeSender(PlayMusicActivity.this, file1.getId()).start();
        });

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
            }
        });

        next.setOnClickListener((view) -> {
            freeMusicPlayer();
            byOrder = false;
            ContentController.Repository.getValue().getNextByOrder();

            getIntentMethod();
            setMetaData();
        });

        prev.setOnClickListener((view) ->  {
            if (ContentController.Repository.getValue().getCurrent() != ContentController.Repository.getValue().getPreviousByOrder()) {
                freeMusicPlayer();
                byOrder = false;

                getIntentMethod();
                setMetaData();
            }
        });

        viewSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });
    }

    private String formatedText(int currPos) {
        return String.format("%02d:%02d", currPos / 60, currPos % 60);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int curr = mediaPlayer.getCurrentPosition() / 1000;

                    currDer.postValue(formatedText(curr));
                    position.postValue(curr);
                }
            }
        },0,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null)
            timer.cancel();
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

    private void init() {
        ImageButton circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(circleButton, ContentController.Repository.getValue().getOrganizerMode());

        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        viewSwitcher = findViewById(R.id.switcher_play);

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

        slide_in_left = AnimationUtils.loadAnimation(PlayMusicActivity.this,
                android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(PlayMusicActivity.this,
                android.R.anim.slide_out_right);
        viewSwitcher.setInAnimation(slide_in_left);
        viewSwitcher.setOutAnimation(slide_out_right);
    }

    private void getIntentMethod() {
        freeMusicPlayer();

        MusicFile musicFile = ContentController.Repository.getValue().getCurrent();
        musicFile.setActive(true);

        if (musicFile.getCashUri() == null) {
            new MusicFileLouder(
                    PlayMusicActivity.this,
                    musicFile,
                    () -> initMediaPlayer(musicFile)
            ).start();
        } else {
            initMediaPlayer(musicFile);
        }
    }

    private void initMediaPlayer(MusicFile musicFile) {
        loaded = new Thread(() -> {
            mediaPlayer = MediaPlayer.create(PlayMusicActivity.this, musicFile.getCashUri());

            mediaPlayer.setOnCompletionListener(this::onCompletion);
            loaded = null;

            runOnUiThread(() -> {
                durationTotal.setText(formatedText(mediaPlayer.getDuration() / 1000));
                seekBar.setMax(mediaPlayer.getDuration() / 1000);

                musicPlayStopButton.performClick();
            });

            mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
                seekBar.setSecondaryProgress(percent);
            });

        });

        loaded.start();
    }

    private void setMetaData() {
        MusicFile musicFile = ContentController.Repository.getValue().getCurrent();

        Picasso.get()
                .load(musicFile.getPicturePath())
                .placeholder(R.drawable.ic_music)
                .error(R.drawable.ic_music)
                .centerCrop()
                .resize(800, 800) // изменяем размер картинки до указанной ширины и высоты
                .transform(new RoundedCornersTransformation(30, 0)) // указываем градус, на который следует повернуть картинку
                .into(imageView);

        title.setText(musicFile.getTitle());
        subtitle.setText(musicFile.getSubTitle());

        musicLikeButton.changeState(musicFile.getLiked());

        if (mediaPlayer != null)
        {
            int max = mediaPlayer.getDuration() / 1000;
            seekBar.setMax(max);

            int curr = mediaPlayer.getCurrentPosition() / 1000;
            seekBar.setProgress(curr);

            durationPlayed.setText(formatedText(curr));
            durationTotal.setText(formatedText(max));

//            mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
//                seekBar.setSecondaryProgress(percent);
//            });
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        freeMusicPlayer();

        if (byOrder) {
            ContentController.Repository.getValue().getNext();
        }

        byOrder = true;
        getIntentMethod();
        setMetaData();
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
                viewSwitcher.showPrevious();
                return false;
            }
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
                viewSwitcher.showNext();
                return false;
            }
            return false;
        }
    }
}




