package com.android.avempace.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.avempace.R;
import com.android.avempace.utilities.SharedPreferenceHelper;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceHelper = new SharedPreferenceHelper();
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    Intent intent;
                    // if token is empty = user not logged in
                    if(sharedPreferenceHelper.getToken().isEmpty())
                        intent  = new Intent(getApplicationContext(), LoginActivity.class);
                    else
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}