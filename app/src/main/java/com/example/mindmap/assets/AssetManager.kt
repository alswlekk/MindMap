package com.example.mindmap.assets

import android.content.Context
import com.example.mindmap.map.data.FacilityData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

fun loadJsonFromAssets(context: Context, fileName: String): String {
    val assetManager = context.assets
    val inputStream = assetManager.open(fileName)
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = inputStreamReader.buffered()
    return bufferedReader.use { it.readText() }
}

fun parseJsonToFacilityData(jsonString: String): List<FacilityData> {
    val gson = Gson()
    val facilityListType = object : TypeToken<List<FacilityData>>() {}.type
    return gson.fromJson(jsonString, facilityListType)
}