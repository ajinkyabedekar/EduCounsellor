package com.education.counselor.trainer.admin.live_chat.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.live_chat.collegeliveChat.collegeAdminChat;
import com.education.counselor.trainer.admin.live_chat.liveChatCounsellor.counsellorLiveChat;
import com.education.counselor.trainer.admin.live_chat.liveChatTrainer.trainerLiveChat;
import com.education.counselor.trainer.admin.live_chat.schoolLiveChat.schoolAdminChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
public class ListEntryAdapter extends RecyclerView.Adapter<List> {
    private String senderKey, user, email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private Context c;
    private ArrayList<ListEntryVo> details;
    ListEntryAdapter(Context c, ArrayList<ListEntryVo> details) {
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
    public List onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_placement_list_adapter2, viewGroup, false);
        return new List(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final List holder, final int i) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("admin");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(email, ds.child("mail").getValue(String.class))) {
                        senderKey = ds.getKey();
                        user = ds.child("name").getValue(String.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = details.get(holder.getAdapterPosition()).getType();
                String receiverKey = details.get(holder.getAdapterPosition()).getKey();
                Toast.makeText(c, "" + senderKey + " " + user, Toast.LENGTH_SHORT).show();
                switch (name) {
                    case "college_authority":
                        c.startActivity(new Intent(c, collegeAdminChat.class).putExtra("key", receiverKey).putExtra("user", user).putExtra("senderKey", senderKey));
                        break;
                    case "counsellor":
                        c.startActivity(new Intent(c, counsellorLiveChat.class).putExtra("key", receiverKey).putExtra("user", user).putExtra("senderKey", senderKey));
                        break;
                    case "trainer":
                        c.startActivity(new Intent(c, trainerLiveChat.class).putExtra("key", receiverKey).putExtra("user", user).putExtra("senderKey", senderKey));
                        break;
                    case "school_authority":
                        c.startActivity(new Intent(c, schoolAdminChat.class).putExtra("key", receiverKey).putExtra("user", user).putExtra("senderKey", senderKey));
                        break;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}