package com.example.musicplayer.Components;

import android.view.View;
import android.widget.ImageButton;

import com.example.musicplayer.R;

public class MusicPlayStopButton {

    public enum MusicPlayState {
        play(R.drawable.ic_round, 1),
        pause(R.drawable.ic_round_play, 0);

        private int _state;
        private int _picture;

        MusicPlayState(int picture, int state){
            this._state = state;
            this._picture = picture;
        }

        public int getState(){
            return this._state;
        }

        public int getPicture(){
            return this._picture;
        }
    }

    private MusicPlayState _state;
    private ImageButton _button;

    public MusicPlayStopButton(ImageButton button, MusicPlayState musicPlayState){
        this._button = button;
        this._state = musicPlayState;

        this._button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(_state.getState()){
                    case 0: _state = MusicPlayState.play; break;
                    case 1: _state = MusicPlayState.pause; break;
                }

                changePicture();
            }
        });
    }

    public MusicPlayState getState(){
        return this._state;
    }

    private void changePicture(){
        this._button.setImageResource(this._state.getPicture());
    }
}
