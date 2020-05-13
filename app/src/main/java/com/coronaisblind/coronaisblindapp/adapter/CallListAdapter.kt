package com.coronaisblind.coronaisblindapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.data.Call
import kotlinx.android.synthetic.main.call_upcoming_row.view.*

class CallListAdapter(private val context: Context, private val callList: List<Call>) : RecyclerView.Adapter<CallListAdapter.ViewHolder>() {

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
        holder.tvTime.text = currentCall.time.toDate().time.toString(10) // TODO: Format time
        holder.btnGo.setOnClickListener {
            // TODO: go to call screen
        }
    }


    inner class ViewHolder(callView: View): RecyclerView.ViewHolder(callView) {
        val tvName = callView.tvName
        val tvTime = callView.tvTime
        val btnGo = callView.btnGo
    }
}