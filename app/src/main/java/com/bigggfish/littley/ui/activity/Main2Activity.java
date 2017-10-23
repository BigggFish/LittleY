package com.bigggfish.littley.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bigggfish.littley.R;
import com.bigggfish.littley.util.Log;
import com.bigggfish.littley.util.ToastUtils;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mWebView = (WebView) findViewById(R.id.webview);
        initWebViewSettings();
        mWebView.loadUrl("http://api.fxll.tigercel.com/page/goods/portal?authcode=zKpAGQNmJqI19n51");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onBackPressed() {

        if(mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }

    //WebViewClient就是帮助WebView处理各种通知、请求事件的。
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 如下方案可在非微信内部WebView的H5页面中调出微信支付
            if (url.startsWith("weixin://wap/pay?")) {
                if(!isWeixinAvilible(Main2Activity.this)){
                    Toast.makeText(Main2Activity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    mWebView.goBack();
                    return true;
                }
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(Main2Activity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    mWebView.goBack();
                    return true;
                }
                return true;
            } else if (parseScheme(url)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");

                    intent.setComponent(null);
                    // intent.setSelector(null);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return false;
        }


    }

    public boolean parseScheme(String url) {

        //platformapi/startApp
        if (url.contains("platformapi/startApp")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startApp"))) {
            return true;
        } else {
            return false;
        }
    }

    private void initWebViewSettings() {
        WebSettings webSettings = mWebView.getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();

        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //webSettings.setPluginState(WebSettings.PluginState.ON);

        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setGeolocationEnabled(true);
    }

    private boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}
