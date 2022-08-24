package com.tbum.prayertv.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityReminderBinding

class ReminderActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityReminderBinding
    val sp = SharedPref(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)

        binding.swFajar.isChecked = sp.getBoolean("isFajar", false)
        binding.swZohar.isChecked = sp.getBoolean("isZohar", false)
        binding.swAsar.isChecked = sp.getBoolean("isAsar", false)
        binding.swMaghrib.isChecked = sp.getBoolean("isMaghrib", false)
        binding.swIsha.isChecked = sp.getBoolean("isIsha", false)

        binding.etFajar.setText(sp.getInt("fajar", 0).toString())
        binding.etZohar.setText(sp.getInt("zohar", 0).toString())
        binding.etAsar.setText(sp.getInt("asar", 0).toString())
        binding.etMaghrib.setText(sp.getInt("maghrib", 0).toString())
        binding.etIsha.setText(sp.getInt("isha", 0).toString())
    }

    fun tvSave(view: View) {
        sp.saveBoolean("isFajar", binding.swFajar.isChecked)
        sp.saveBoolean("isZohar", binding.swZohar.isChecked)
        sp.saveBoolean("isAsar", binding.swAsar.isChecked)
        sp.saveBoolean("isMaghrib", binding.swMaghrib.isChecked)
        sp.saveBoolean("isIsha", binding.swIsha.isChecked)

        val fajar = binding.etFajar.text.toString().trim()
        val zohar = binding.etZohar.text.toString().trim()
        val asar = binding.etAsar.text.toString().trim()
        val maghrib = binding.etMaghrib.text.toString().trim()
        val isha = binding.etIsha.text.toString().trim()

        if (fajar.isNotEmpty()) {
            sp.saveInt("fajar", fajar.toInt())
        }
        if (zohar.isNotEmpty()) {
            sp.saveInt("zohar", zohar.toInt())
        }
        if (asar.isNotEmpty()) {
            sp.saveInt("asar", asar.toInt())
        }
        if (maghrib.isNotEmpty()) {
            sp.saveInt("maghrib", maghrib.toInt())
        }
        if (isha.isNotEmpty()) {
            sp.saveInt("isha", isha.toInt())
        }

        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }
}