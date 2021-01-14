package com.opgaver.recordingplanner

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opgaver.recordingplanner.persistence.PlannerDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    // Null indtil onCreateView.
    private var toPlans_Button: Button? = null
    private var toRecordings_Button: Button? = null

    lateinit var database : PlannerDatabase
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        database = PlannerDatabase.getInstance(applicationContext)
        prepopulateDatabase()

        setContentView(R.layout.activity_main)

        toPlans_Button = findViewById(R.id.toPlans_Button)
        toPlans_Button!!.setOnClickListener {
            val intent = Intent(this, PlansFrameActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        }

        toRecordings_Button = findViewById(R.id.toRecordings_button)
        toRecordings_Button!!.setOnClickListener {
            val intent = Intent(this, MainActivityTabbed::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    fun prepopulateDatabase(){
        GlobalScope.launch {
            database.clearAllTables()


            if ( database.planitemDAO().getAll().size < 3) {
                val now : LocalDate = LocalDate.now()
                val nowAsLong : Long = PlanItem.formatDateIntsToDateLong(now.year,now.monthValue,now.dayOfMonth)

                val itemList : MutableList<PlanItem> = emptyList<PlanItem>().toMutableList()
                itemList.add(PlanItem("FIRST",nowAsLong,nowAsLong+1))
                itemList.add(PlanItem("SECOND",nowAsLong+1,nowAsLong+2))
                itemList.add(PlanItem("THIRD",nowAsLong+2,nowAsLong+3))
                database.planitemDAO().insertAll( itemList)
            }
        }

    }



}