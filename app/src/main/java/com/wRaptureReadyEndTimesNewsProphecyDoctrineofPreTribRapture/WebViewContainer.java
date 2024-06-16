package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * This class inherits from fragment, it's the class that keeps the web view and operate on it
 */
public class WebViewContainer extends Fragment {

    private static final String URL_KEY = "URL_KEY";
    private String url;

    private ExtendedWebView webView;

    private boolean isLoaded;

    //default constructor
    public WebViewContainer(){}

    //creates an instance of the fragment and passes an argument to the fragment
     public static WebViewContainer newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        WebViewContainer fragment = new WebViewContainer();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;

        url = args.getString(URL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.adapter_layout, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webview);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        //webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        //webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setSaveEnabled(true);

        disableCache(webView);

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setFocusableInTouchMode(true);
        //webView.clearCache(true);

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WebViewContainer.this.url = url;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    /*
    overrides the onResume method, and calls the web view onResume to stop every html5, javascript processing
     */
    @Override
    public void onResume() {
        super.onResume();
//        if (isLoaded)
        webView.onResume();
        disableCache(webView);
//        if (!isLoaded) {
        webView.loadUrl(url);
        isLoaded = true;
//        }
    }

    //opposite of onResume
    @Override
    public void onPause() {
        super.onPause();
        if (isLoaded)
            webView.onPause();
        disableCache(webView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
        outState.putBoolean(URL_KEY, isLoaded);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
            isLoaded = savedInstanceState.getBoolean(URL_KEY, false);
        }
    }

    private void disableCache(WebView wv){
        // Clear cache
        wv.clearCache(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public void tryReload(){
        webView.reload();
    }
}
