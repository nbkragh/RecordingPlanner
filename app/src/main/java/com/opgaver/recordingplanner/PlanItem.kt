package com.opgaver.recordingplanner


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.util.*


class PlanItem(
    private var title: String = "",
    private var category: String = "",
    private var active: Boolean = false,
    var configsFrameTitle: String = "",
    var startDate: Date? = null,
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
        }
    }
}