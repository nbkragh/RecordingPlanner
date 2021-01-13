package com.opgaver.recordingplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import kotlinx.coroutines.android.awaitFrame


/**
 * A fragment representing a list of Items.
 */
class PlanListFragment : Fragment() {

    private var columnCount = 1
    val model: ViewModelPlanList by activityViewModels() //scoped til fragments ejer activity
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        val inflater = TransitionInflater.from(requireContext())

        exitTransition = inflater.inflateTransition(R.transition.fade)
        reenterTransition = inflater.inflateTransition(R.transition.fade)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (recyclerView == null) {
            val view = inflater.inflate(R.layout.fragment_plan_recyclerview, container, false)
            // Set the adapter
            if (view is RecyclerView) {
                with(view) {
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(
                            context,
                            RecyclerView.VERTICAL,
                            false
                        )
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = PlanRecyclerViewAdapter(
                        model,
                        this
                    )
                }
                recyclerView = view
                model.plans.observe(viewLifecycleOwner, Observer {
                    val adapter = (recyclerView!!.adapter as PlanRecyclerViewAdapter)
                    if (it.size > adapter.plans.size) {
                        if (adapter.itemCount > 0) {
                            adapter.plans.add(it.last())
                            adapter.notifyItemInserted(adapter.itemCount)
                            recyclerView!!.doOnNextLayout {
                                (recyclerView!!.findViewHolderForLayoutPosition(adapter.plans.size - 1) as? PlanRecyclerViewAdapter.planItemViewHolder)?.animate()
                                adapter.smoothSnapToPosition(adapter.itemCount)
                            }
                        } else {

                            adapter.plans = it.toMutableList()
                            for (i in 0 until adapter.itemCount) {
                                recyclerView!!.doOnNextLayout { (recyclerView!!.findViewHolderForLayoutPosition(i) as? PlanRecyclerViewAdapter.planItemViewHolder)?.animate(i * 50L) }
                                adapter.notifyItemInserted(i)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.plans = it.toMutableList()
                        adapter.notifyDataSetChanged()
                    }
                })
            }
        }
        return recyclerView
    }
    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int, model: ViewModelPlanList) =
            PlanListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}