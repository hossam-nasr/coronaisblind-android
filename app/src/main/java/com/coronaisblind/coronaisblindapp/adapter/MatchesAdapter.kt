package com.coronaisblind.coronaisblindapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.data.Call
import kotlinx.android.synthetic.main.call_reveal_row.view.*
import kotlinx.android.synthetic.main.call_upcoming_row.view.tvName
import kotlinx.android.synthetic.main.call_upcoming_row.view.tvTime
import kotlinx.android.synthetic.main.prev_call_row.view.*
import java.util.*

class MatchesAdapter(private val context: Context, private var callList: List<Call>) :
    RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.call_reveal_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return callList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCall = callList[position]
        holder.tvName.text =
            context.getString(R.string.full_name, currentCall.name, currentCall.lastName)
        val sdf = java.text.SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
        holder.tvSince.text =  context.getString(R.string.known_since, sdf.format(currentCall.time.toDate()))
        holder.tvEmail.text = context.getString(R.string.email_reveal, currentCall.email)
    }

    fun updateList(list: List<Call>) {
        callList = list
        notifyDataSetChanged()
    }


    inner class ViewHolder(callView: View) : RecyclerView.ViewHolder(callView) {
        val tvName = callView.tvName
        val tvSince = callView.tvSince
        val tvEmail = callView.tvEmail
    }
}