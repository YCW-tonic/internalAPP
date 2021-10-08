package com.tonic.internalapp.data

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.tonic.internalapp.R
import org.w3c.dom.Text
import ru.nikartm.support.BadgePosition
import ru.nikartm.support.ImageBadgeView
import ru.nikartm.support.model.Badge
import java.time.format.TextStyle

class HomeGridItemAdapter(context: Context?, resource: Int, objects: ArrayList<HomeGridItem>) :
    ArrayAdapter<HomeGridItem>(context as Context, resource, objects) {

    //private val mTAG = ReceiptDetailItemAdapter::class.java.name
    private val layoutResourceId: Int = resource

    private var inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val items: ArrayList<HomeGridItem> = objects
    //private val mContext = context

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): HomeGridItem? {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //Log.e(mTAG, "getView = "+ position);
        val view: View
        val holder: ViewHolder
        if (convertView == null || convertView.tag == null) {
            //Log.e(mTAG, "convertView = null");
            /*view = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder(view);
            view.setTag(holder);*/

            //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResourceId, null)
            holder = ViewHolder(view)
            //holder.checkbox.setVisibility(View.INVISIBLE);
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        //holder.fileicon = (ImageView) view.findViewById(R.id.fd_Icon1);
        //holder.filename = (TextView) view.findViewById(R.id.fileChooseFileName);
        //holder.checkbox = (CheckBox) view.findViewById(R.id.checkBoxInRow);


        val homeGridItem = items[position]
        //if (receiptDetailItem != null) {
        holder.icon.setImageResource(homeGridItem.getImgId())
        if (position == 0) {
            holder.icon.badgeValue = 27
            holder.icon.badgePosition = BadgePosition.BOTTOM_RIGHT
            holder.icon.badgeTextSize = 24f
            holder.icon.setBadgePadding(10)
        }
        //holder.icon.badgeTextSize = 12F
        //app:ibv_badgeTextSize="12sp"
        //holder.icon.setFixedBadgeRadius(15f)
        //app:ibv_fixedBadgeRadius="15dp"
        //holder.icon.badgeTextStyle = BOLD
        //app:ibv_badgeTextStyle="bold"
        //holder.icon.badgeTextColor = Color.rgb(0xff, 0xff, 0xff)
        //app:ibv_badgeTextColor="#ffffff"
        //holder.icon.badgeColor = Color.rgb(0x00, 0xac, 0xc1)
        //app:ibv_badgeColor="#00ACC1"
        //holder.icon.setLimitBadgeValue(false)
        //app:ibv_badgeLimitValue="false"
        holder.header.setText(homeGridItem.getStringId())



        return view
    }

    class ViewHolder (view: View) {
        var icon: ImageBadgeView = view.findViewById(R.id.imageViewHomeGrid)
        var header: TextView = view.findViewById(R.id.textViewHomeGrid)
    }
}