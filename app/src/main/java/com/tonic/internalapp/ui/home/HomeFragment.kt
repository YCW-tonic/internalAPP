package com.tonic.internalapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tonic.internalapp.R
import com.tonic.internalapp.databinding.FragmentHomeBinding
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        /*val webView = view.findViewById<WebView>(R.id.webviewPhone)
        val webSettings = webView!!.settings
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView!!.webChromeClient = MyWebChromeClient()
        webView!!.webViewClient = WebClient()
        webView.loadUrl("https://www.google.com")*/
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.shohei_ohtani_1))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_2))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_3))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_4))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_5))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_6))
        imageList.add(SlideModel(R.drawable.shohei_ohtani_7))

        val imageSlider = root.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        imageSlider.startSliding(3000) // with new period
        imageSlider.startSliding()
        imageSlider.stopSliding()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            Log.i("===>$newProgress", "onProgressChanged")
            if (newProgress < 100) {
               // webviewProgressBar!!.visibility = View.VISIBLE
                //webviewProgressBar!!.progress = newProgress
            } else {
                //webviewProgressBar!!.visibility = View.GONE
            }
        }

    }

    class WebClient : WebViewClient() {
        /*override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }*/

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            Log.e("===>", "onPageFinished")

            //webviewProgressBar!!.visibility = View.GONE
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)

            Log.e("===>", "onPageCommitVisible")

            //webviewProgressBar!!.visibility = View.GONE
        }

    }
}