package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wiacek.martyna.esnpwapp.R;


public class PWWebsiteFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public WebView myWebView;
    final static String PWWebsiteAddress = "http://www.pw.edu.pl/engpw";
    String myUrl;


    public PWWebsiteFragment() {
    }

    public WebView getMyWebView() {
        return myWebView;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pwwebsite, container, false);
        myWebView = (WebView)view.findViewById(R.id.mywebview);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());

        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setDisplayZoomControls(true);
        myWebView.getSettings().setBuiltInZoomControls(true);

        if(myUrl == null){
            myUrl = PWWebsiteAddress;
        }
        myWebView.loadUrl(myUrl);

        myWebView.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;}}
                return false;
            }
        });

        return view;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


}
