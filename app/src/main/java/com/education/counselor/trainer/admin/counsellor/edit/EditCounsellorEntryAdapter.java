package com.education.counselor.trainer.admin.counsellor.edit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
public class EditCounsellorEntryAdapter extends RecyclerView.Adapter<EditCounsellor> {
    private Context c;
    private ArrayList<EditCounsellorEntryVo> details;
    EditCounsellorEntryAdapter(Context c, ArrayList<EditCounsellorEntryVo> details) {
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
    public EditCounsellor onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_edit_counsellor_adapter, viewGroup, false);
        return new EditCounsellor(v);
    }
    @Override
    public void onBindViewHolder(@NonNull EditCounsellor holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.p_name.setText(details.get(i).getPhone());
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}