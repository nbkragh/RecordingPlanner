package com.opgaver.recordingplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.databinding.PlanItemBinding
import com.opgaver.recordingplanner.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.plan_item.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PlanRecyclerViewAdapter(
    private val plansData: ViewModelPlanList,
    private val container: ViewGroup?,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {

    private var  expandedPosition = -1

    var recyclerView: RecyclerView? = null
    init {
        plansData.getPlans().observe(lifecycleOwner, Observer<List<PlanItem>>{
            notifyDataSetChanged()
        })
    }
    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv!!)
        recyclerView = rv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_item, parent, false)
        val binding : PlanItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.plan_item, container, false)
        binding.lifecycleOwner = this@PlanRecyclerViewAdapter.lifecycleOwner

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val isExpanded = position === expandedPosition
        holder.binding.itemConfigsFrame.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded


        holder.binding.planitem = plansData.getPlans().value!!.get(position)
        holder.binding.expandButton.setOnClickListener {

            expandedPosition = if (isExpanded) -1 else position

            (recyclerView!!.layoutManager  as LinearLayoutManager)!!.scrollToPositionWithOffset(position,0)
            notifyItemChanged(position)

        }
        holder.binding.executePendingBindings()
        /*holder.titleView.text = item.title
        holder.configTitleView.text = item.category*/
    }

    override fun getItemCount(): Int = plansData.getPlans().value!!.size

    inner class ViewHolder(val binding: PlanItemBinding) : RecyclerView.ViewHolder(binding.root) {
/*        val titleView: TextView = binding.planitem.findViewById(R.id.plan_item_title)
        val configFrame: LinearLayout = view.findViewById(R.id.item_configs_frame)
        val configTitleView: TextView = view.findViewById(R.id.item_configs_frame_title)
        val expandButton: ImageButton = view.findViewById(R.id.expand_button)*/
        override fun toString(): String {
            return super.toString() + " '" + binding.planItemTitle + "'"
        }
    }
}