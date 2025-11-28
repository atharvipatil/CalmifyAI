package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView textViewSelectedDate;
    private TextView textViewInstructions;

    private Map<String, Integer> clickCountMap = new HashMap<>();
    private String currentSelectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        textViewInstructions = findViewById(R.id.textViewInstructions);

        // Set up date change listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Create date key
            String dateKey = year + "-" + (month + 1) + "-" + dayOfMonth;
            currentSelectedDate = dateKey;

            // Update click count
            int count = clickCountMap.getOrDefault(dateKey, 0) + 1;

            if (count > 3) {
                count = 0;  // Reset after triple click
            }

            clickCountMap.put(dateKey, count);

            // Update display
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            updateSelectedDateText(calendar, count);
        });

        // Set initial instructions
        textViewInstructions.setText("Tap dates to mark them:\n1 tap = Red (Important) | 2 taps = Green (Completed)\n3 taps = Blue (Pending) | 4 taps = Reset");
    }

    private void updateSelectedDateText(Calendar date, int clickCount) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        String formattedDate = sdf.format(date.getTime());

        String status;
        switch (clickCount) {
            case 1:
                status = "🔴 Red (Important)";
                break;
            case 2:
                status = "🟢 Green (Completed)";
                break;
            case 3:
                status = "🔵 Blue (Pending)";
                break;
            default:
                status = "⚪ Unmarked";
                break;
        }

        textViewSelectedDate.setText("Selected: " + formattedDate + "\nStatus: " + status);
    }
}