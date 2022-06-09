package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.musicplayer.R;

public class PlayMusicActivity extends AppCompatActivity {

    private ImageButton _circleButton;


    private MusicPlayModeButton musicPlayModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        this._circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(this._circleButton);
    }
}