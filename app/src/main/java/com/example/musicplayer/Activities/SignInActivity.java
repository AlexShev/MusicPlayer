package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicplayer.authorization.Authorizer;
import com.example.musicplayer.R;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private MutableLiveData<Boolean> isOK = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        com.google.android.material.textfield.TextInputLayout login = findViewById(R.id.textField);

        login.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                {
                    login.setError(null);
                }
                else
                {
                    Editable text = login.getEditText().getText();

                    if (text.toString().length() == 0)
                    {
                        login.setError("Введите почту");
                    }
                    else if(!isValidEmailAddress(Objects.requireNonNull(login.getEditText()).getText().toString()))
                    {
                        login.setError("Это не почта");
                    }
                }
            }
        });

        com.google.android.material.textfield.TextInputLayout password = findViewById(R.id.editTextTextPassword);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.clearFocus();
                password.clearFocus();

                if (login.getEditText().getText().toString().length() == 0 ||
                    password.getEditText().getText().toString().length() == 0)
                {
                    Toast.makeText(SignInActivity.this, "Введите недостоющие поля", Toast.LENGTH_SHORT).show();
                }
                else if(login.getError() != null || password.getError() != null)
                {
                    Toast.makeText(SignInActivity.this, "Исправте ошибки в полях", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Authorizer.autorotate(login.getEditText().getText().toString(),
                            password.getEditText().getText().toString(), isOK, SignInActivity.this);
                }
            }
        });

        isOK.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}