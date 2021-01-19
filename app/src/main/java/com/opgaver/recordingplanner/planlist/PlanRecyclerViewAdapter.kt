package com.opgaver.recordingplanner.planlist

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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.opgaver.recordingplanner.R
import com.opgaver.recordingplanner.ViewModelPlanList
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
            binding.root.item_config_start_date_value.setOnClickListener {
                datePickingReciever = binding.itemConfigStartDateValue
                onDateClick()
            }
            binding.root.item_config_end_date_value.setOnClickListener {
                datePickingReciever = binding.itemConfigEndDateValue
                onDateClick()
            }
            binding.root.plan_item_onoff_switch.setOnClickListener {
                Snackbar.make(container, "Plan is active", Snackbar.LENGTH_INDEFINITE)
                    .setAnchorView(container).setDuration(1000).show()
            }
            binding.root.delete_plan_button.setOnClickListener {
                binding.root.alpha = 0.5F
                deleteItem(binding.index)
            }
            binding.gotoRecordingsButton.setOnClickListener{
                println(model.selectedPlan.value )
                model.selectedPlan.value = binding.item?.pid
            }
        }
    }

    override fun onBindViewHolder(holder: planItemViewHolder, position: Int) {
        holder.bind(plans.get(position), position)
        if (holder.expanded) {
            holder.binding.expandButton.text = "SAVE"
            holder.binding.expandButton.foreground.alpha = 0
            holder.binding.itemConfigsFrame.setVisibility(View.VISIBLE)
        } else {
            holder.binding.expandButton.text = ""
            holder.binding.expandButton.foreground.alpha = 255
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


    fun deleteItem(i: Int) {
        val deleteItem = plans[i]
        Snackbar.make(container, "1 Item Plan deleted", Snackbar.LENGTH_LONG)
            .setAction("    UNDO     ") {
                var alpha = recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.alpha!!
                if (alpha < 1.0F) {
                    recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.alpha = 1.0F
                } else {
                        (recyclerView.findViewHolderForAdapterPosition(i) as planItemViewHolder).animate()
                    notifyDataSetChanged()
                }
            }.setDuration(2800)
            .setAnchorView(recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.anchor)
            .addCallback(
                object : Snackbar.Callback() {
                    override fun onDismissed(snackbar: Snackbar, event: Int) {
                        if (event != DISMISS_EVENT_ACTION) {
                            model.deletePlan(deleteItem)
                            plans.removeAt(i)
                            notifyItemRemoved(i)
                        }
                    }
                }
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

    @JvmName("setPlans1")
    fun setPlans(newPlans: List<PlanItem>) {
        if (newPlans.size > plans.size) {
            if (itemCount > 0) {
                plans.add(newPlans.last())
                notifyItemInserted(itemCount)
                recyclerView!!.doOnNextLayout {
                    (recyclerView!!.findViewHolderForLayoutPosition(plans.size - 1) as? planItemViewHolder)?.animate()
                    smoothSnapToPosition(itemCount)
                }
            } else {
                plans = newPlans.toMutableList()
                for (i in 0 until itemCount) {
                    recyclerView.doOnNextLayout {
                        (recyclerView!!.findViewHolderForLayoutPosition(
                            i
                        ) as? planItemViewHolder)?.animate(i * 50L)
                    }
                    notifyItemInserted(i)
                }
            }
            notifyDataSetChanged()
        } else {
            plans = newPlans.toMutableList()
            notifyDataSetChanged()
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
                    val snackBar = Snackbar.make(container, "SAVED", Snackbar.LENGTH_INDEFINITE)
                        .setDuration(1000)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    snackBar.show()
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

        fun animate(delay: Long = 0L) {
            this.itemView.x = container.width.toFloat()
            ObjectAnimator.ofFloat(this.itemView, "translationX", 0F).apply {
                interpolator = Interpolators(Easings.BACK_OUT)
                duration = 600
                startDelay = delay
                start()
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.planItemCategory + "'"
        }
    }
}