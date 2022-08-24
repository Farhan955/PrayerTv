package com.tbum.prayertv.Activities;


import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.tbum.prayertv.R;
import com.tbum.prayertv.Utils.Functions;

@SuppressWarnings("deprecation")
public class PlayVideoActivity extends AppCompatActivity {

    Context context;
    PlayerView playerView;
    SimpleExoPlayer exoPlayer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        initUi();
    }

    private void initUi() {
        context = PlayVideoActivity.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Functions.fullScreenWithNav(context);
        }
        Functions.changeNavigationBarColor(context, R.color.black);
        String videoUrl = getIntent().getStringExtra("video");
        viewsInit();

        progressBar.setVisibility(View.VISIBLE);
        DefaultLoadControl control = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                        100 * 1000,
                        200 * 1000,
                        1000,
                        5000)
                .createDefaultLoadControl();
        exoPlayer = new SimpleExoPlayer.Builder(context)
                .setLoadControl(control)
                .build();


        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(context, getString(R.string.app_name));
        MediaSource source = new ProgressiveMediaSource.Factory(factory).createMediaSource(Uri.parse(videoUrl));
        exoPlayer.prepare(source, false, false);

        playWhenReady();
    }

    private void viewsInit() {
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void playWhenReady() {
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.setVolume(0);
        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(@NonNull Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(@NonNull TrackGroupArray trackGroups, @NonNull TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(@NonNull ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(@NonNull PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }


        });

        playerView.setPlayer(exoPlayer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.pause();
//        Functions.hideKeyboard(context);
    }
}