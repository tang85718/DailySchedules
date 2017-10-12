package co.stringstech.notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MediaPlayerActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ButterKnife.bind(this);

        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setJavaScriptEnabled(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);

        String url = "https://music.baidu.com";
        webView.setFocusable(true);
        webView.setLongClickable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Timber.d("url:%s", url);
                return true;
            }
        });

        webView.loadUrl(url);
    }

}
