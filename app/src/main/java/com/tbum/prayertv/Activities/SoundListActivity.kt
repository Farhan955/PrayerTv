package com.tbum.prayertv.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.databinding.ActivitySoundListBinding

class SoundListActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivitySoundListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)
    }

    fun tvSave(view: View) {

    }
}