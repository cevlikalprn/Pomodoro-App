package com.cevlikalprn.pomodoro

import android.content.Context
import android.content.SharedPreferences

object LocalDataManager {

    private var preferences: SharedPreferences? = null

    fun getPreferences(context: Context): SharedPreferences{
            if(preferences == null){
                preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            }
            return preferences!!
    }

}