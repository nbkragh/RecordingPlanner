package com.opgaver.recordingplanner


import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "Planitems")
class PlanItem(
    @DatabaseField
    private var title: String = "",
    @DatabaseField
    private var category: String = "",
    @DatabaseField
    private var active: Boolean = false,
    var configsFrameTitle: String = "",
    private var startDate: Date? = null,
    var endDate: Date?,
    var recordQuality: Int?,
    var autoIncreaseQuality: Boolean,
    var notifyDeletion: Boolean,
    var brief: String = ""
) : BaseObservable() {
    constructor(title: String, category: String) : this(
        title,
        category,
        false,
        "some detail",
        Date(),
        Date(),
        1,
        true,
        true,
        "brief"
    )


    @Bindable
    fun getTitle(): String = title

    fun setTitle(value: String) {
        if (this.title != value) {
            this.title = value
            notifyPropertyChanged(BR.title)
        }
    }

    @Bindable
    fun getCategory(): String = category
    fun setCategory(value: String) {
        if (category != value) {
            category = value
            notifyPropertyChanged(BR.category)
        }
    }

    @Bindable
    fun getActive(): Boolean = active
    fun setActive(value: Boolean) {
        if (active != value) {
            active = value
            notifyPropertyChanged(BR.active)
        }
    }

    @Bindable
    fun getStartDate(): Date? = startDate
    fun setStartDate(value: Date){
        if(startDate != value){
            startDate = value
            notifyPropertyChanged(BR.startDate)
        }

    }


    fun printAll() {
        println(title)
        println(category)
        println(active)
        println(configsFrameTitle)
        println(startDate)
        println(endDate)
        println(recordQuality)
        println(autoIncreaseQuality)
        println(notifyDeletion)
        println(brief)
    }


}