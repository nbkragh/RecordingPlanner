package com.opgaver.recordingplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.databinding.PlanItemBinding
import com.opgaver.recordingplanner.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.plan_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PlanRecyclerViewAdapter(
    private val plansData: ViewModelPlanList,
    private val container: ViewGroup?,
    private val lifecycleOwner: LifecycleOwner,
    private val contextAsDataClickHandler: dateClickHandler
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.planItemViewHolder>() {

    private var expandedPosition = -1

    var recyclerView: RecyclerView? = null

    init {
        plansData.plans.observe(lifecycleOwner, Observer<List<PlanItem>> {
            notifyDataSetChanged()
        })

    }

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv!!)
        recyclerView = rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): planItemViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.plan_item, parent, false)
        val binding: PlanItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.plan_item,
            container,
            false
        )
        binding.lifecycleOwner = lifecycleOwner
        return planItemViewHolder(binding, plansData, lifecycleOwner).also {
            binding.root.item_config_start.setOnClickListener {  contextAsDataClickHandler.onClick( it, binding.index )
                //binding.executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: planItemViewHolder, position: Int) {
        holder.bind(plansData.plans.value!!.get(position), position)
        val isExpanded = position === expandedPosition
        holder.binding.itemConfigsFrame.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded


        //holder.binding.planitemslist = plansData.getPlans().value!!.get(position)
        holder.binding.expandButton.setOnClickListener {

            expandedPosition = if (isExpanded) -1 else position

            (recyclerView!!.layoutManager as LinearLayoutManager)!!.scrollToPositionWithOffset(
                position,
                0
            )
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int = plansData.plans.value!!.size

    inner class planItemViewHolder(
        val binding: PlanItemBinding,
        val viewModel: ViewModelPlanList,
        val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: PlanItem, position: Int) {
            binding.item = item
            binding.index = position
            binding.viewmodel = viewModel
            //binding.executePendingBindings()
            binding.setLifecycleOwner(lifecycleOwner)

        }

        override fun toString(): String {
            return super.toString() + " '" + binding.planItemTitle + "'"
        }
    }

    interface dateClickHandler {
        fun onClick(viewholder: View, index : Int)
    }
}