package com.example.baads;
//import static com.example.baads.R.id.wikiV;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;



public class WikiTips extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_tips);
        WebView newWebView = (WebView) findViewById(R.id.wikiV);
        newWebView.setWebViewClient(new WebViewClient());
        newWebView.loadUrl("https://www.wikihow.com/Deal-With-Stress");
    }
}