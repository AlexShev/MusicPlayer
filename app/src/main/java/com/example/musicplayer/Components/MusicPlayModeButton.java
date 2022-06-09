package com.example.musicplayer.Components;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.musicplayer.R;

public class MusicPlayModeButton {

    public enum PlayModeStates {
        random(R.drawable.ic_circle_random,0),
        circle(R.drawable.ic_circle_single,1),
        normal(R.drawable.ic_circle,2);

        static public int stateCount = 3;

        final private int _picture;
        final private int _numberState;

        public int getPicture(){
            return this._picture;
        }

        public int getNumberState(){
            return this._numberState;
        }

        PlayModeStates(int picture, int numberState){
            this._picture = picture;
            this._numberState = numberState;
        }
    }

    public interface IOnClickListener {
        void onClick(PlayModeStates state);
    }


    final private ImageButton _button;

    private PlayModeStates _state;
    private IOnClickListener _listener;

    public MusicPlayModeButton(ImageButton button){
        this._button = button;
        this._state = PlayModeStates.normal;
        this._listener = new IOnClickListener() {
            @Override
            public void onClick(PlayModeStates state) {
            }
        };
        this._button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberState = _state.getNumberState();
                switch((numberState + 1) % PlayModeStates.stateCount){
                    case 0: _state = PlayModeStates.random; break;
                    case 1: _state = PlayModeStates.circle; break;
                    case 2: _state = PlayModeStates.normal; break;
                }

                changeImage();
                _listener.onClick(_state);
            }
        });
    }

    public PlayModeStates getState(){
        return this._state;
    }

    public void setOnClickListener(IOnClickListener onClickListener){
        this._listener = onClickListener;
    }

    private void changeImage(){
        _button.setImageResource(_state.getPicture());
    }

}
