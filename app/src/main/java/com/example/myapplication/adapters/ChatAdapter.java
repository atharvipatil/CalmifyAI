package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.models.ChatMessage;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);

        if (message.isUser()) {
            // User message
            holder.textViewUserMessage.setVisibility(View.VISIBLE);
            holder.textViewBotMessage.setVisibility(View.GONE);
            holder.textViewUserMessage.setText(message.getMessage());
        } else {
            // Bot message
            holder.textViewUserMessage.setVisibility(View.GONE);
            holder.textViewBotMessage.setVisibility(View.VISIBLE);
            holder.textViewBotMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserMessage;
        TextView textViewBotMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserMessage = itemView.findViewById(R.id.textViewUserMessage);
            textViewBotMessage = itemView.findViewById(R.id.textViewBotMessage);
        }
    }
}