package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;

import com.suddenh4x.ratingdialog.AppRating;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(500);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, WebViewFragment.newInstance(), "FRAGMENT").commitNow();
        }

        AppRating.Builder builder = new AppRating.Builder(this).setMinimumDays(3).setMinimumDaysToShowAgain(3).setMinimumLaunchTimes(6);
        builder.showIfMeetsConditions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?").setTitle("Exit")
                    .setPositiveButton("yes", (dialog, which) -> finish())
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss()).setCancelable(false);
            builder.create().show();
            return true;
        }
        return false;
    }
}