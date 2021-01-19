package com.opgaver.recordingplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.opgaver.recordingplanner.persistence.PlannerDatabase
import com.opgaver.recordingplanner.planlist.PlanItem
import kotlinx.coroutines.launch


class ViewModelPlanList(app: Application) : AndroidViewModel(app) {

    val database: PlannerDatabase = PlannerDatabase.getInstance(app)

    val selectedPlan : MutableLiveData<Int> by lazy{ MutableLiveData<Int>() }
    val plans  = database.planitemDAO().getAllLive()

    init {
        selectedPlan.value = -1
    }
     fun addPlan(plan: PlanItem) {
        viewModelScope.launch {
            database.planitemDAO().insert(plan)
        }
    }

    fun updatePlan(plan: PlanItem){
        viewModelScope.launch {
            database.planitemDAO().updateUsers(plan)
        }
    }

    fun deletePlan(plan: PlanItem){
        viewModelScope.launch {
            database.planitemDAO().delete(plan)
        }
    }

}