package com.education.counselor.trainer.authority.college.liveChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CollegeAdminLiveChat extends AppCompatActivity {
    RecyclerView recyclerView;
    Button send;
    DatabaseReference db,ref;
    chatAdapter adapter;
    ProgressBar pg;
    LinearLayoutManager mLinearLayoutManager;
    Context mContext;
    EditText text;
    String email,name, key="";
    private FirebaseRecyclerAdapter<chatMessages, MessageViewHolder> mFirebaseAdapter;
    private ArrayList<chatMessages> details = new ArrayList<>();

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView message,name,date;
        public MessageViewHolder(View v) {
            super(v);
            message =  itemView.findViewById(R.id.cname);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.cid);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_admin_live_chat);
        send=findViewById(R.id.send);
        text = findViewById(R.id.message);
        pg=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycle);
        mContext = this;
        send.setEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent i=getIntent();
        if (user != null) {
            email = user.getEmail();
            if(i.hasExtra("key"))
                key=i.getStringExtra("key");
            if(i.hasExtra("name"))
                name=i.getStringExtra("name");

        }
        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        db = FirebaseDatabase.getInstance().getReference("college_authority");
        SnapshotParser<chatMessages> parser = new SnapshotParser<chatMessages>() {
            @NonNull
            @Override
            public chatMessages parseSnapshot(DataSnapshot dataSnapshot) {
                chatMessages message = dataSnapshot.getValue(chatMessages.class);
                if (message != null) {
                    message.setDate(dataSnapshot.getKey());
                }
                return message;
            }
        };
        Toast.makeText(mContext, email+" "+key, Toast.LENGTH_SHORT).show();
        final DatabaseReference messagesRef = db.child(key).child("live_chat");

        FirebaseRecyclerOptions<chatMessages> options =
                new FirebaseRecyclerOptions.Builder<chatMessages>()
                        .setQuery(messagesRef, parser)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<chatMessages, MessageViewHolder>(options) {
            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new MessageViewHolder(inflater.inflate(R.layout.live_chat_layout, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull final MessageViewHolder viewHolder,
                                            int position,
                                            @NonNull chatMessages message) {
                pg.setVisibility(ProgressBar.INVISIBLE);
                if (text.getText() != null) {
                    viewHolder.message.setText(message.getMessage());
                    viewHolder.name.setText(message.getName());
                    viewHolder.name.setText(message.getDate());
                    Toast.makeText(mContext,"Empty...", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext, "Unable to fetch details", Toast.LENGTH_SHORT).show();
                if (message.getName() != null) {
                    // write this message to the on-device index
                    FirebaseAppIndex.getInstance().update(getMessageIndexable(message));
                }

                // log a view action on it
                FirebaseUserActions.getInstance().end(getMessageViewAction(message));
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });

        recyclerView.setAdapter(mFirebaseAdapter);
        pg.setVisibility(View.GONE);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    send.setEnabled(true);
                } else {
                    send.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatMessages chat=new chatMessages();
                chat.setMessage(text.getText().toString());
                chat.setDate(new Date().toString());
                chat.setName(name);
                messagesRef.push().setValue(chat);
            }
        });

    }
    private Indexable getMessageIndexable(chatMessages friendlyMessage) {
        PersonBuilder sender = Indexables.personBuilder()
                .setIsSelf(email.equals(friendlyMessage.getName()))
                .setName(friendlyMessage.getName());

        PersonBuilder recipient = Indexables.personBuilder()
                .setName(email);

        return Indexables.messageBuilder()
                .setName(friendlyMessage.getMessage())
                .setSender(sender)
                .setRecipient(recipient)
                .build();
    }
    private Action getMessageViewAction(chatMessages friendlyMessage) {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setMetadata(new Action.Metadata.Builder().setUpload(false))
                .build();
    }
}