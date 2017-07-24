package com.atguigu.h5demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private WebView wv;
    private WebViewClient webClient = new WebViewClient(){

        //页面开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Log.i("aaaaa", "onPageStarted: "+url);

            if (url.contains("ddd") || url.contains("fff")){
                view.stopLoading();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
                view.stopLoading();
            }

        }

        //页面加载结束
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //Log.i(TAG, "onPageFinished: "+url);
            //getCookie(url);
        }

       /* //加载网页失败的时候回调的方法
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            view.stopLoading();//停止加载页面
            //if (errorCode == ERROR_UNSUPPORTED_SCHEME)
            view.loadUrl("file:///android_asset/error.html");
        }*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    public void getCookie(String url){
        //cookie管理者
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        tv.setText(cookie);
    }



    private void initView() {

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        wv = (WebView) findViewById(R.id.wv);

    }

    private void initData() {

        //设置cookie
        //setCookie();
        WebSettings settings = wv.getSettings();
        //设置userAgent
        settings.setUserAgentString("androidddddddddddddddddddddddd");
        //获取userAgnetString
        String userAgentString = settings.getUserAgentString();
        tv.setText(userAgentString);



        settings.setJavaScriptEnabled(true); //允许加载js
        //给H5一个当前本身的对象 第一个参数是对像本身 第二个参数是对象的一个名称
        wv.addJavascriptInterface(this,"android");
        wv.setWebViewClient(webClient);
        wv.loadUrl("file:///android_asset/123.html");
        //wv.loadUrl("http://www.baidu.com");

    }

    private void setCookie() {
        //创建CookieSyncManager 参数是上下文
        CookieSyncManager.createInstance(this);
        //得到CookieManager
        CookieManager cookieManager = CookieManager.getInstance();
        //得到向URL中添加的Cookie的值
        String cookieString;//获取方法不再详述，以项目要求而定
        //使用cookieManager..setCookie()向URL中添加Cookie
        cookieManager.setCookie("http://www.baidu.com", "ccccccccccc");
        CookieSyncManager.getInstance().sync();
    }

    private boolean isCookie = false;
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    //参数javascript+js函数名称
                    wv.loadUrl("javascript:message()");
                    break;
                case R.id.btn2:
//                在android调用js有参的函数的时候参数要加单引号
                    String name = "小龙哥帅呆了";
                    wv.loadUrl("javascript:message2('" + name + "')");
                    break;
                case R.id.btn3:
                    isCookie = true;
                    //setCookie("http://www.baidu.com");
                    wv.loadUrl("http://www.baidu.com");
                    break;
            }
    }

    /*
    * 让H5页面调用的
    *
    * */
    //这个注解必须加 因为 兼容问题
    @JavascriptInterface
    public void setMessage() {
        Toast.makeText(this, "我弹", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void setMessage(String name) {
        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
    }
}
