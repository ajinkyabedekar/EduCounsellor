package com.education.counselor.trainer.employee.trainer.start_class.batches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        holder.time.setText(details.get(i).getTime());
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Class Started", Toast.LENGTH_SHORT).show();
                holder.start.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}