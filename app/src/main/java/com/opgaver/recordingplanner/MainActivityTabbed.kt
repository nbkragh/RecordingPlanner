package com.opgaver.recordingplanner

import android.os.Bundle
import android.view.View.OnTouchListener
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opgaver.recordingplanner.ui.main.SectionsPagerAdapter


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
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()


/*        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }
}