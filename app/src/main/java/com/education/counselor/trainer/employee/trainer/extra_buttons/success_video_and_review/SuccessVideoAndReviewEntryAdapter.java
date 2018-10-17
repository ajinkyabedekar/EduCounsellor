package com.education.counselor.trainer.employee.trainer.extra_buttons.success_video_and_review;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class SuccessVideoAndReviewEntryAdapter extends RecyclerView.Adapter<SuccessVideoAndReview> {
    private Context c;
    private ArrayList<SuccessVideoAndReviewEntryVo> details;

    SuccessVideoAndReviewEntryAdapter(Context c, ArrayList<SuccessVideoAndReviewEntryVo> details) {
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
    public SuccessVideoAndReview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_success_video_and_review_adapter, viewGroup, false);
        return new SuccessVideoAndReview(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SuccessVideoAndReview holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, SuccessVideoAndReviewDetailsActivity.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getName());
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}