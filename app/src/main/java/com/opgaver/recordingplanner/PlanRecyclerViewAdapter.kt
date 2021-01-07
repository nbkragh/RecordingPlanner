package com.opgaver.recordingplanner

import android.app.DatePickerDialog
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.databinding.PlanItemBinding
import kotlinx.android.synthetic.main.plan_item.view.*
import java.util.*


class PlanRecyclerViewAdapter(
    private val plansData: ViewModelPlanList,
    private val container: ViewGroup,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.planItemViewHolder>(),
    DatePickerDialog.OnDateSetListener {

    private var expandedPosition = -1
    val calendar = Calendar.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var datePickingReciever: TextView
    init {
        plansData.plans.observe(lifecycleOwner, Observer<MutableList<PlanItem>> {
            notifyDataSetChanged()
        })

    }

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv!!)
        recyclerView = rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): planItemViewHolder {

        val binding: PlanItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.plan_item,
            container,
            false
        )
        return planItemViewHolder(binding, plansData, lifecycleOwner).also {
            binding.root.item_config_start_title.setOnClickListener {
                datePickingReciever = binding.itemConfigStartTitle
                onDateClick(it, binding.index)

            }
            binding.root.item_config_end_title.setOnClickListener {
                datePickingReciever = binding.itemConfigEndTitle
                onDateClick(it, binding.index)

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
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = plansData.plans.value!!.size

    inner class planItemViewHolder(
        val binding: PlanItemBinding,
        val viewModel: ViewModelPlanList,
        val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlanItem, position: Int) {
            binding.item = item
            binding.index = position
            binding.viewmodel = viewModel
            //binding.setLifecycleOwner(lifecycleOwner)
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.planItemCategory+ "'"
        }
    }


    fun onDateClick(viewholder: View, index: Int) {
        DatePickerDialog(
            container.context, this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        var monthString: String =
            if ((month + 1).toString().length < 2) "0" + (month + 1) else "" + (month + 1)
        var dayString: String = if (day.toString().length < 2) "0" + day else "" + day

        datePickingReciever.setText("$year" + monthString + dayString)

    }

    fun smoothSnapToPosition(position: Int) {
        object : LinearSmoothScroller(recyclerView.context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 120f / displayMetrics.densityDpi
            }
        }.apply {
            targetPosition = position }.let {
                recyclerView.layoutManager?.startSmoothScroll(it)
            }

    }

}