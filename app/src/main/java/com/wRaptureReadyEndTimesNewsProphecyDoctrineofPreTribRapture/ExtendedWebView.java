package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;

public class ExtendedWebView extends WebView {
    public ExtendedWebView(Context context) {
        this(context, null);
    }

    public ExtendedWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtendedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (canGoBack()) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        loadUrl("javascript:document.location=document.location");//a stackoverflow recommended solution that works like charm
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFocus();
    }
}
