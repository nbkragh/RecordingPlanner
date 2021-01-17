package com.opgaver.recordingplanner.planlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.opgaver.recordingplanner.R
import com.opgaver.recordingplanner.SwipetToDeleteCallback
import com.opgaver.recordingplanner.ViewModelPlanList


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

        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { view ->
            model.addPlan(PlanItem("NEW"))
        }

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
            view.isFocusableInTouchMode = true
            ItemTouchHelper(SwipetToDeleteCallback((recyclerView!!.adapter as PlanRecyclerViewAdapter)!!)).attachToRecyclerView(
                recyclerView
            )
            model.plans.observe(viewLifecycleOwner, Observer {
                (recyclerView!!.adapter as PlanRecyclerViewAdapter).setPlans(it)
            })

        }

        return recyclerView
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
        (recyclerView!!.adapter as? PlanRecyclerViewAdapter)?.setPlans(model.plans.value ?: ArrayList())
    }

    override fun onPause() {
        super.onPause()
        (recyclerView!!.adapter as PlanRecyclerViewAdapter).setPlans(ArrayList())
    }

    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PlanListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}