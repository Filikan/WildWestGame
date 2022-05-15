package com.example.wildwest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btnShoot1,btnShoot2,btnStart;
    TextView tvTime1,tvTime2,tvScore1,tvScore2;
    SeekBar sb1,sb2;
    Handler handler;

    class myRunnable implements Runnable{

        @Override
        public void run() {
            Log.d("TIMER","Wow");
            handler.postDelayed(this,1000);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShoot1 = (Button) findViewById(R.id.btnShoot1);
        btnShoot2 = (Button) findViewById(R.id.btnShoot2);
        btnStart = (Button) findViewById(R.id.btnStart);

        tvScore1 = (TextView) findViewById(R.id.tvScore1);
        tvScore2 = (TextView) findViewById(R.id.tvScore2);

        tvTime1 = (TextView) findViewById(R.id.tvTime);
        tvTime2 = (TextView) findViewById(R.id.tvTime2);



        sb1 = (SeekBar) findViewById(R.id.sbTime1);

        sb1.setMax(30);
        sb1.setProgress(1);

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                String second;
                int minute;

                second = ""+(i%60);
                minute = i/60;

                if (i%60<10)
                {
                    second = "0"+second;
                }

                String timestr = ""+minute+":"+second;
                tvTime1.setText(timestr);
                tvTime2.setText(timestr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }

    CountDownTimer cdt;

    private int milliseconds = 0;
    private int seconds = 0;

    private boolean running1;
    private boolean running2;
    private boolean wasRunning;

    boolean isOneWin = false;
    boolean isTwoWin = false;

    public void Start(View v)
    {
        btnStart.setEnabled(false);
        cdt = new CountDownTimer(sb1.getProgress()*1000,1000) {
            @Override
            public void onTick(long l) {

                sb1.setProgress(sb1.getProgress()-1);

                //tvTime1.setText(cdt.toString());
                btnStart.setEnabled(false);
                btnShoot1.setEnabled(false);
                btnShoot2.setEnabled(false);
                sb1.setEnabled(false);
                //sb2.setEnabled(false);


                MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.ready);
                mp.start();
                mp.start();

            }



            @Override
            public void onFinish() {
                MediaPlayer mpf = MediaPlayer.create(MainActivity.this,R.raw.bang);
                mpf.start();

                //Toast.makeText(MainActivity.this,"Shoot!",Toast.LENGTH_SHORT).show();
                sb1.setEnabled(true);
                //sb2.setEnabled(true);

                btnShoot1.setEnabled(true);
                btnShoot2.setEnabled(true);

                btnStart.setEnabled(true);

                //tvScore1.setEnabled(true);

                int resID = getResources().getIdentifier("ready.mp3","raw",getPackageName());

                //MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.ready);
                //mp.start();

                running1 = true;
                running2 = true;
                runTimer();

                //Check();
            }
        };
        cdt.start();
    }



    private void runTimer() {
        final TextView tvTimeShot1 = (TextView)findViewById(R.id.tvTimeShot1);
        final TextView tvTimeShot2 = (TextView)findViewById(R.id.tvTimeShot2);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;


                // Format the seconds into hours, minutes,
                // and seconds.
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);

                // Set the text view text.
                tvTimeShot1.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running1) {
                    seconds++;
                    //Check();
                }

                /*else if (running2) {
                    seconds++;
                    Check();
                }*/

                // Post the code again
                // with a delay of 1 second.
                //handler.postDelayed(this, 1000);
                handler.postDelayed(this, 1);
            }
        });

    }

    int score1=0,score2=0;

    public void Shoot1(View v)
    {
        MediaPlayer mpsh = MediaPlayer.create(MainActivity.this,R.raw.shotgun);
        mpsh.start();
        //handler = new Handler();
        //handler.postDelayed(new myRunnable());
        running1 = false; //running1 üstteki sayaç.
        isOneWin = true;
        score1 = score1+1;
        tvScore1.setText(""+score1);

        //tvScore1.setEnabled(false);
        Check();

    }

    public void Shoot2(View v)
    {
        MediaPlayer mpsh = MediaPlayer.create(MainActivity.this,R.raw.shotgun);
        mpsh.start();
        //running1 = false;
        running2 = false;
        score2 = score2+1;
        tvScore2.setText(""+score2);
        isTwoWin = true;
        Check();
    }

    private void Check()
    {
        if (isOneWin)
        {
            //score1 = score1+1;
            MediaPlayer mpone = MediaPlayer.create(MainActivity.this,R.raw.playeronewins);
            mpone.start();
            Toast.makeText(MainActivity.this,"Player 1 Wins!",Toast.LENGTH_LONG).show();
        }
        else if (isTwoWin)
        {
            //score2 = score2+1;
            MediaPlayer mptwo = MediaPlayer.create(MainActivity.this,R.raw.playertwowins);
            mptwo.start();
            Toast.makeText(MainActivity.this,"Player 2 Wins!",Toast.LENGTH_LONG).show();
        }
    }

    //    boolean isMusicOff = false;
//    // Tek bir Music On/Off butonu sayesinde müzik açma kapama sorunu çözülebilir.
//    public void MusicOn(View v)
//    {
//       MediaPlayer mps = MediaPlayer.create(MainActivity.this,R.raw.westworld);
//       mps.start();
//       if(isMusicOff == true){
//           Log.d("test","" + isMusicOff);
//           mps.stop();
//       }else
//           isMusicOff = false;
//    }
//    public void MusicOff(View v)
//    {
//        isMusicOff = true;
//    }
    // Test : Music on/off class
    /**/
    boolean isMusicOn = true;
    private MediaPlayer mps;
    public void MusicOn(View v){
        if (isMusicOn == true){
            // stopPlaying();
            mps = MediaPlayer.create(MainActivity.this,R.raw.westworld);
            mps.start();
            Log.d("test","" + isMusicOn);
            isMusicOn = false;
        }else{
            Log.d("test","" + isMusicOn);
            stopPlaying();
        }
    }
    private void stopPlaying() {
        if (mps != null) {
            mps.stop();
            mps.release();
            mps = null;
            isMusicOn = true;
        }
    }
}