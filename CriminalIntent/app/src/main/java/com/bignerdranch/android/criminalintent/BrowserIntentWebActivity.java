package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class BrowserIntentWebActivity extends AppCompatActivity {

    private EditText mUrlEditText;
    private Button mBrowserIntentWebButton;
    private WebView mWebView;
    private Button mBrowserWebViewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_intent_web);
        mWebView=(WebView) findViewById(R.id.WebViewBrowser);
        mBrowserWebViewButton=(Button)findViewById(R.id.button_WebView);
        mUrlEditText=(EditText)findViewById(R.id.editText_url);
        mBrowserIntentWebButton=(Button)findViewById(R.id.button_browser_intentWeb);
        mBrowserWebViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl(mUrlEditText.getText().toString());
            }
        });
    }


    public void onClickBrowserIntentWeb(View view){
        Uri uriBrowser=Uri.parse(mUrlEditText.getText().toString());
        BrowserIntentWebActivity.this.startActivity(new Intent(Intent.ACTION_VIEW,uriBrowser));
    }
}
