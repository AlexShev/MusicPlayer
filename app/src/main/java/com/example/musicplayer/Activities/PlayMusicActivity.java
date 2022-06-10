package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
import com.example.musicplayer.MusicFileLouder;
import com.example.musicplayer.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class PlayMusicActivity extends AppCompatActivity {

    private ImageButton _circleButton;
    private TextView title;
    private TextView subtitle;
    private ImageButton _loveButton;
    private ImageButton _playButton;

    private ImageView imageView;

    private MusicPlayModeButton musicPlayModeButton;
    private MusicLikeButton musicLikeButton;
    private MusicPlayStopButton musicPlayStopButton;

    private SeekBar seekBar;
    private TextView durationPlayed;
    private TextView durationTotal;

    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    // private MutableLiveData<MusicFile> current = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();
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

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currPos = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currPos);
                    durationPlayed.setText(formatedText(currPos));
                }
                handler.postDelayed(this, 1000);
            }
        });

        musicPlayStopButton.setOnClickListener(new MusicPlayStopButton.IOnClickListener() {
            @Override
            public void onClick(MusicPlayStopButton.MusicPlayState state) {
                switch (state) {
                    case play:
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }
                        break;
                    case pause:
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                        }
                        break;
                }
            }
        });

//        current.observe(this, new Observer<MusicFile>() {
//            @Override
//            public void onChanged(MusicFile musicFile) {
//                title.setText(musicFile.getTitle());
//                subtitle.setText(musicFile.getSubTitle());
//
//                Picasso.get()
//                        .load(musicFile.getPicturePath())
//                        .placeholder(R.drawable.ic_music)
//                        .error(R.drawable.ic_music)
//                        .centerCrop()
////                        .resize(120, 120) // изменяем размер картинки до указанной ширины и высоты
//                        .transform(new RoundedCornersTransformation(10, 0)) // указываем градус, на который следует повернуть картинку
//                        .into(imageView);
//            }
//        });
//
//        current.setValue(ContentController.Repository.getValue().getCurrent());

    }


    private void init() {
        this._circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(this._circleButton);

        title = findViewById(R.id.music_title_play);
        subtitle = findViewById(R.id.music_author_play);
        imageView = findViewById(R.id.image);

        this._loveButton = this.findViewById(R.id.music_like_play);
        musicLikeButton = new MusicLikeButton(this._loveButton, false);

        this._playButton = this.findViewById(R.id.play_pause_button_play);
        musicPlayStopButton = new MusicPlayStopButton(_playButton, MusicPlayStopButton.MusicPlayState.pause);

        seekBar = findViewById(R.id.seekbar);
        durationPlayed = findViewById(R.id.duration_played);
        durationTotal = findViewById(R.id.duration_total);

    }

    private void getIntentMethod() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        MutableLiveData<String> uri = new MutableLiveData<>();

        uri.observe(this, s -> {
                    System.out.println(s);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(s));
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration() / 1000);
        });

        new MusicFileLouder(PlayMusicActivity.this, uri, ContentController.Repository.getValue().getCurrent().getPath()).start();

    }

    private String formatedText(int currPos) {
        return String.format("%02d:%02d", currPos / 60, currPos % 60);
    }
}

