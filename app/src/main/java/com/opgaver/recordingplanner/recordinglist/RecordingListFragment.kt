package com.opgaver.recordingplanner.recordinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.opgaver.recordingplanner.R
import com.opgaver.recordingplanner.ViewModelPlanList
import com.opgaver.recordingplanner.planlist.PlanItem
import java.util.*


/**
 * A fragment representing a list of Items.
 */
class RecordingListFragment : Fragment() {

    private var columnCount = 1
    private var planid = -1
    val model: ViewModelPlanList by activityViewModels() //scoped til fragments ejer activity
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recording_recyclerview, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = RecordingRecyclerViewAdapter()
            }
            recyclerView = view
            model.plans.observe(viewLifecycleOwner, Observer {

                if (planid > -1) {
                    var selecteditems = ArrayList<PlanItem>()
                    for (plan in it) {
                        if (plan.pid == planid) {
                            selecteditems.add(plan)
                        }
                    }
                    (recyclerView!!.adapter as RecordingRecyclerViewAdapter).setRecordings(
                        selecteditems
                    )
                } else {
                    (recyclerView!!.adapter as RecordingRecyclerViewAdapter).setRecordings(it)
                }
            })
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RecordingListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}