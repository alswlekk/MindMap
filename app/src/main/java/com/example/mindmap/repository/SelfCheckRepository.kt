package com.example.mindmap.repository

import com.example.mindmap.model.SelfCheckRecord

class SelfCheckRepository(private val dao: SelfCheckDao) {
    suspend fun saveRecord(record: SelfCheckRecord) {
        dao.insert(record)
    }

    suspend fun getAllRecords(): List<SelfCheckRecord> {
        return dao.getAllRecords()
    }

    suspend fun getGraphRecords(): List<SelfCheckRecord> {
        return dao.getGraphRecords()
    }

    suspend fun deleteOldRecords(cutoffDate: String) {
        dao.deleteOldRecords(cutoffDate)
    }
}
