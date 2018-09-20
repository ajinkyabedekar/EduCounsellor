package com.education.counselor.trainer.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class CounsellorCentersEntryAdapter extends RecyclerView.Adapter<CounsellorCenters> {
    private Context c;
    private ArrayList<CounsellorCentersEntryVo> details;

    CounsellorCentersEntryAdapter(Context c, ArrayList<CounsellorCentersEntryVo> details) {
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
    public CounsellorCenters onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_counsellor_centers_adapter, viewGroup, false);

        return new CounsellorCenters(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CounsellorCenters holder, int i) {
        holder.sname.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}