package com.example.amazonclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    TextView introsignin,introsignup,loginusingotptext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    introsignin=findViewById(R.id.txtSignin);
    introsignup=findViewById(R.id.txtSignup);
    loginusingotptext=findViewById(R.id.OtpSignUPText);

    introsignin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity(new Intent(IntroActivity.this,LoginActivity.class));

        }
    });

    introsignup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {

            startActivity(new Intent(IntroActivity.this,RegisterActivity.class));

        }
    });


    loginusingotptext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(IntroActivity.this,OtpLoginActivity.class));
        }
    });


    }
}