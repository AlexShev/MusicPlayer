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

    public interface IOnClickListener{
         void onClick(MusicPlayState state);
    }

    private MusicPlayState _state;
    private ImageButton _button;
    private IOnClickListener _listener;

    public MusicPlayStopButton(ImageButton button, MusicPlayState musicPlayState){
        this._button = button;
        this._state = musicPlayState;
        this._listener = new IOnClickListener() {
            @Override
            public void onClick(MusicPlayState state) {

            }
        };

        this._button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(_state.getState()){
                    case 0: _state = MusicPlayState.play; break;
                    case 1: _state = MusicPlayState.pause; break;
                }

                changePicture();
                _listener.onClick(_state);
            }
        });
    }

    public MusicPlayState getState(){
        return this._state;
    }

    public void setOnClickListener(IOnClickListener onClickListener){
        this._listener = onClickListener;
    }

    private void changePicture(){
        this._button.setImageResource(this._state.getPicture());
    }
}
