package com.example.baads;

//import static com.example.baads.R.id.wikiV;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class StressManagement2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_management2);

       // newButton = (Button) findViewById(R.id.newButton);

       WebView newWebView = (WebView) findViewById(R.id.wikiV);
       newWebView.setWebViewClient(new WebViewClient());
        //setContentView(newWebView);
        newWebView.loadUrl("https://www.wikihow.com/Deal-With-Stress");

    }
}