package com.tonic.internalapp.ui.announcement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tonic.internalapp.MainActivity
import com.tonic.internalapp.MainActivity.Companion.announcementItemList
import com.tonic.internalapp.R
import com.tonic.internalapp.data.AnnouncementItem
import com.tonic.internalapp.data.AnnouncementItemAdapter
import com.tonic.internalapp.data.Constants
import com.tonic.internalapp.databinding.FragmentAnnouncementBinding
import java.util.ArrayList

class AnnouncementFragment : Fragment() {
    private val mTAG = AnnouncementFragment::class.java.name
    private var announcementContext: Context? = null

    private var progressBar: ProgressBar? = null
    private var relativeLayout: RelativeLayout? = null

    private lateinit var announcementViewModel: AnnouncementViewModel
    private var _binding: FragmentAnnouncementBinding? = null

    private var listViewAnnouncement: ListView ?= null
    private var announcementItemAdapter: AnnouncementItemAdapter ?= null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //var announcementItemList = ArrayList<AnnouncementItem>()
    private var mReceiver: BroadcastReceiver? = null
    private var isRegister = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(mTAG, "onCreate")

        announcementContext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        announcementViewModel =
            ViewModelProvider(this).get(AnnouncementViewModel::class.java)

        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listViewAnnouncement = root.findViewById(R.id.listViewAnnouncement)

        relativeLayout = root.findViewById(R.id.announcement_container)
        progressBar = ProgressBar(announcementContext, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(MainActivity.screenHeight / 4, MainActivity.screenWidth / 4)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)

        val localRelativeLayout: RelativeLayout? = relativeLayout
        if (localRelativeLayout != null) {
            localRelativeLayout.addView(progressBar, params)
        } else {
            Log.e(mTAG, "localRelativeLayout = null")
        }
        progressBar!!.visibility = View.GONE

        if (announcementContext != null) {

            announcementItemAdapter = AnnouncementItemAdapter(announcementContext, R.layout.fragment_announcement_listview_item, announcementItemList)
            listViewAnnouncement!!.adapter = announcementItemAdapter

            //returnOfGoodsDetailItemAdapter = ReturnOfGoodsDetailItemAdapter(returnOfGoodsContext, R.layout.fragment_return_of_goods_detail_item, returnOfGoodsDetailShowList)
            //listViewReturnOfGoodsDetail!!.adapter = returnOfGoodsDetailItemAdapter
        }


        val filter: IntentFilter

        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action != null) {
                    if (intent.action!!.equals(Constants.ACTION.ACTION_ANNOUNCEMENT_UPDATE_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_ANNOUNCEMENT_UPDATE_ACTION")

                        if (announcementItemAdapter != null) {
                            announcementItemAdapter?.notifyDataSetChanged()
                        }
                    }

                }
            }
        }

        if (!isRegister) {
            filter = IntentFilter()
            filter.addAction(Constants.ACTION.ACTION_ANNOUNCEMENT_UPDATE_ACTION)
            announcementContext?.registerReceiver(mReceiver, filter)
            isRegister = true
            Log.d(mTAG, "registerReceiver mReceiver")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (isRegister && mReceiver != null) {
            try {
                announcementContext!!.unregisterReceiver(mReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

            isRegister = false
            mReceiver = null
            Log.d(mTAG, "unregisterReceiver mReceiver")
        }
    }
}