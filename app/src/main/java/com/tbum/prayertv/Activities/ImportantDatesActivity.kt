package com.tbum.prayertv.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tbum.prayertv.Adapters.ImportantDatesAdapter
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityImportantDatesBinding

class ImportantDatesActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityImportantDatesBinding
    val sp = SharedPref(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportantDatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)

    }

    override fun onResume() {
        super.onResume()


        binding.recyclerView.adapter=ImportantDatesAdapter(context,sp.getList("myDates"))

    }


    fun ivAdd(view: View) {
        startActivity(Intent(context, AddDateActivity::class.java))
    }

}