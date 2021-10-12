package com.tonic.internalapp.ui.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import android.widget.AdapterView
import android.widget.GridView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.tonic.internalapp.data.Constants
import com.tonic.internalapp.data.HomeGridItem
import com.tonic.internalapp.data.HomeGridItemAdapter

class HomeFragment : Fragment() {
    private val mTAG = HomeFragment::class.java.name

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var appList = ArrayList<HomeGridItem>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var homeGridContext: Context? = null
    private var homeGridItemAdapter: HomeGridItemAdapter? = null

    private var mReceiver: BroadcastReceiver? = null
    private var isRegister = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(mTAG, "onCreate")

        homeGridContext = context
    }

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

        /*\val webView = view.findViewById<WebView>(R.id.webviewPhone)
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
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_1))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_2))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_3))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_4))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_5))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_6))
        //imageList.add(SlideModel(R.drawable.shohei_ohtani_7))
        imageList.add(SlideModel(R.drawable.tonic1))
        imageList.add(SlideModel(R.drawable.tonic2))
        //imageList.add(SlideModel(R.drawable.tonic3))
        //imageList.add(SlideModel(R.drawable.tonic4))
        //imageList.add(SlideModel(R.drawable.tonic5))

        val imageSlider = root.findViewById<ImageSlider>(R.id.image_slider_home)
        imageSlider.setImageList(imageList)

        imageSlider.startSliding(3000) // with new period

        val item0 = HomeGridItem("Announcement", R.drawable.baseline_assignment_black_48, R.string.nav_announcement)
        appList.add(item0)

        val item1 = HomeGridItem("Edit", R.drawable.baseline_edit_black_48, R.string.nav_edit)
        appList.add(item1)

        val item2 = HomeGridItem("Balance", R.drawable.baseline_monetization_on_black_48, R.string.nav_balance)
        appList.add(item2)

        val gridView = root.findViewById<GridView>(R.id.gridViewHome)

        homeGridItemAdapter = HomeGridItemAdapter(homeGridContext, R.layout.fragment_home_grid_item, appList)
        //listView.setAdapter(receiptDetailItemAdapter)
        gridView!!.adapter = homeGridItemAdapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Log.d(mTAG, "click $position")

            when(appList[position].getAppId()) {
                "Announcement" -> {
                    val showIntent = Intent()
                    showIntent.action = Constants.ACTION.ACTION_HOME_GO_TO_ANNOUNCEMENT_ACTION
                    homeGridContext!!.sendBroadcast(showIntent)
                }
                "Edit" -> {
                    val showIntent = Intent()
                    showIntent.action = Constants.ACTION.ACTION_HOME_GO_TO_EDIT_ACTION
                    homeGridContext!!.sendBroadcast(showIntent)
                }
                "Balance" -> {
                    val showIntent = Intent()
                    showIntent.action = Constants.ACTION.ACTION_HOME_GO_TO_BALANCE_ACTION
                    homeGridContext!!.sendBroadcast(showIntent)
                }
            }
        }

        val filter: IntentFilter

        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action != null) {
                    if (intent.action!!.equals(Constants.ACTION.ACTION_ANNOUNCEMENT_UPDATE_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_ANNOUNCEMENT_UPDATE_ACTION")

                        var badge = item0.getBadge()
                        badge += 1
                        item0.setBadge(badge)
                        homeGridItemAdapter?.notifyDataSetChanged()
                    }

                }
            }
        }

        if (!isRegister) {
            filter = IntentFilter()
            filter.addAction(Constants.ACTION.ACTION_ANNOUNCEMENT_UPDATE_ACTION)
            homeGridContext?.registerReceiver(mReceiver, filter)
            isRegister = true
            Log.d(mTAG, "registerReceiver mReceiver")
        }

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