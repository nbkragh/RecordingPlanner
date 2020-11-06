package com.opgaver.recordingplanner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class PlansFrameActivity : AppCompatActivity(), LifecycleOwner {
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
                .add(R.id.plans_frame_content, planListFrag)
                .commit()
        }



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
    override fun onBackPressed() {
        System.out.println(model.plans.value?.get(0)?.getActive())
        super.onBackPressed()
    }
}