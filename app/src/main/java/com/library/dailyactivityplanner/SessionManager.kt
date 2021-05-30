package com.library.dailyactivityplanner

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SessionManager {
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences

    private val PREFERENCESNAME by lazy { "Daily Activity tracker" }

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCESNAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
    fun getActivities(): ArrayList<ActivityModel> {
        val json = sharedPreferences.getString("activity_list", "")
        return Gson().fromJson(json, object : TypeToken<List<ActivityModel>>() {}.type)
            ?: ArrayList()
    }
    fun saveActivity(listing: ArrayList<ActivityModel>) {
        editor.putString("activity_list", Gson().toJson(listing))
        editor.commit()
    }


}
