package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.adapters.ChatAdapter;
import com.example.myapplication.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private ImageButton buttonSend;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Initialize views
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        // Setup RecyclerView
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        // Add welcome message
        addBotMessage("Hello! I'm your assistant. How can I help you today?");

        // Send button click listener
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();

        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add user message
        addUserMessage(message);
        editTextMessage.setText("");

        // Simulate bot response
        simulateBotResponse(message);
    }

    private void addUserMessage(String message) {
        chatMessages.add(new ChatMessage(message, true));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerViewChat.scrollToPosition(chatMessages.size() - 1);
    }

    private void addBotMessage(String message) {
        chatMessages.add(new ChatMessage(message, false));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerViewChat.scrollToPosition(chatMessages.size() - 1);
    }

    private void simulateBotResponse(String userMessage) {
        // Simulate a delay for bot response
        recyclerViewChat.postDelayed(new Runnable() {
            @Override
            public void run() {
                String response = generateResponse(userMessage);
                addBotMessage(response);
            }
        }, 1000);
    }

    private String generateResponse(String message) {
        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("hello") || lowerMessage.contains("hi")) {
            return "Hello! How can I assist you today?";
        } else if (lowerMessage.contains("calendar")) {
            return "You can access the calendar from the main menu to view and manage dates.";
        } else if (lowerMessage.contains("help")) {
            return "I can help you with:\n• Calendar information\n• General questions\n• Navigation tips";
        } else if (lowerMessage.contains("thank")) {
            return "You're welcome! Feel free to ask if you need anything else.";
        } else if (lowerMessage.contains("how are you")) {
            return "I'm doing great! Thanks for asking. How can I help you?";
        } else {
            return "I understand. Is there anything specific you'd like to know?";
        }
    }
}
