package com.opgaver.recordingplanner.ui.main_tapped

import android.content.Context
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.transition.Visibility
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.opgaver.recordingplanner.MainActivityTabbed
import com.opgaver.recordingplanner.PlanListFragment
import com.opgaver.recordingplanner.R
import com.opgaver.recordingplanner.RecordingListFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentActivity) :
    FragmentStateAdapter(fm) {


    fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getItemCount(): Int {
        // Show 2 total pages.
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        if(position ==0){
            return PlanListFragment.newInstance(1)
        }
        return RecordingListFragment.newInstance(1)
        (context as MainActivityTabbed).findViewById<FloatingActionButton>(R.id.fab).setVisibility(View.GONE)
    }
}