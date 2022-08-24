package com.tbum.prayertv.Adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.tbum.prayertv.Activities.PlayVideoActivity
import com.tbum.prayertv.Activities.PreviewImageActivity
import com.tbum.prayertv.Models.MySlider
import com.tbum.prayertv.R
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ItemSliderGridBinding

/**
 * Created by FA on 08/19/2022.
 */

class BannerImagesAdapter(var context: Context, var arrayList: ArrayList<MySlider>) :


    RecyclerView.Adapter<BannerImagesAdapter.VH?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_slider_grid, parent, false)
        return VH(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: VH, position: Int) {


        holder.bind(arrayList[position])


    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemSliderGridBinding.bind(itemView)
        val sp = SharedPref(context)

        fun bind(obj: MySlider) {


            Glide.with(context).load(obj.uri).into(binding.image)

            binding.ivDelete.setOnClickListener {

                arrayList.removeAt(adapterPosition)
                sp.save("mySlider", Gson().toJson(arrayList))
                notifyItemRemoved(adapterPosition)
            }

            itemView.setOnClickListener {
                if (obj.type == "video") {
                    val intent = Intent(context, PlayVideoActivity::class.java)
                    intent.putExtra("video", obj.uri)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, PreviewImageActivity::class.java)
                    intent.putExtra("img", obj.uri)
                    context.startActivity(intent)
                }

            }


        }

    }
}
