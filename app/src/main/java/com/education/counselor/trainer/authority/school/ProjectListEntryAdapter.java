package com.education.counselor.trainer.authority.school;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class ProjectListEntryAdapter extends RecyclerView.Adapter<ProjectList> {
    private Context c;
    private ArrayList<ProjectListEntryVo> details;

    ProjectListEntryAdapter(Context c, ArrayList<ProjectListEntryVo> details) {
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
    public ProjectList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_project_list_adapter2, viewGroup, false);
        return new ProjectList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectList holder, int i) {
        holder.sname.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}