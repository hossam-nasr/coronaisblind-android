package com.coronaisblind.coronaisblindapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.data.Call
import kotlinx.android.synthetic.main.call_upcoming_row.view.*
import java.util.*

class CallListAdapter(private val context: Context, private var callList: List<Call>) : RecyclerView.Adapter<CallListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.call_upcoming_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return callList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCall = callList[position]
        holder.tvName.text = currentCall.name
        val sdf = java.text.SimpleDateFormat("h:mm a z", Locale.getDefault())
        holder.tvTime.text = sdf.format(currentCall.time.toDate())
        holder.btnGo.setOnClickListener {
            // TODO: go to call screen
        }
    }

    fun updateList(list: List<Call>) {
        callList = list
        notifyDataSetChanged()
    }


    inner class ViewHolder(callView: View): RecyclerView.ViewHolder(callView) {
        val tvName = callView.tvName
        val tvTime = callView.tvTime
        val btnGo = callView.btnGo
    }
}