package com.opgaver.recordingplanner


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.InverseMethod
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planitems")
data class PlanItem(
    @ColumnInfo(name = "title") private var title: String = "",
    @ColumnInfo(name = "category") private var category: String = "",
    @ColumnInfo(name = "active") private var active: Boolean = false,
    @ColumnInfo(name = "start_Date") private var startDate: Long,
    @ColumnInfo(name = "end_Date") private var endDate: Long,
    @ColumnInfo(name = "auto_incr_quality") private var autoIncreaseQuality: Boolean,
    @ColumnInfo(name = "notify_delete") private var notifyDeletion: Boolean
) : BaseObservable() {
    constructor( title: String, start: Long, end : Long ) : this(
        title,
        "Recording Plan",
        false,
        start,
        end,
        false,
        false
    )

    @PrimaryKey(autoGenerate = true)
    var pid: Int = 0

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

        fun formatDateIntsToDateLong(year: Int, month: Int, day: Int): Long{
            // husk at checke om month starter ved 0
            var yearString: String = year.toString()
            var monthString: String = if (month.toString().length < 2) "0" + month else "" + month
            var dayString: String = if (day.toString().length < 2) "0" + day else "" + day

            return  (yearString+monthString+dayString).toLong()
        }
    }
}