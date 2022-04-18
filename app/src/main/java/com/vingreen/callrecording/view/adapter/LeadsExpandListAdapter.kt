package com.vingreen.callrecording.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.vingreen.callrecording.R
import com.vingreen.callrecording.responses.menu.LeadStatus
import com.vingreen.callrecording.responses.menu.LeadSubStatus
import com.vingreen.callrecording.utils.ViewCallBack

@SuppressLint("InflateParams")
class LeadsExpandListAdapter(
    private val context: Context?,
    private val menuList: MutableList<LeadStatus>,
    private val itemClickListener: ViewCallBack.LeadsItemClick
) : BaseExpandableListAdapter() {

    private var lastPosition: Int = -1

    override fun getGroup(groupPosition: Int): LeadStatus {
        return menuList[groupPosition]
    }

    override fun getGroupCount(): Int {
        return menuList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup?
    ): View {
        var view = convertView
        val lead = getGroup(groupPosition)
        if (view == null) {
            val inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_lead_head, null)
        }
        val lblListHeader = view!!.findViewById<View>(R.id.tv_head_menu) as TextView
        val imgListHeader = view.findViewById<View>(R.id.img_arrow) as ImageView
        lblListHeader.setTextColor(ContextCompat.getColor(context!!, R.color.black))

        if (lastPosition == groupPosition && isExpanded) {
            lblListHeader.setTypeface(null, Typeface.BOLD)
            lblListHeader.text = lead.LeadStatusName
            lblListHeader.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            imgListHeader.setImageResource(R.drawable.ic_arrow_down)
            imgListHeader.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
        } else {
            lblListHeader.setTypeface(null, Typeface.NORMAL)
            lblListHeader.text = lead.LeadStatusName
            lblListHeader.setTextColor(ContextCompat.getColor(context, R.color.black))
            imgListHeader.setImageResource(R.drawable.ic_arrow_right)
            imgListHeader.setColorFilter(ContextCompat.getColor(context, R.color.black))

        }

        return view
    }

    fun highlightGroup(checkedPosition: Int) {
        lastPosition = checkedPosition
        notifyDataSetChanged()
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): LeadSubStatus {
        return menuList[groupPosition].LeadSubStatusList[childPosititon]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        var view = convertView
        val subLead = getChild(groupPosition, childPosition)
        if (view == null) {
            val inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_lead_child, null)
        }
        val lblListChild = view!!.findViewById<View>(R.id.tv_child) as TextView

        if (lastPosition == groupPosition) {
            lblListChild.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            lblListChild.text = subLead.LeadSubStatusName
        } else {
            lblListChild.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            lblListChild.text = subLead.LeadSubStatusName
        }
        lblListChild.setOnClickListener {
            itemClickListener.onItemClick(getGroup(groupPosition))
        }
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return menuList[groupPosition].LeadSubStatusList.size
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}



