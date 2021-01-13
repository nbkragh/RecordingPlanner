package com.opgaver.recordingplanner

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.opgaver.recordingplanner.databinding.PlanItemBinding
import com.ramijemli.easings.Easings
import com.ramijemli.easings.Interpolators
import kotlinx.android.synthetic.main.plan_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class PlanRecyclerViewAdapter(
    val model: ViewModelPlanList,
    private val container: ViewGroup
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.planItemViewHolder>(),
    DatePickerDialog.OnDateSetListener {

    var expandedPosition = -1
    val calendar = Calendar.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var datePickingReciever: TextView
    var plans: MutableList<PlanItem> = ArrayList()

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv)
        recyclerView = rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): planItemViewHolder {
        val binding: PlanItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.plan_item,
            container,
            false
        )
        if (expandedPosition == itemCount - 1) smoothSnapToPosition(itemCount + 1)

        return planItemViewHolder(binding, model).also {
            binding.root.item_config_start_title.setOnClickListener {
                datePickingReciever = binding.itemConfigStartTitle
                onDateClick()
            }
            binding.root.item_config_end_title.setOnClickListener {
                datePickingReciever = binding.itemConfigEndTitle
                onDateClick()
            }
            binding.root.plan_item_onoff_switch.setOnClickListener {

                Snackbar.make(container, "Plan is active", Snackbar.LENGTH_INDEFINITE)
                    .setAnchorView(container).setDuration(1000).show()

            }
        }
    }

    override fun onBindViewHolder(holder: planItemViewHolder, position: Int) {
        holder.bind(plans.get(position), position)

        if (holder.expanded) {
            (holder.binding.expandButton).setImageResource(R.drawable.baseline_expand_less_white_18dp)
            holder.binding.itemConfigsFrame.setVisibility(View.VISIBLE)

        } else {
            (holder.binding.expandButton).setImageResource(R.drawable.baseline_expand_more_white_18dp)
            holder.binding.itemConfigsFrame.setVisibility(View.GONE)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = plans.size

    fun onDateClick() {
        DatePickerDialog(
            container.context, this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        datePickingReciever.setText(
            PlanItem.formatDateIntsToDateLong(year, month + 1, day).toString()
        )
    }

    fun smoothSnapToPosition(position: Int) {
        object : LinearSmoothScroller(recyclerView.context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 120f / displayMetrics.densityDpi
            }
        }.apply {
            targetPosition = position
        }.let {
            recyclerView.layoutManager?.startSmoothScroll(it)
        }

    }

    inner class planItemViewHolder(
        val binding: PlanItemBinding,
        val viewModel: ViewModelPlanList
    ) : RecyclerView.ViewHolder(binding.root) {
        var expanded: Boolean = false
        fun bind(item: PlanItem, position: Int) {
            binding.item = item
            binding.index = position
            binding.viewmodel = viewModel
            //binding.setLifecycleOwner(lifecycleOwner)
            binding.expandButton.setOnClickListener {
                if (expanded) {
                    model.updatePlan(plans.get(position))
                    Snackbar.make(container, "SAVED", Snackbar.LENGTH_INDEFINITE)
                        .setAnchorView(container.right).setDuration(1000).show()
                } else {
                    recyclerView!!.doOnNextLayout {
                        smoothSnapToPosition(position)
                        itemView.isActivated = expanded
                    }
                }
                expanded = !expanded
                notifyDataSetChanged()
            }
            binding.executePendingBindings()
        }

        fun animate(delay: Long = 0) {

            this.itemView.x = container.width.toFloat()

            ObjectAnimator.ofFloat(this.itemView, "translationX", 0f).apply {
                interpolator = Interpolators(Easings.ELASTIC_OUT)
                duration = 800
                startDelay = delay
                start()
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.planItemCategory + "'"
        }
    }
}