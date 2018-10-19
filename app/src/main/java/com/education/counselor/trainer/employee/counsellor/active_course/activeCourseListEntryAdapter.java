package com.education.counselor.trainer.employee.counsellor.active_course;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.active_course.batch.AIBatchListActivity;

import java.util.ArrayList;

public class activeCourseListEntryAdapter extends RecyclerView.Adapter<activeCourseList> {
    private Context c;
    private ArrayList<activeCourseListEntryVo> details;

    activeCourseListEntryAdapter(Context c, ArrayList<activeCourseListEntryVo> details) {
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
    public activeCourseList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_internship_list_adapter4, viewGroup, false);
        return new activeCourseList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final activeCourseList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, AIBatchListActivity.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getPhone());
                in.putExtra("cname", details.get(holder.getAdapterPosition()).getName());
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}