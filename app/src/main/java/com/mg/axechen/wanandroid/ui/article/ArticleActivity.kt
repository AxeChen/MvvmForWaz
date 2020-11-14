package com.mg.axechen.wanandroid.ui.article

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.text.Html
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.load.LoadingCallback
import com.mg.axechen.wanandroid.base.load.NoDataCallBack
import com.mg.axechen.wanandroid.base.webview.WebViewActivity
import com.mg.axechen.wanandroid.ui.dialog.BottomFollowDialog
import com.smallbuer.jsbridge.core.BridgeWebView
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ArticleActivity : WebViewActivity() {

    override fun setLayoutId(): Int = R.layout.activity_article

    private var mWebView: BridgeWebView? = null
    private var webSettings: WebSettings? = null

    private var loadService: LoadService<FrameLayout>? = null

    companion object {
        const val TITLE = "title"
        const val URL = "url"
    }

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.white)
            .statusBarDarkFont(true, 0.2f)
            .init()
        mWebView = BridgeWebView(this)
        mWebView?.run {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            webView.addView(this)
        }
        initLoadStatus()
        toolbar?.run {
            setBackgroundResource(R.color.white)
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener { finish() }
            toolbarTitle.setTextColor(
                ContextCompat.getColor(
                    this@ArticleActivity,
                    R.color.text_title
                )
            )
        }
        initWebSettings()
    }

    private fun initLoadStatus() {
        val loadSir = LoadSir.Builder().addCallback(LoadingCallback())
            .addCallback(NoDataCallBack()).build()
        loadService = loadSir.register(webView) as LoadService<FrameLayout>?
    }


    @SuppressLint("AddJavascriptInterface", "SetJavaScriptEnabled")
    private fun initWebSettings() {
        webSettings = mWebView?.settings
        webSettings?.run {
            allowFileAccess = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            setSupportZoom(true)
            builtInZoomControls = true
            useWideViewPort = true
            setSupportMultipleWindows(false)
            loadWithOverviewMode = true
            setAppCacheEnabled(true)
            databaseEnabled = true
            domStorageEnabled = true
            javaScriptEnabled = true
            setGeolocationEnabled(true)
            allowContentAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            allowUniversalAccessFromFileURLs = true
            allowFileAccessFromFileURLs = true
            setAppCachePath(this@ArticleActivity.getDir("appcache", 0).path)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //两者都可以
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW//设置安全的来源
            }
            cacheMode = WebSettings.LOAD_NO_CACHE//重写使用缓存的方式
        }
        mWebView?.run {
            overScrollMode = View.OVER_SCROLL_ALWAYS
            isVerticalScrollBarEnabled = false
            webChromeClient = mWebChromeClient
            webViewClient = mWebViewClient
            intent?.run {
                loadService?.showCallback(LoadingCallback::class.java)
                val url = getStringExtra(URL) ?: ""
                loadUrl(url)
                val title = getStringExtra(TITLE) ?: ""
                toolbarTitle.text = Html.fromHtml(title).toString()
            }
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            view?.run {
                title?.let {
                }
            }
        }

        override fun onProgressChanged(webView: WebView?, newProgress: Int) {
            super.onProgressChanged(webView, newProgress)
            if (newProgress > 70) {
                loadService?.showSuccess()
                // 判断是不是已经关注了这个类型，假如没有则关注这个类型
            }
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return super.onJsAlert(view, url, message, result)
        }

        override fun onJsConfirm(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return super.onJsConfirm(view, url, message, result)
        }

        override fun onJsPrompt(
            view: WebView?,
            url: String?,
            message: String?,
            defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }

    }

    private val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            var dialog = BottomFollowDialog()
            dialog.show(supportFragmentManager, "follow")
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            //忽略SSL证书错误，继续加载页面           
            handler?.proceed()
        }

    }

    override fun initData() {
        super.initData()
    }

    override fun onDestroy() {
        mWebView?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView?.removeView(this)
                removeAllViews()
                destroy()
            } else {
                removeAllViews()
                destroy()
                webView?.removeView(this)
            }
            mWebView = null
        }
        super.onDestroy()
    }

}