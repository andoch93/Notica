package com.notica.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    private static MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "ChopinScript.otf");
        TextView myTextview = (TextView)findViewById(R.id.textview1);
        myTextview.setTypeface(myTypeface);

        startUpButton();

        music = MediaPlayer.create(this, R.raw.bachsong);
        music.setVolume(100, 100);
        music.start();
        music.setLooping(true);
    }

    public void startUpButton(){
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                music.pause();
                Intent start = new Intent("android.intent.action.MAIN2");
                startActivity(start);
            }
        });

    }

}
