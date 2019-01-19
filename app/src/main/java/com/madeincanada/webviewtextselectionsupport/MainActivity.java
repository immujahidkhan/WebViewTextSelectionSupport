package com.madeincanada.webviewtextselectionsupport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.web);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(), "Android");
        myWebView.loadData("Logcat is a tool that dumps a log of system messages. The messages include a stack trace when the device throws an error," +
                " as well as log messages written " +
                "from your application and those written using JavaScript console APIs" +
                "<script>" +
                "var text='';setInterval(function(){ if(window.getSelection().toString() && text!==window.getSelection().toString()){text=window.getSelection().toString();" +
                "console.log(text);Android.showToast(text); }}, 1000);" +
                "</script>", "text/html; charset=UTF-8", null);
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("MyApplication", message);
            }
        });

    }

    public class WebAppInterface {
        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
