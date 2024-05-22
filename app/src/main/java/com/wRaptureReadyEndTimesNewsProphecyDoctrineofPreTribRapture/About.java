package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ShapeableImageView imageView = findViewById(R.id.about_close);
        TextView textView = findViewById(R.id.tv2);

        textView.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
        imageView.setOnClickListener(v -> finish());

        TextView link = findViewById(R.id.about_web_link);
        link.setOnClickListener( v -> startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://eternityready.com")) ) );
    }
}