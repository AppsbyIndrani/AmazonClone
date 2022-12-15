package com.example.amazonclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mauth=FirebaseAuth.getInstance();

        askForFullScreen();
        runNextScreen();


    }

    private void askForFullScreen()
    {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

    private void runNextScreen()
    {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                if (mauth.getCurrentUser() == null)
                {
                    startActivity(new Intent(SplashScreen.this,IntroActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                    finish();

                }


            }
        },2500);

    }
}