package com.education.counselor.trainer.student.query.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
public class QueryListEntryAdapter extends RecyclerView.Adapter<QueryList> {
    private Context c;
    private ArrayList<QueryListEntryVo> details;
    QueryListEntryAdapter(Context c, ArrayList<QueryListEntryVo> details) {
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
    public QueryList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_query_list_adapter, viewGroup, false);
        return new QueryList(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final QueryList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.p_name.setText(details.get(i).getPhone());
        holder.v.setEnabled(false);
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}