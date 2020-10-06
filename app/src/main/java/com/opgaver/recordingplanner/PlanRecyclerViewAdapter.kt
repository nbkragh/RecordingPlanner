package com.opgaver.recordingplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PlanRecyclerViewAdapter(
    private val values: List<DummyItem>,
    private val container: ViewGroup?
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {

    private var  expandedPosition = -1

    var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv!!)
        recyclerView = rv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val isExpanded = position === expandedPosition
        holder.configFrame.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded
        holder.expandButton.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position

            (recyclerView!!.layoutManager  as LinearLayoutManager)!!.scrollToPositionWithOffset(position,0)
            notifyItemChanged(position)

        }

        val item = values[position]
        holder.titleView.text = item.id
        holder.configTitleView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.plan_item_title)
        val configFrame: LinearLayout = view.findViewById(R.id.item_configs_frame)
        val configTitleView: TextView = view.findViewById(R.id.item_configs_frame_title)
        val expandButton: ImageButton = view.findViewById(R.id.expand_button)
        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }
}