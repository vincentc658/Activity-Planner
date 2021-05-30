package com.library.dailyactivityplanner

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdapterList(
    private val context: Activity,
    private val callback: SetOnDeleteListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var  data: ArrayList<ActivityModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        Item(LayoutInflater.from(context).inflate(R.layout.rv_item_activity, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as Item
        val itemData = data[position]
        holder.tvActivityName.text = itemData.activity
        holder.tvLocation.text = "Location : ${itemData.location}"
        holder.tvTimeStart.text = "Start : ${
            TimeConverter.getDate(itemData.startTime!!, TimeConverter.YYYY_MM_DD_HH_MM_SS)
        }"
        holder.tvTimeEnd.text = "End  : ${
            TimeConverter.getDate(itemData.endTime!!, TimeConverter.YYYY_MM_DD_HH_MM_SS)
        }"
        holder.ivDelete.setOnClickListener {
            callback.onDeleteListener(itemData.id!!)
        }

    }
    fun clearData(){
        data.clear()
        notifyDataSetChanged()
    }
    fun addData(data : ArrayList<ActivityModel>){
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = data.size
    inner class Item(view: View) : RecyclerView.ViewHolder(view) {
        val tvActivityName: TextView = view.findViewById(R.id.tvActivityName)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvTimeStart: TextView = view.findViewById(R.id.tvTimeStart)
        val tvTimeEnd: TextView = view.findViewById(R.id.tvTimeEnd)
        val ivDelete: ImageView = view.findViewById(R.id.ivDelete)

    }
}