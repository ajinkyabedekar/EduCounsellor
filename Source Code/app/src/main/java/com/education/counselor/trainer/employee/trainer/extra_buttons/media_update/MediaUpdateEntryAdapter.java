package com.education.counselor.trainer.employee.trainer.extra_buttons.media_update;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
public class MediaUpdateEntryAdapter extends RecyclerView.Adapter<MediaUpdate> {
    private Context c;
    private ArrayList<MediaUpdateEntryVo> details;
    MediaUpdateEntryAdapter(Context c, ArrayList<MediaUpdateEntryVo> details) {
        setHasStableIds(true);
        this.c = c;
        this.details = details;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @NonNull
    @Override
    public MediaUpdate onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_media_update_adapter, viewGroup, false);
        return new MediaUpdate(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final MediaUpdate holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, MediaUpdateDetailsActivity.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getName());
                c.startActivity(in);
            }
        });
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}