package com.education.counselor.trainer.authority.college.placement_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class PlacementListEntryAdapter extends RecyclerView.Adapter<PlacementList> {
    private Context c;
    private ArrayList<PlacementListEntryVo> details;

    PlacementListEntryAdapter(Context c, ArrayList<PlacementListEntryVo> details) {
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
    public PlacementList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_placement_list_adapter2, viewGroup, false);
        return new PlacementList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlacementList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}