package com.tbum.prayertv.Activities


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView
import com.tbum.prayertv.R

class PreviewImageActivity : AppCompatActivity() {


    lateinit var zoomageView: ZoomageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_image)


        zoomageView = findViewById(R.id.myZoomageView)
        val img = intent.getStringExtra("img")

        Glide.with(this).load(Uri.parse(img)).into(zoomageView)

    }

    fun back(view: View) {
        onBackPressed()
    }
}