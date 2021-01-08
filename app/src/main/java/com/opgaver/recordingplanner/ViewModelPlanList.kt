package com.opgaver.recordingplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opgaver.recordingplanner.dummy.DummyContent
import com.opgaver.recordingplanner.persistence.PlannerDatabase
import kotlinx.coroutines.launch
import java.util.ArrayList


class ViewModelPlanList(app: Application) : AndroidViewModel(app) {

    val database: PlannerDatabase = PlannerDatabase.getInstance(app)

    val plans: MutableLiveData<List<PlanItem>> =  MutableLiveData()

/*    val plans: MutableLiveData<MutableList<PlanItem>> by lazy {
        MutableLiveData<MutableList<PlanItem>>().also {
            it.postValue(loadPlans())
        }
    }*/

    init {
        plans.value = ArrayList()
        loadPlans()
    }
    private fun loadPlans(){
        val length = 10
        viewModelScope.launch {
            plans.postValue(database.planitemDAO().getAll().toMutableList())
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+plans)
        }
/*        ((DummyContent.ITEMS).subList(0, length)).forEach {
            list.add(
                PlanItem(
                    it.id,
                    "Recording Plan"
                )
            )
        }*/
    }

    fun addPlan() {
/*        println(plans)
        plans.value?.add(PlanItem("NEW","Recording Plan"))
        plans.value = plans.value*/
    }

    fun initializeData(db: PlannerDatabase) {


    }

}