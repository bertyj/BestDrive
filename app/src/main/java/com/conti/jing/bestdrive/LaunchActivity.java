package com.conti.jing.bestdrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.conti.jing.bestdrive.R.layout.layout_launch_activity);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
    }
}
