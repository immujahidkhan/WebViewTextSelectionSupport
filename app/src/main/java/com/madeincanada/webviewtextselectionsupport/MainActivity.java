package com.madeincanada.webviewtextselectionsupport;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;
    FloatingActionButton highlighter;
    String data = "Logcat is a tool that dumps a log of system messages. The messages include a stack trace when the device throws an error," +
            " as well as log messages written " +
            "from your application and those written using JavaScript console APIs";
    String myHighLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.web);
        highlighter = findViewById(R.id.highlighter);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(), "Android");
        myWebView.loadData(data.replace("written", "<mark>written</mark>") +
                "<script>" +
                "var text='';setInterval(function(){ if(window.getSelection().toString() && text!==window.getSelection().toString()){text=window.getSelection().toString();" +
                "console.log(text);Android.showToast(text); }}, 1000);" +
                "</script>", "text/html; charset=UTF-8", null);
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("MyApplication", message);
            }
        });
        highlighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadData(data.replace(myHighLight, "<mark>" + myHighLight + "</mark>") +
                        "<script>" +
                        "var text='';setInterval(function(){ if(window.getSelection().toString() && text!==window.getSelection().toString()){text=window.getSelection().toString();" +
                        "console.log(text);Android.showToast(text); }}, 1000);" +
                        "</script>", "text/html; charset=UTF-8", null);
                Toast.makeText(MainActivity.this, myHighLight, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class WebAppInterface {
        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            myHighLight = toast;
            //Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
        }
    }
}