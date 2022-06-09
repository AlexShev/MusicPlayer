package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.musicplayer.Components.MusicLikeButton;
import com.example.musicplayer.Components.MusicPlayModeButton;
import com.example.musicplayer.R;

public class PlayMusicActivity extends AppCompatActivity {

    private ImageButton _circleButton;
    private ImageButton _loveButton;


    private MusicPlayModeButton musicPlayModeButton;
    private MusicLikeButton musicLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        this._circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(this._circleButton);

        this._loveButton = this.findViewById(R.id.music_like_play);
        musicLikeButton = new MusicLikeButton(this._loveButton, false);
    }
}