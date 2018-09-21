package com.education.counselor.trainer.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class EmployeesListEntryAdapter extends RecyclerView.Adapter<EmployeesList> {
    private Context c;
    private ArrayList<EmployeesListEntryVo> details;

    EmployeesListEntryAdapter(Context c, ArrayList<EmployeesListEntryVo> details) {
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
    public EmployeesList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_employees_list_adapter, viewGroup, false);
        return new EmployeesList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesList holder, int i) {
        holder.sname.setText(details.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}