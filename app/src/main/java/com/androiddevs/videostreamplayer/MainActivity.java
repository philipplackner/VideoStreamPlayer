package com.androiddevs.videostreamplayer;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Track> tracks = new ArrayList<>(Arrays.asList(
            // I don't have that many example URLs, but of course you can just insert several own here
            new Track(
                    "Funny Bunny Video",
                    "https://www.sample-videos.com/" +
                            "video123/mp4/720/big_buck_bunny_720p_20mb.mp4"
            )
    ));

    PlayerView playerView;
    RecyclerView rvTracks;
    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
        rvTracks = findViewById(R.id.rvTracks);

        rvTracks.setLayoutManager(new LinearLayoutManager(this));
        TrackAdapter adapter = new TrackAdapter(tracks, new OnTrackChangedListener() {
            @Override
            public void onTrackChanged(Track newTrack) {
                changeTrack(newTrack);
            }
        });
        rvTracks.setAdapter(adapter);

        setupExoPlayer();
    }

    private void changeTrack(Track newTrack) {
        MediaSource newSource = new ProgressiveMediaSource
                .Factory(dataSourceFactory).createMediaSource(Uri.parse(newTrack.getUrl()));
        player.prepare(newSource);
    }

    DataSource.Factory dataSourceFactory;

    private void setupExoPlayer() {
        String url = tracks.get(1).getUrl();

        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "yourApplicationName"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(url));
        // Prepare the player with the source.
        player.prepare(videoSource);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        player = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupExoPlayer();
    }
}
