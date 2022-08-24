package com.tbum.prayertv.Activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tbum.prayertv.Models.MyDate
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityAddDateBinding
import java.text.SimpleDateFormat
import java.util.*


class AddDateActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityAddDateBinding
    val sp = SharedPref(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)
    }

    fun tvSave(view: View) {
        var date = binding.tvDate.text.toString()
        val info = binding.etInfo.text.toString().trim()

        if (date.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show()
            return
        }

        if (info.isEmpty()) {
            Toast.makeText(this, "Please write some information", Toast.LENGTH_SHORT).show()
            return
        }


        val obj = MyDate(date, info)

        var list = sp.getList("myDates")
        list.add(obj)


        sp.save("myDates", Gson().toJson(list))


        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        onBackPressed()


    }

    fun dateClick(view: View) {setDateTimeField()}


    private fun setDateTimeField() {

        val df = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)

        val calendar: Calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
//                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0)
//                dateEditText.setText(df.format(dateSelected.getTime()))

                binding.tvDate.text=""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}