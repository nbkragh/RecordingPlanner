package com.opgaver.recordingplanner


import androidx.databinding.*

class PlanItem(
    private var title: String = "",
    private var category: String = "",
    private var active: Boolean = false,
    var configsFrameTitle: String = "",
    private var startDate: Long,
    private var endDate: Long,
    private var autoIncreaseQuality: Boolean,
    private var notifyDeletion: Boolean
) : BaseObservable() {
    constructor(title: String, category: String) : this(
        title,
        category,
        false,
        "some detail",
        10101010,
        20202020,
        false,
        false
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
    fun getStartDate(): Long = startDate
    fun setStartDate(value: Long){
        if(startDate != value){
            startDate = value
            notifyPropertyChanged(BR.startDate)
        }
    }
    @Bindable
    fun getEndDate(): Long = endDate
    fun setEndDate(value: Long){
        if(endDate != value){
            endDate = value
            notifyPropertyChanged(BR.endDate)
        }
    }

    @Bindable
    fun getNotifyDeletion(): Boolean = notifyDeletion
    fun setNotifyDeletion(value: Boolean) {
        if (notifyDeletion != value) {
            notifyDeletion = value
            notifyPropertyChanged(BR.notifyDeletion)
        }
    }
    @Bindable
    fun getAutoIncreaseQuality(): Boolean = autoIncreaseQuality
    fun setAutoIncreaseQuality(value: Boolean) {
        if (autoIncreaseQuality != value) {
            autoIncreaseQuality = value
            notifyPropertyChanged(BR.autoIncreaseQuality)
        }
    }

    fun printAll() {
        println(title)
        println(category)
        println(active)
        println(configsFrameTitle)
        println(startDate)
        println(endDate)
        println(autoIncreaseQuality)
        println(notifyDeletion)
    }


    companion object {
        @InverseMethod("stringToDate")
        @JvmStatic fun dateToString(value: Long): String {
            var asString = value.toString()
            return ""+asString.subSequence(0,4)+"/"+asString.subSequence(4,6)+"/"+asString.substring(asString.length-2)
        }

        @JvmStatic fun stringToDate(value: String): Long {
            return value.replace("/","").toLong()
        }
    }
}