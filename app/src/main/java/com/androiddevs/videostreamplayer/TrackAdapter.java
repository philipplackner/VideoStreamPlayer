package com.androiddevs.videostreamplayer;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    List<Track> tracks;
    ArrayList<TrackViewHolder> viewHolders = new ArrayList<>();
    OnTrackChangedListener trackChangedListener;

    int curPlaying = 0;

    public TrackAdapter(List<Track> tracks, OnTrackChangedListener trackChangedListener) {
        this.tracks = tracks;
        this.trackChangedListener = trackChangedListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new TrackViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final TrackViewHolder holder, final int position) {
        if(position == curPlaying) {
            holder.tvTitle.setTextColor(holder.itemView.getContext().getColor(R.color.colorAccent));
        }
        viewHolders.add(holder);
        holder.tvTitle.setText(tracks.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notify MainActivity to play the next track
                trackChangedListener.onTrackChanged(tracks.get(position));
                holder.tvTitle.setTextColor(holder.itemView.getContext().getColor(R.color.colorAccent));
                viewHolders.get(curPlaying).tvTitle.setTextColor(Color.WHITE);
                curPlaying = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
