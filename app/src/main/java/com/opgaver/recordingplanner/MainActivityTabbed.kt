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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opgaver.recordingplanner.persistence.PlannerDatabase
import com.opgaver.recordingplanner.planlist.PlanItem
import com.opgaver.recordingplanner.ui.main_tapped.SectionsPagerAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainActivityTabbed : FragmentActivity(), LifecycleOwner {
    val model: ViewModelPlanList by viewModels()
    lateinit var database : PlannerDatabase
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics
        database = PlannerDatabase.getInstance(applicationContext)
        prepopulateDatabase()

        setContentView(R.layout.activity_main_tabbed)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, this)
        viewPager= findViewById(R.id.view_pager2)
        viewPager.setOnTouchListener( { v, event -> true })
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
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun prepopulateDatabase(){
        GlobalScope.launch {
            database.clearAllTables()


            if ( database.planitemDAO().getAll().size < 3) {
                val now : LocalDate = LocalDate.now()
                val nowAsLong : Long = PlanItem.formatDateIntsToDateLong(now.year,now.monthValue,now.dayOfMonth)

                val itemList : MutableList<PlanItem> = emptyList<PlanItem>().toMutableList()
                itemList.add(PlanItem("TEST_1",nowAsLong,nowAsLong+1))
                itemList.add(PlanItem("TEST_2",nowAsLong+1,nowAsLong+2))
                itemList.add(PlanItem("TEST_3",nowAsLong+2,nowAsLong+3))
                database.planitemDAO().insertAll( itemList)
            }
        }

    }

    override fun onBackPressed() {
        viewPager.currentItem = if(viewPager.currentItem == 1) 0 else 0
    }
}