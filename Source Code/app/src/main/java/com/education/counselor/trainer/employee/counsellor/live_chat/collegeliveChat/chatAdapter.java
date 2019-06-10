package com.education.counselor.trainer.employee.counsellor.live_chat.collegeliveChat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
public class chatAdapter extends RecyclerView.Adapter<chatList> {
    private Context c;
    private ArrayList<chatMessages> details;
    chatAdapter(Context c, ArrayList<chatMessages> details) {
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
    public chatList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.live_chat_layout, viewGroup, false);
        return new chatList(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final chatList holder, int i) {
        holder.name.setText(details.get(i).getName());
        holder.date.setText(details.get(i).getDate());
        holder.message.setText(details.get(i).getMessage());
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}