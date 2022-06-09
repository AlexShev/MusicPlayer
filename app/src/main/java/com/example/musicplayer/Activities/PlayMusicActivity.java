package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.Components.MusicLikeButton;
import com.example.musicplayer.Components.MusicPlayModeButton;
import com.example.musicplayer.Components.MusicPlayStopButton;
import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.MusicFile;
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

    private MutableLiveData<MusicFile> current = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        this._circleButton = this.findViewById(R.id.circle_button_play);
        musicPlayModeButton = new MusicPlayModeButton(this._circleButton);

        title = findViewById(R.id.music_title_play);
        subtitle = findViewById(R.id.music_author_play);
        imageView = findViewById(R.id.image);

        this._loveButton = this.findViewById(R.id.music_like_play);
        musicLikeButton = new MusicLikeButton(this._loveButton, false);

        this._playButton = this.findViewById(R.id.play_pause_button_play);
        musicPlayStopButton = new MusicPlayStopButton(_playButton, MusicPlayStopButton.MusicPlayState.pause);

        current.observe(this, new Observer<MusicFile>() {
            @Override
            public void onChanged(MusicFile musicFile) {
                title.setText(musicFile.getTitle());
                subtitle.setText(musicFile.getSubTitle());

                Picasso.get()
                        .load(musicFile.getPicturePath())
                        .placeholder(R.drawable.ic_music)
                        .error(R.drawable.ic_music)
                        .centerCrop()
//                        .resize(120, 120) // изменяем размер картинки до указанной ширины и высоты
                        .transform(new RoundedCornersTransformation(10, 0)) // указываем градус, на который следует повернуть картинку
                        .into(imageView);
            }
        });

        current.setValue(ContentController.Repository.getValue().getCurrent());
    }
}