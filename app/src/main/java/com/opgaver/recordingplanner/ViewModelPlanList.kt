package com.opgaver.recordingplanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.opgaver.recordingplanner.dummy.DummyContent
import com.opgaver.recordingplanner.dummy.DummyContent.DummyItem

/**
 * that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ViewModelPlanList : ViewModel() {

    public val plans: MutableLiveData<List<PlanItem>> by lazy {
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
        val length = 9;
        val list: MutableList<PlanItem> = emptyList<PlanItem>().toMutableList()

        ((DummyContent.ITEMS).subList(0, length)).forEach {
            list.add(
                PlanItem(
                    it.id,
                    it.content
                )
            )
        }
        return list
    }
}