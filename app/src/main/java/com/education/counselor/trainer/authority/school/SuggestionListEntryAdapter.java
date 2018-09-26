package com.education.counselor.trainer.authority.school;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class SuggestionListEntryAdapter extends RecyclerView.Adapter<SuggestionList> {
    private Context c;
    private ArrayList<SuggestionListEntryVo> details;

    SuggestionListEntryAdapter(Context c, ArrayList<SuggestionListEntryVo> details) {
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
    public SuggestionList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_suggestion_list_adapter, viewGroup, false);
        return new SuggestionList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SuggestionList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}