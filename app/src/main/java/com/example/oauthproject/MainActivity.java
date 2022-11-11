package com.example.oauthproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.authorization);
        webView.loadUrl("https://trello.com/1/authorize?expiration=never&name=TrelloPowerUp&" +
                "scope=read&key=4e9a82e4c4c9ee1debd9dbda562eb260&callback_method=fragment" +
                "&return_url=https://tasya.com/pog.png");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("ACCESSING_URL", url);
                if (url.contains("#token")){
                    String[] raw = url.split("#");
                    String ready = raw[1].replace("token=", "");
                    loadResult(ready);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void loadResult(String token) {
        Intent myIntent = new Intent(MainActivity.this, ResultActivity.class);
        myIntent.putExtra("token", token);
        Log.d("meow", token);
        MainActivity.this.startActivity(myIntent);
    }
}