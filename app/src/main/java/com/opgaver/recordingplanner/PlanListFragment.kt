package com.opgaver.recordingplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater


/**
 * A fragment representing a list of Items.
 */
class PlanListFragment : Fragment() {

    private var columnCount = 1
    val model: ViewModelPlanList by activityViewModels()
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
                        this,
                        this@PlanListFragment.viewLifecycleOwner
                    )
                }
                this@PlanListFragment.recyclerView = view
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