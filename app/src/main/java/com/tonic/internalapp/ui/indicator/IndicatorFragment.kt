package com.tonic.internalapp.ui.indicator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tonic.internalapp.MainActivity.Companion.webViewProgressBar
import com.tonic.internalapp.R
import com.tonic.internalapp.databinding.FragmentIndicatorBinding

class IndicatorFragment : Fragment() {
    private val mTag = IndicatorFragment::class.java.name
    private var webView: WebView? = null


    private lateinit var indicatorViewModel: IndicatorViewModel
    private var _binding: FragmentIndicatorBinding? = null
    var webViewProgressBar:ProgressBar? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.e(mTag, "onCreateView")


        indicatorViewModel =
                ViewModelProvider(this).get(IndicatorViewModel::class.java)

        _binding = FragmentIndicatorBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //webViewProgressBar = root.findViewById(R.id.progressBar)
        webView = root.findViewById(R.id.webView)
        val webSettings = webView!!.settings
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView!!.webChromeClient = MyWebChromClient()
        webView!!.webViewClient = WebClient()
        webView!!.loadUrl("www.google.com")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MyWebChromClient : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            /*if(newProgress<100){
                webViewProgressBar!!.visibility = View.VISIBLE
                webViewProgressBar!!.progress = newProgress
            }else{
                webViewProgressBar!!.visibility = View.GONE
            }*/
        }
    }
    class WebClient : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //webViewProgressBar!!.visibility = View.GONE
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
            //webViewProgressBar!!.visibility = View.GONE
        }
    }
}