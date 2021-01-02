package com.opgaver.recordingplanner.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.opgaver.recordingplanner.PlanItem
import com.opgaver.recordingplanner.R
import java.sql.SQLException


class DatabaseHelper(
    context :Context, databasename: String = "planitemsDB", databaseversion: Int = 1
): OrmLiteSqliteOpenHelper(
    context, databasename, null, databaseversion ) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, PlanItem::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }
}