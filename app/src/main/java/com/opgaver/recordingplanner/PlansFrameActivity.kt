package com.opgaver.recordingplanner

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


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
                .add(R.id.plans_frame_content, planListFrag, "plans_list_fragment")
                .commit()
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            model.addPlan(PlanItem("NEW"))
        }
    }

    override fun onBackPressed() {
        model.plans.value?.forEach({ it.printAll() })
        super.onBackPressed()
    }

}