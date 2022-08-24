package com.tbum.prayertv.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.tbum.prayertv.R
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityColorPickerBinding

class ColorPickerActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityColorPickerBinding
    val sp = SharedPref(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityColorPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()

    }

    private fun _init_() {

        Functions.setToolbar(this)
        setColors()

    }

    private fun showColorPicker(key: String) {

        val primaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
        var mColor = sp.getInt("key")

        ColorPickerDialog
            .Builder(this) // Pass Activity Instance
            .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
            .setDefaultColor(R.color.black) // Pass Default Color
            .setColorListener { color, _ ->
                mColor = color
//                colorPickerView.setColor(color)
//                setButtonBackground(colorPickerBtn, color)

                sp.saveInt(key, mColor)
                setColors()
            }
            .setDismissListener {
                Log.d("ColorPickerDialog", "Handle dismiss event")
            }
            .show()
    }

    private fun setColors() {

        binding.viewMosqueName.setBackgroundColor(sp.getColor("mName"))
        binding.viewTimeClock.setBackgroundColor(sp.getColor("timeClock"))
        binding.viewDate.setBackgroundColor(sp.getColor("date"))
        binding.viewHText.setBackgroundColor(sp.getColor("hText"))
        binding.viewVText.setBackgroundColor(sp.getColor("vText"))


    }

    fun mName(view: View) {
        showColorPicker("mName")
    }

    fun timeClock(view: View) {
        showColorPicker("timeClock")
    }

    fun date(view: View) {
        showColorPicker("date")
    }

    fun hText(view: View) {
        showColorPicker("hText")
    }

    fun vText(view: View) {
        showColorPicker("vText")

    }
}