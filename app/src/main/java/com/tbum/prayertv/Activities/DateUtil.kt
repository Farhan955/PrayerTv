package com.tbum.prayertv.Activities

import android.util.Log
import java.util.*

/**
 * Created by FA on 8/19/2022.
 */


fun isGreater(time: String, add: Int = 0): Boolean {
    val currentTime = Calendar.getInstance()
    val timeToMatch = Calendar.getInstance()

//    currentTime[Calendar.HOUR_OF_DAY]=20
//    currentTime[Calendar.MINUTE]=0

    timeToMatch[Calendar.HOUR_OF_DAY] = getHour(time).toInt() + add
    timeToMatch[Calendar.MINUTE] = getMinute(time).toInt() + add

    var flag = false

    when {
        currentTime == timeToMatch ->
            flag = true
        currentTime < timeToMatch ->
            flag = false
        currentTime > timeToMatch
        -> flag = true

    }

    Log.d("_farhanx", (getHour(time).toInt() + add).toString() + " " + flag)

    return flag
}


fun getHour(time: String): String {

    val s = time.split(":")
    return s[0].trim()
}


fun getMinute(time: String): String {

    val s = time.split(":")
    return s[1].trim()
}


fun getIndex(
    fajar: String,
    sunrise: String,
    ishraq: String,
    zohar: String,
    asar: String,
    maghrib: String,
    isha: String
): Int {


    var i = 0
    if (isGreater(fajar)) {
        i++
    }
    if (isGreater(sunrise)) {
        i++
    }
    if (isGreater(ishraq)) {
        i++
    }
    if (isGreater(zohar, 12)) {
        i++
    }
    if (isGreater(asar, 12)) {
        i++
    }
    if (isGreater(maghrib, 12)) {
        i++
    }
    if (isGreater(isha, 12)) {
        i++
    }

    return i
}