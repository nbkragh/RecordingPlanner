package com.opgaver.recordingplanner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlansFrameActivity : AppCompatActivity(), LifecycleOwner{
    private val fragmentManager = supportFragmentManager
    val model: ViewModelPlanList by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans_frame)
        setSupportActionBar(findViewById(R.id.toolbar))


        if (savedInstanceState == null) {

            val planListFrag = PlanListFragment().apply {
                arguments = intent.extras
            }
            fragmentManager.beginTransaction()
                .add(R.id.plans_frame_content, planListFrag, "plans_list_fragment")
                .commit()
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
/*            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            model.addPlan()
            (( fragmentManager.findFragmentByTag("plans_list_fragment") as PlanListFragment)
                .recyclerView?.adapter as PlanRecyclerViewAdapter).let {
                    it.smoothSnapToPosition( it.itemCount -1)
                }

        }
    }


    override fun onBackPressed() {
        model.plans.value?.forEach({ it.printAll() })
        super.onBackPressed()
    }

}