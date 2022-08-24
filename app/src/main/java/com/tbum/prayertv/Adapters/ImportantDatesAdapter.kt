package com.tbum.prayertv.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tbum.prayertv.Models.MyDate
import com.tbum.prayertv.R
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ItemMyDateBinding

/**
 * Created by FA on 08/19/2022.
 */

class ImportantDatesAdapter(var context: Context, var arrayList: ArrayList<MyDate>) :


    RecyclerView.Adapter<ImportantDatesAdapter.VH?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_date, parent, false)
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

        val binding = ItemMyDateBinding.bind(itemView)
        val sp=SharedPref(context)

        fun bind(obj: MyDate) {


            binding.tvDate.text = obj.date
            binding.tvInfo.text = obj.info

            binding.ivDelete.setOnClickListener {

                arrayList.removeAt(adapterPosition)
                sp.save("myDates", Gson().toJson(arrayList))
                notifyItemRemoved(adapterPosition)
            }


        }

    }
}
