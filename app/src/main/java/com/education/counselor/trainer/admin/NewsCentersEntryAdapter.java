package com.education.counselor.trainer.admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class NewsCentersEntryAdapter extends RecyclerView.Adapter<NewsCenters> {
    private Context c;
    private ArrayList<NewsCentersEntryVo> details;

    NewsCentersEntryAdapter(Context c, ArrayList<NewsCentersEntryVo> details) {
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
    public NewsCenters onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_news_centers_adapter, viewGroup, false);
        return new NewsCenters(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsCenters holder, int i) {
        holder.sname.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,NewsListActivity.class);
                in.putExtra("name",details.get(holder.getAdapterPosition()).getName());
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}