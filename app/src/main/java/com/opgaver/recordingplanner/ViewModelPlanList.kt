package com.opgaver.recordingplanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opgaver.recordingplanner.dummy.DummyContent



class ViewModelPlanList : ViewModel() {

    val plans: MutableLiveData<MutableList<PlanItem>> by lazy {
        MutableLiveData<MutableList<PlanItem>>().also {
            it.setValue(loadPlans())
        }
    }

    private fun loadPlans(): MutableList<PlanItem> {
        val length = 10
        val list: MutableList<PlanItem> = emptyList<PlanItem>().toMutableList()

        ((DummyContent.ITEMS).subList(0, length)).forEach {
            list.add(
                PlanItem(
                    it.id,
                    "Recording Plan"
                )
            )
        }
        return list
    }

    fun addPlan(){
        println(plans)
        plans.value?.add(PlanItem("NEW","Recording Plan"))
        plans.value = plans.value
    }

}