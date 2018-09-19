package com.example.user.myapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapp.R;
import com.example.user.myapp.db.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Message> messageList = new ArrayList<>();

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessagesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MessagesViewHolder messagesViewHolder = (MessagesViewHolder) holder;
        messagesViewHolder.tvUserKey.setText(messageList.get(i).userKey);
        messagesViewHolder.tvMessage.setText(messageList.get(i).messageText);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserKey, tvMessage;

        MessagesViewHolder(View itemView) {
            super(itemView);
            tvUserKey = itemView.findViewById(R.id.tvUserKey);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
