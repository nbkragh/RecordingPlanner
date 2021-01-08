package com.opgaver.recordingplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.opgaver.recordingplanner.persistence.PlannerDatabase
import kotlinx.coroutines.launch
import java.util.ArrayList


class ViewModelPlanList(app: Application) : AndroidViewModel(app) {

    val database: PlannerDatabase = PlannerDatabase.getInstance(app)

    val plans: MutableLiveData<List<PlanItem>> =  MutableLiveData()

    init {
        plans.value = ArrayList()
        loadPlans()
    }
    private fun loadPlans(){
        viewModelScope.launch {
            plans.postValue(database.planitemDAO().getAll().toMutableList())
        }
    }

    fun addPlan(plan: PlanItem) {
        viewModelScope.launch {

            database.planitemDAO().insert(plan)
            plans.postValue(database.planitemDAO().getAll().toMutableList())
        }
    }

}