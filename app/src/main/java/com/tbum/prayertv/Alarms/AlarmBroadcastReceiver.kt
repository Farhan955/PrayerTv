package com.tbum.prayertv.Alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tbum.prayertv.Activities.getHour
import com.tbum.prayertv.Activities.getIndex
import com.tbum.prayertv.Activities.getMinute
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import java.util.logging.Handler

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var fajar = ""
    var zohar = ""
    var asar = ""
    var maghrib = ""
    var isha = ""
    var ishraq = ""
    var sunrise = ""
    var date = ""

    var myAlarmManager: MyAlarmManager? = null

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "service is working", Toast.LENGTH_SHORT).show()
        Functions.showNotificationAPI26(context)

        getPrayerTime(context)

        val sp = SharedPref(context)
        sp.saveInPref(context, "invoke", System.currentTimeMillis().toString())

    }

    private fun getPrayerTime(ctx: Context) {
        val json: String?
        try {
            val stream: InputStream = ctx.assets.open("PutrajayaZone.json")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val charset: Charset = Charsets.UTF_8
            json = String(buffer, charset)
            var obj = JSONObject(json)
            val jsonArray: JSONArray = obj.getJSONArray("PutrajayaZone")

            for (i in 0 until jsonArray.length()) {

                obj = jsonArray.getJSONObject(i)
                val date = obj.getString("Date")

                if (date.equals(Functions.getCurentDate())) {
                    break
                }

            }


            date =
                obj.getString("Day") + ", " + obj.getString("Date") + " / " + obj.getString("iDate")


            fajar = obj.getString("Fajar")
            zohar = obj.getString("Zohar")
            asar = obj.getString("Asar")
            maghrib = obj.getString("Maghrib")
            isha = obj.getString("Isha")
            ishraq = obj.getString("Ishrak")
            sunrise = obj.getString("Sun Rise")

            setNextAlarm(ctx)
        } catch (ex: IOException) {
        }

    }

    private fun setNextAlarm(ctx: Context) {
        myAlarmManager = MyAlarmManager(ctx)

        val currentTime = Calendar.getInstance()

        var s20 = 20000

        when (getIndex(fajar, sunrise, ishraq, zohar, asar, maghrib, isha)) {
            1 -> {
                //fajar
                currentTime[Calendar.HOUR_OF_DAY] = getHour(zohar).toInt() + 12
                currentTime[Calendar.MINUTE] = getMinute(zohar).toInt()
                myAlarmManager!!.setAlarm(currentTime.timeInMillis - s20)
            }
            4 -> {
                currentTime[Calendar.HOUR_OF_DAY] = getHour(asar).toInt() + 12
                currentTime[Calendar.MINUTE] = getMinute(asar).toInt()
                myAlarmManager!!.setAlarm(currentTime.timeInMillis - s20)
            }

            5 -> {
                currentTime[Calendar.HOUR_OF_DAY] = getHour(maghrib).toInt() + 12
                currentTime[Calendar.MINUTE] = getMinute(maghrib).toInt()
                myAlarmManager!!.setAlarm(currentTime.timeInMillis - s20)
            }

            6 -> {

                currentTime[Calendar.HOUR_OF_DAY] = getHour(isha).toInt() + 12
                currentTime[Calendar.MINUTE] = getMinute(isha).toInt()
                myAlarmManager!!.setAlarm(currentTime.timeInMillis - s20)
            }

            7 -> {

                //todo get next day time for fajar
                currentTime.add(Calendar.DAY_OF_YEAR, 1)
                currentTime[Calendar.HOUR_OF_DAY] = getHour(fajar).toInt()
                currentTime[Calendar.MINUTE] = getMinute(fajar).toInt()
                myAlarmManager!!.setAlarm(currentTime.timeInMillis - s20)
            }
        }

    }

}