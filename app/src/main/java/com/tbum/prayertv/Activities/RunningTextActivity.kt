package com.tbum.prayertv.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityRunningTextBinding

class RunningTextActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityRunningTextBinding

    val sp = SharedPref(context)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunningTextBinding.inflate(layoutInflater)
        setContentView(binding.root)


        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)

        binding.etRunningText.setText(sp["running_text"])
        binding.seekBar.progress = sp.getInt("speed", 0)


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                sp.saveInt("speed", p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    fun tvSave(view: View) {
        val text = binding.etRunningText.text.toString().trim()

        if (text.isEmpty()) {
            binding.tilTitle.error = "Please give text"
            return
        }

        sp.save("running_text", text)
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

        onBackPressed()
    }
}