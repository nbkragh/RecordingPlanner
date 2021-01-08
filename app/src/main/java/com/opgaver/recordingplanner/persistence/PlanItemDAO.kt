package com.opgaver.recordingplanner.persistence

import androidx.room.*
import com.opgaver.recordingplanner.PlanItem

@Dao
interface PlanItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planitems: List<PlanItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planitem: PlanItem)

    @Query("SELECT * FROM planitems")
    suspend fun getAll(): List<PlanItem>

    @Query("SELECT * FROM planitems WHERE pid =(:id)")
    suspend fun getById(id: Int): PlanItem

    @Update
    suspend fun updateUsers(vararg planitems: PlanItem)

    @Delete
    suspend fun delete(planitem: PlanItem)

    @Query("DELETE FROM planitems")
    fun emptyTable()
}