package com.example.okno_wyszukiwanie_kat_nazwy.View;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.okno_wyszukiwanie_kat_nazwy.R;

public class YoutubeActivity extends AppCompatActivity {

    WebView mYoutubeWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);

        Intent intent = getIntent();
        String youtubeUrl = intent.getStringExtra("youtubeKey");

        mYoutubeWebView = findViewById(R.id.youtubeWebView);
        mYoutubeWebView.loadUrl(youtubeUrl);
    }
}
