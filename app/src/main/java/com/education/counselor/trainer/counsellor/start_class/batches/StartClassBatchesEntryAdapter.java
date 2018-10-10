package com.education.counselor.trainer.counsellor.start_class.batches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class StartClassBatchesEntryAdapter extends RecyclerView.Adapter<StartClassBatches> {
    private Context c;
    private ArrayList<StartClassBatchesEntryVo> details;

    StartClassBatchesEntryAdapter(Context c, ArrayList<StartClassBatchesEntryVo> details) {
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
    public StartClassBatches onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_start_class_batches_adapter, viewGroup, false);
        return new StartClassBatches(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StartClassBatches holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}