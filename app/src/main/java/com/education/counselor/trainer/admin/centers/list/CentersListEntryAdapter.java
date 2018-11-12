package com.education.counselor.trainer.admin.centers.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.centers.CentersDetailsActivity;

import java.util.ArrayList;
public class CentersListEntryAdapter extends RecyclerView.Adapter<CentersList> {
    private Context c;
    private ArrayList<CentersListEntryVo> details;
    CentersListEntryAdapter(Context c, ArrayList<CentersListEntryVo> details) {
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
    public CentersList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_centers_list_adapter, viewGroup, false);
        return new CentersList(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final CentersList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, CentersDetailsActivity.class);
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