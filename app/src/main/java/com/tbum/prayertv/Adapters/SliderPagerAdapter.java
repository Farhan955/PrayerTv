package com.tbum.prayertv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.tbum.prayertv.Activities.PlayVideoActivity;
import com.tbum.prayertv.Activities.PreviewImageActivity;
import com.tbum.prayertv.Models.MySlider;
import com.tbum.prayertv.R;

import java.util.ArrayList;

/**
 * Created by FA on 8/21/2022.
 */
public class SliderPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<MySlider> arrayList;

    public SliderPagerAdapter(Context context, ArrayList<MySlider> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.item_slider, null);

        MySlider obj = arrayList.get(position);
        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);
        ImageView ivVideo = sliderLayout.findViewById(R.id.slider_background);
        TextView caption_title = sliderLayout.findViewById(R.id.my_caption_title);

//        featured_image.setImageResource(arrayList.get(position).getUrl());
        Glide.with(context).load(obj.getUri()).into(featured_image);

        if (obj.getType().equals("video")) {
            ivVideo.setVisibility(View.VISIBLE);
        } else {
            ivVideo.setVisibility(View.GONE);

        }

        featured_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (obj.getType().equals("video")) {

                    Intent intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra("video", obj.getUri());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PreviewImageActivity.class);
                    intent.putExtra("img", obj.getUri());
                    context.startActivity(intent);
                }

            }
        });
        container.addView(sliderLayout);


        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
