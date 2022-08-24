package com.tbum.prayertv.Utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tbum.prayertv.Models.MyDate
import com.tbum.prayertv.Models.MySlider
import com.tbum.prayertv.R

class SharedPref(var context: Context) {
    fun save(key: String?, value: String?) {
        val editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.commit()
    }

    operator fun get(key: String?): String? {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    fun saveImg(key: String?, image: ByteArray?) {
        val img_str = Base64.encodeToString(image, Base64.DEFAULT)
        val sh = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sh.edit()
        editor.putString(key, img_str)
        editor.commit()
    }

    fun getImg(key: String?): ByteArray {
        val sharedPreferences =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val s = sharedPreferences.getString(key, "")
        // String text = new String(data, StandardCharsets.UTF_8);
        return Base64.decode(s, Base64.DEFAULT)
    }

    fun clear() {
        val settings = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }

    fun saveBoolean(key: String?, value: Boolean) {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        val prefs =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return prefs.getBoolean(key, defValue)
    }

    fun saveInt(key: String?, value: Int) {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?, default: Int? = -1): Int {
        val prefs =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return prefs.getInt(key, default!!)
    }

    fun getColor(key: String?): Int {
        val prefs =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return prefs.getInt(key, -1)
    }



    fun getList(key: String?): ArrayList<MyDate> {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<MyDate?>?>() {}.type


        return try {
            gson.fromJson(json, type)

        } catch (e: Exception) {
            ArrayList()
        }
    }

    fun getSliderList(key: String?): ArrayList<MySlider> {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<MySlider?>?>() {}.type


        return try {
            gson.fromJson(json, type)

        } catch (e: Exception) {
            ArrayList()
        }
    }


    fun isLoggedIn(): Boolean {
        return getBoolean("isLoggedIn", false)
    }

    fun isLoggedIn(isLoggedIn: Boolean) {
        saveBoolean("isLoggedIn", isLoggedIn)
    }


    /*fun saveProfile(profile: Profile) {
        save("profile", Gson().toJson(profile))
    }

    fun getProfile(): Profile {
        val data = get("profile")
        return Gson().fromJson(data, Profile::class.java)
    }*/


    fun registerPref(
        context: Context,
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterPref(
        context: Context,
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun saveInPref(context: Context, key: String?, value: String?) {
        val json = Gson().toJson(value)
        save(key, json)
    }

    fun loadFromPref(context: Context, key: String?): String? {
        return get(key)
    }
}