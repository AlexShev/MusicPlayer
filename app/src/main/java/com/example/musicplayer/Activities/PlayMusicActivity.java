package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.R;

public class PlayMusicActivity extends AppCompatActivity {

    private ImageButton _circleButton;
    private TextView title;
    private TextView subtitle;

    private MutableLiveData<MusicFile> current = new MutableLiveData<>();

    private MusicPlayModeButton musicPlayModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        this._circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(this._circleButton);


        title = findViewById(R.id.music_title_play);
        subtitle = findViewById(R.id.music_author_play);

        current.setValue(ContentController.Repository.getValue().getCurrent());
        current.observe(this, new Observer<MusicFile>() {
            @Override
            public void onChanged(MusicFile musicFile) {
                title.setText(musicFile.getTitle());
                subtitle.setText(musicFile.getSubTitle());
            }
        });
    }
}