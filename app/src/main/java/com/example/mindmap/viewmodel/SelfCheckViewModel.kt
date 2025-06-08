package com.example.mindmap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindmap.model.SelfCheckRecord
import com.example.mindmap.repository.AppDatabase
import com.example.mindmap.repository.SelfCheckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SelfCheckViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SelfCheckRepository

    private val _allRecords = MutableStateFlow<List<SelfCheckRecord>>(emptyList())
    val allRecords: StateFlow<List<SelfCheckRecord>> = _allRecords

    private val _graphRecords = MutableStateFlow<List<SelfCheckRecord>>(emptyList())
    val graphRecords: StateFlow<List<SelfCheckRecord>> = _graphRecords

    init {
        val dao = AppDatabase.getDatabase(application).selfCheckDao()
        repository = SelfCheckRepository(dao)
        loadRecords()
    }

    fun saveRecord(record: SelfCheckRecord) {
        viewModelScope.launch {
            repository.saveRecord(record)

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cutoffDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -6) }.time.let {
                sdf.format(it)
            }

            repository.deleteOldRecords(cutoffDate)

            loadRecords()
        }
    }

    fun loadRecords() {
        viewModelScope.launch {
            _allRecords.value = repository.getAllRecords()
            _graphRecords.value = repository.getGraphRecords()
        }
    }
}
