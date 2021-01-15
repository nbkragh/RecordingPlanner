package com.opgaver.recordingplanner

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opgaver.recordingplanner.ui.main_tapped.SectionsPagerAdapter


class MainActivityTabbed : FragmentActivity(), LifecycleOwner {
    val model: ViewModelPlanList by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager2)
       viewPager.setOnTouchListener(OnTouchListener { v, event -> true })
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)



        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0  -> {
                    tab.text ="PLANS"
                }
                else -> {
                    tab.text ="RECORDINGS"
                }
            }
        }.attach()


        findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { view ->
            model.addPlan(PlanItem("NEW"))
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