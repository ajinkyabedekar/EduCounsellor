package com.education.counselor.trainer.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class NewsListEntryAdapter extends RecyclerView.Adapter<NewsList> {
    private Context c;
    private ArrayList<NewsListEntryVo> details;

    NewsListEntryAdapter(Context c, ArrayList<NewsListEntryVo> details) {
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
    public NewsList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_news_list_adapter, viewGroup, false);
        return new NewsList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsList holder, int i) {
        holder.sname.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}