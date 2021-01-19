package com.opgaver.recordingplanner.recordinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.R
import com.opgaver.recordingplanner.planlist.PlanItem
import kotlinx.android.synthetic.main.recording_item.view.*
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RecordingRecyclerViewAdapter(

) : RecyclerView.Adapter<RecordingRecyclerViewAdapter.ViewHolder>() {
    var  recordings: MutableList<PlanItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recording_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recordings[position]
        holder.name.text = "${item.getTitle()}"
        holder.startDate.text = "${item.getStartDate()} 00:00"
        holder.endDate.text = "${item.getEndDate()} 23:59"
    }

    override fun getItemCount(): Int = recordings.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.plan_name
        val startDate : TextView = view.start_date_text
        val endDate: TextView = view.end_date_text

        override fun toString(): String {
            return super.toString() + " '" + endDate.text + "'"
        }
    }

    @JvmName("setRecordings1")
    fun setRecordings(newRecordings : List<PlanItem>){

        recordings = newRecordings.toMutableList()
            notifyDataSetChanged()
        }


}