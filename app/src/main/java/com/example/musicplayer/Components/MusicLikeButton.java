package com.example.musicplayer.Components;

import android.view.View;
import android.widget.ImageButton;

import com.example.musicplayer.R;

public class MusicLikeButton {

    public enum MusicLikeStates{
        like(1, R.drawable.ic_love),
        unlike(0, R.drawable.ic_un_love);

        private int _state;
        private int _picture;

        MusicLikeStates(int state, int picture){
            this._state = state;
            this._picture = picture;
        }

        public int getState(){
            return _state;
        }

        public int getPicture(){
            return _picture;
        }
    }

    private ImageButton _button;
    private MusicLikeStates _state;

    public MusicLikeButton(ImageButton button, boolean isLiked){
        this._button = button;
        this._state = (isLiked) ? MusicLikeStates.like : MusicLikeStates.unlike;

        this._button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(_state.getState()){
                    case 0: _state = MusicLikeStates.like; break;
                    case 1: _state = MusicLikeStates.unlike; break;
                }
                changePicture();
            }
        });
    }

    public MusicLikeStates getState(){
        return this._state;
    }

    private void changePicture(){
        this._button.setImageResource(this._state.getPicture());
    }
}
