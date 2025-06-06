package com.example.mindmap.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mindmap.model.SelfCheckRecord

@Dao
interface SelfCheckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: SelfCheckRecord)

    @Query("""
        SELECT * FROM self_check_records 
        ORDER BY id DESC
    """)
    suspend fun getAllRecords(): List<SelfCheckRecord>

    @Query("""
        SELECT * FROM self_check_records
        WHERE id IN (
            SELECT MAX(id) FROM self_check_records
            GROUP BY date
        )
        ORDER BY date ASC
    """)
    suspend fun getGraphRecords(): List<SelfCheckRecord>

    @Query("DELETE FROM self_check_records WHERE date < :cutoffDate")
    suspend fun deleteOldRecords(cutoffDate: String)
}