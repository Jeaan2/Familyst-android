package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.tcc.ufpr.familyst.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();

                //TODO se usuario logado, vai direto pra MainActivity, se não, vai para login
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 600);
    }
}
