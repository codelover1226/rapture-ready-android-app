package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Integer colorPosition = preferences.getInt(ThemeModel.COLOR_KEY, 0);
        ThemeModel themeModel = ThemeModel.get(colorPosition);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(themeModel.STATUS_BAR_COLOR);

        listener = (sharedPreferences, key) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (key.equals(ThemeModel.COLOR_KEY)) ){
                Integer position = sharedPreferences.getInt(ThemeModel.COLOR_KEY, 0);
                ThemeModel themeModel1 = ThemeModel.get(position);

                getWindow().setStatusBarColor(themeModel1.STATUS_BAR_COLOR);
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }
}
