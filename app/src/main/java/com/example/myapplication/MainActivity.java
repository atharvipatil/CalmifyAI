package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView cardChatbot;
    private CardView cardCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        cardChatbot = findViewById(R.id.cardChatbot);
        cardCalendar = findViewById(R.id.cardCalendar);

        // Set click listeners
        cardChatbot.setOnClickListener(v -> openChatbot());
        cardCalendar.setOnClickListener(v -> openCalendar());
    }

    private void openChatbot() {
        Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
        startActivity(intent);
    }

    private void openCalendar() {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
}
