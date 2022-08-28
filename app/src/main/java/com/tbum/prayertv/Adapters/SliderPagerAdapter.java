package com.tbum.prayertv.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.tbum.prayertv.Models.MySlider;
import com.tbum.prayertv.R;
import com.tbum.prayertv.Utils.ChangeItem;
import com.tbum.prayertv.Utils.Functions;

import java.util.ArrayList;

/**
 * Created by FA on 8/21/2022.
 */

public class SliderPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<MySlider> arrayList;
    VideoView videoView;
    ChangeItem changeItem;

    public SliderPagerAdapter(Context context, ArrayList<MySlider> arrayList, ChangeItem changeItem) {

        this.context = context;
        this.arrayList = arrayList;
        this.changeItem = changeItem;
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
        TextView caption_title = sliderLayout.findViewById(R.id.my_caption_title);
        ConstraintLayout layout_image = sliderLayout.findViewById(R.id.layout_image);
        ConstraintLayout layout_video = sliderLayout.findViewById(R.id.layout_video);

        videoView = sliderLayout.findViewById(R.id.video_view);

        Glide.with(context).load(obj.getUri()).into(featured_image);

        if (obj.getType().equals("video")) {
            layout_image.setVisibility(View.GONE);
            layout_video.setVisibility(View.VISIBLE);
            initUi(obj.getUri(),position);
        } else {
            layout_image.setVisibility(View.VISIBLE);
            layout_video.setVisibility(View.GONE);
        }


        /*        featured_image.setOnClickListener(new View.OnClickListener() {
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
        });*/



        container.addView(sliderLayout);

        return sliderLayout;
    }

    private void initUi(String uri, int position) {

        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();

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
