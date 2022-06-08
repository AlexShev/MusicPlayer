package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.musicplayer.Activities.authorization.Authorizer;
import com.example.musicplayer.R;

public class SplashActivity extends AppCompatActivity {

    private Handler _handler;
    private MutableLiveData<Boolean> isOK = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        _handler = new Handler();

        isOK.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }

                finish();
            }
        });

        _handler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                String login = preferences.getString("login", "");

                if (!login.isEmpty())
                {
                    String password = preferences.getString("password", "");

                    Authorizer.autorotate(login, password, isOK, SplashActivity.this);
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }

            }
        });
    }
}