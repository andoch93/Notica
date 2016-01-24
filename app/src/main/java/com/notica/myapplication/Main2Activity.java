package com.notica.myapplication;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class Main2Activity extends Activity {


    private final int duration = 2; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
//    private final double freqOfTone = 440; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];

    Handler handler = new Handler();




    int[] arr = {210,240,270,300,330,360,390,420,450,480,510};
    char[] answers = {'G','F','E','D','C','B','A','G','F','E','D'};
    double[] hertz = {783.991, 698.456, 659.255, 587.330, 523.251, 493.883, 440.000, 391.995, 349.228, 329.628, 293.665};
    char answer;
    char choice;
//    int g2 = 210;
//    int f2 = 240;
//    int e2 = 270;
//    int d2 = 300;
//    int c2 = 330;
//    int b2 = 360;
//    int a2 = 390;
//    int g = 420;
//    int f = 450;
//    int e = 480;
//    int d = 510;
    int curx = 300;
    int increasex = 100;
    int size = 11;
    Button[] b;
    ImageView im;
    ImageView[] showAnswers;
//    int[] showanswersX = {600, 700, 800};
    boolean[] showanswerscheck = {false,false,false};
    boolean[] showanswerscheck2 = {false,false,false};
    int index;
    int score = 0;
    int wrong = 0;
    int maxwrong = 5;
    Button soundbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (ImageView) findViewById(R.id.checknote);
        showAnswers = new ImageView[3];
        showAnswers[0] = (ImageView) findViewById(R.id.checkanswer1);
        showAnswers[0].setX(500);
        showAnswers[1] = (ImageView) findViewById(R.id.checkanswer2);
        showAnswers[1].setX(700);
        showAnswers[2] = (ImageView) findViewById(R.id.checkanswer3);
        showAnswers[2].setX(900);


        soundbutton = (Button) findViewById(R.id.buttonsound);
        soundbutton.setBackgroundDrawable(getResources().getDrawable(R.drawable.sound_onblack));
//        soundbutton.setButtonDrawable(getResources().getDrawable(R.drawable.sound_on));
        soundbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playSound();
            }
        });

        setnote();

        startUpButton();
    }

    public void startUpButton(){
        b = new Button[8];
        b[0] = (Button) findViewById(R.id.buttonA);
        b[1] = (Button) findViewById(R.id.buttonB);
        b[2] = (Button) findViewById(R.id.buttonC);
        b[3] = (Button) findViewById(R.id.buttonD);
        b[4] = (Button) findViewById(R.id.buttonE);
        b[5] = (Button) findViewById(R.id.buttonF);
        b[6] = (Button) findViewById(R.id.buttonG);
        b[7] = (Button) findViewById(R.id.buttonidk);

        char temp = 'A';
        for (int i = 0; i < 8; i++){
            startupbuttonhelper(b[i],temp);
            temp++;
        }



    }

    public void startupbuttonhelper(Button b, final char a) {
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent start = new Intent("android.intent.action.MAIN2");
//                startActivity(start);
                choice = a;
                checkanswer();
                if(wrong != maxwrong)
                    setnote();

            }
        });
    }

    public void setnote(){
        Random r = new Random();
        index = r.nextInt(size);

        findViewById(R.id.note).setX(findViewById(R.id.treble).getX()+curx);
//        curx+=increasex;
        findViewById(R.id.note).setY(findViewById(R.id.treble).getY() + arr[index]);

        answer = answers[index];

        genTone();
        handler.post(new Runnable() {

            public void run() {
                playSound();
            }
        });

    }

    public void checkanswer(){
        if(choice == answer){
//            im.setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotegreen));
            setanswernote(true);
            score++;
        }else{
//            im.setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotered));
            setanswernote(false);
            wrong++;
            if(wrong == maxwrong){
                startUpPopMenu();
            }
        }
    }

    public void setanswernote(boolean x){
        if(showanswerscheck[1]) {
            if (showanswerscheck2[1]) {
                showAnswers[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotegreen));
                showanswerscheck2[2] = true;
            } else {
                showAnswers[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotered));
                showanswerscheck2[2] = false;
            }
            showAnswers[2].setY(showAnswers[1].getY());
            showanswerscheck[2] = true;
        }
        if(showanswerscheck[0]) {
            if(showanswerscheck2[0]){
                showAnswers[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotegreen));
                showanswerscheck2[1] = true;
            } else {
                showAnswers[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotered));
                showanswerscheck2[1] = false;
            }
            showAnswers[1].setY(showAnswers[0].getY());
            showanswerscheck[1] = true;
        }
        if(x){
            showAnswers[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotegreen));
            showanswerscheck2[0] = true;
        }else{
            showAnswers[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.quarternotered));
            showanswerscheck2[0] = false;
        }
        showAnswers[0].setY(findViewById(R.id.treble).getY() + arr[index]);
        showanswerscheck[0] = true;
    }

    public void startUpPopMenu(){

//        textViewCountDown.setText("0.00");
        Builder dialogBox = new AlertDialog.Builder(this);
        dialogBox.setTitle("Game Over");
        dialogBox.setMessage("Correct Notes: " + (score) + "\n Do you want to retry? ");
        dialogBox.setCancelable(false);
        dialogBox.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            writeToFile();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Intent a = new Intent("android.intent.action.MAIN");
//                        startActivity(a);
                        finish();
                    }
                });
        dialogBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            //restarts game challenge when user want to continue
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                try {
//                    writeToFile();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Intent startGameChallenge = getIntent();
                startActivity(startGameChallenge);
                finish();
            }
        });
        dialogBox.show();
    }






    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/hertz[index]));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

}
