package com.opgaver.recordingplanner

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton


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

    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}