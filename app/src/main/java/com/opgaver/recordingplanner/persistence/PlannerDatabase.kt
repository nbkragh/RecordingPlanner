package com.opgaver.recordingplanner.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.opgaver.recordingplanner.PlanItem

@Database(entities = arrayOf(PlanItem::class), version = 1)
abstract class PlannerDatabase : RoomDatabase() {
    abstract fun planitemDAO(): PlanItemDAO


    companion object{
        private var  instance: PlannerDatabase? = null

        fun getInstance(context: Context): PlannerDatabase{
            return instance?:Room.databaseBuilder(context, PlannerDatabase::class.java, "plannerDB").build()

        }




    }
}