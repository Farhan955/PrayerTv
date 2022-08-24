package com.tbum.prayertv.Activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityInfoBinding

    val sp = SharedPref(context)

    var location = ""
    var call = ""
    var prayer = ""
    var friday = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Functions.setToolbar(this)

        setData()
    }

    private fun setData() {

        binding.etLocation.setText(sp["location"])
        binding.etTextWaiting.setText(sp["text_call"])
        binding.etTextPrayer.setText(sp["text_prayer"])
        binding.etTextFriday.setText(sp["text_friday"])

    }


    fun tvSave(view: View) {


        location = binding.etLocation.text.toString().trim()
        call = binding.etTextWaiting.text.toString().trim()
        prayer = binding.etTextPrayer.text.toString().trim()
        friday = binding.etTextFriday.text.toString().trim()

        if (location.isEmpty()) {
            binding.etLocation.error = "required"
            return
        }
        if (call.isEmpty()) {
            binding.etTextWaiting.error = "required"
            return
        }
        if (prayer.isEmpty()) {
            binding.etTextPrayer.error = "required"
            return
        }
        if (friday.isEmpty()) {
            binding.etTextFriday.error = "required"
            return
        }

        sp.save("location", location)
        sp.save("text_call", call)
        sp.save("text_prayer", prayer)
        sp.save("text_friday", friday)

        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()

        onBackPressed()
    }
}