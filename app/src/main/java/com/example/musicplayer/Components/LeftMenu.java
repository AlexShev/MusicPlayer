package com.example.musicplayer.Components;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LeftMenu {

    public interface IOnClickListener {
        void onClick();
    }

    private Button outButton;
    private TextView settingText;
    private TextView faqText;

    public LeftMenu(Button out, TextView settingText, TextView faqText){
        this.faqText = faqText;
        this.outButton = out;
        this.settingText = settingText;
    }

    public void setOnClickListenerForOutButton(IOnClickListener onClickListener){
        this.outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick();
            }
        });
    }

    public void setOnClickListenerForSettingText(IOnClickListener onClickListener){
        this.settingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick();
            }
        });
    }

    public void setOnClickListenerForFaqText(IOnClickListener onClickListener){
        this.faqText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick();
            }
        });
    }

}
