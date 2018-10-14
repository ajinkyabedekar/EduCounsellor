package com.education.counselor.trainer.employee.counsellor.internship.centers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.internship.list.InternshipListActivity;

import java.util.ArrayList;

public class InternshipCentersEntryAdapter extends RecyclerView.Adapter<InternshipCenters> {
    private Context c;
    private ArrayList<InternshipCentersEntryVo> details;

    InternshipCentersEntryAdapter(Context c, ArrayList<InternshipCentersEntryVo> details) {
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
    public InternshipCenters onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_internship_centers_adapter2, viewGroup, false);
        return new InternshipCenters(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final InternshipCenters holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, InternshipListActivity.class);
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