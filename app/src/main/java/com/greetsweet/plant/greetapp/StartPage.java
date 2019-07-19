package com.greetsweet.plant.greetapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Login.LoginActivity;


public class StartPage extends AppCompatActivity {
Button startlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken").equalsIgnoreCase("NA")))
        {
            Intent i=new Intent(StartPage.this,HomeActivity.class) ;
            startActivity(i);
            finish();
        }
        startlog=(Button)findViewById(R.id.button);
        startlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent=new Intent(StartPage.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();

            }
        });

/*
        int splash_Time_Out = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken").equalsIgnoreCase("NA")))
                {
                    Intent i=new Intent(StartPage.this,HomeActivity.class) ;
                    startActivity(i);
                    finish();
                }


            }
        }, splash_Time_Out);*/

    }
}
