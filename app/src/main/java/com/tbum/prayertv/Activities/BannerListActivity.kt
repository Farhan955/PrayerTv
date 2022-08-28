package com.tbum.prayertv.Activities

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tbum.prayertv.Adapters.BannerImagesAdapter
import com.tbum.prayertv.Models.MySlider
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityBannerListBinding

class BannerListActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityBannerListBinding
    val sp = SharedPref(context)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBannerListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        _init_()
    }

    private fun _init_() {

        Functions.setToolbar(this)
        setAdapter()
    }


    fun ivAdd(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))

        startActivityForResult(intent, 123)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var obj = MySlider()

        if (resultCode === RESULT_OK) {
            val uri = data!!.data!!
            obj = if (uri.toString().contains("image")) {
                MySlider(uri.toString(), "image", "asfasfa")

            } else if (uri.toString().contains("video")) {
                MySlider(uri.toString(), "video", "asfasfa")
            } else {
                Toast.makeText(this, "Invalid file", Toast.LENGTH_SHORT).show()
                return
            }

            var list = sp.getSliderList("mySlider")
            list.add(obj)
            sp.save("mySlider", Gson().toJson(list))


            setAdapter()
        }


    }

    private fun setAdapter() {


        binding.recyclerView.adapter = BannerImagesAdapter(context, sp.getSliderList("mySlider"))


    }


}