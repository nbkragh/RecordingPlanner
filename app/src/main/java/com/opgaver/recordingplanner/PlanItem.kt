package com.opgaver.recordingplanner


import java.util.*

class PlanItem(
    var title : String,
    var category: String,
    var active: Boolean,
    var startDate: Date?,
    var endDate: Date?,
    var recordQuality: Int?,
    var autoIncreaseQuality: Boolean,
    var notifyDeletion: Boolean,
    var brief: String
){
    constructor(title : String, category: String): this(title, category, false, Date(), Date(), 1, true, true, "brief")

}