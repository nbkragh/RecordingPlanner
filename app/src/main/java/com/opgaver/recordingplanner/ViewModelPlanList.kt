package com.opgaver.recordingplanner

import android.view.View
import android.widget.TextView
import androidx.databinding.InverseMethod
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opgaver.recordingplanner.dummy.DummyContent


class ViewModelPlanList : ViewModel() {

    val plans: MutableLiveData<List<PlanItem>> by lazy {
        MutableLiveData<List<PlanItem>>().also {
            it.setValue(loadPlans())
        }
    }

    /*fun getPlans(): LiveData<List<PlanItem>> {
        return plans
    }
    fun getPlan(index : Int): PlanItem{
        return getPlans().value!!.get(index)
    }*/
    private fun loadPlans(): List<PlanItem> {
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


}