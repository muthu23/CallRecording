package com.vingreen.callrecording.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.vingreen.callrecording.R
import com.vingreen.callrecording.responses.leads.Lead

internal class LeadsListAdapter(
   private val context: Context,
   private val leadsList: List<Lead>
) : RecyclerView.Adapter<LeadsListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_leads_details, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val leads: Lead = leadsList[position]

        viewHolder.tvUserName.text = leads.LeadName.trim()
        viewHolder.tvUserMobile.text = leads.MobileNumber.trim()
        viewHolder.tvUserAlterMobile.text = leads.AlterMobileNumber.trim()
        viewHolder.tvUserEmail.text = leads.EmailId.trim()

        /*Glide.with(context).load(leads.).placeholder(
            AppCompatResources.getDrawable(context, R.drawable.ic_profile)
        ).into(viewHolder.imgProfile)*/

    }

    override fun getItemCount(): Int {
        return leadsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvUserName: TextView = itemView.findViewById(R.id.tv_name)
        var tvUserMobile: TextView = itemView.findViewById(R.id.tv_mobile)
        var tvUserAlterMobile: TextView = itemView.findViewById(R.id.tv_alter_mobile)
        var tvUserEmail: TextView = itemView.findViewById(R.id.tv_email)
        var imgProfile: ImageView = itemView.findViewById(R.id.img_user)
        var imgMsg: ImageView = itemView.findViewById(R.id.img_msg)
        var imgCall: ImageView = itemView.findViewById(R.id.img_call)
        var imgWhatsapp: ImageView = itemView.findViewById(R.id.img_whatsapp)
        var imgEdit: ImageView = itemView.findViewById(R.id.img_edit)
        var layoutGoView: LinearLayout = itemView.findViewById(R.id.lnr_go_view)
    }

}