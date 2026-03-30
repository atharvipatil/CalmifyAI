package com.example.ai;  // ✅ CORRECT!

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Calendarfragment extends Fragment {

    // Stress levels: 0 = none, 1 = green (relax), 2 = yellow (moderate), 3 = red (extreme)
    private static final int STRESS_NONE     = 0;
    private static final int STRESS_RELAX    = 1;
    private static final int STRESS_MODERATE = 2;
    private static final int STRESS_EXTREME  = 3;

    private Calendar currentCalendar;
    private TextView tvMonthYear;
    private GridView calendarGrid;
    private CalendarAdapter adapter;

    // Key: "yyyy-MM-dd", Value: stress level
    private Map<String, Integer> stressData = new HashMap<>();
    private List<String> dayKeys = new ArrayList<>();

    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        prefs           = requireContext().getSharedPreferences("CalmifyCalendar", Context.MODE_PRIVATE);
        currentCalendar = Calendar.getInstance();

        tvMonthYear  = view.findViewById(R.id.tvMonthYear);
        calendarGrid = view.findViewById(R.id.calendarGrid);

        ImageButton btnPrev = view.findViewById(R.id.btnPrevMonth);
        ImageButton btnNext = view.findViewById(R.id.btnNextMonth);
        MaterialButton btnSave = view.findViewById(R.id.btnSaveCalendar);

        loadStressData();
        buildCalendar();

        btnPrev.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            buildCalendar();
        });

        btnNext.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            buildCalendar();
        });

        btnSave.setOnClickListener(v -> saveStressData());

        calendarGrid.setOnItemClickListener((parent, v2, position, id) -> {
            String key = dayKeys.get(position);
            if (key.isEmpty()) return;

            int current = stressData.containsKey(key) ? stressData.get(key) : STRESS_NONE;
            int next = (current + 1) % 4;
            if (next == STRESS_NONE) {
                stressData.remove(key);
            } else {
                stressData.put(key, next);
            }
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void buildCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvMonthYear.setText(sdf.format(currentCalendar.getTime()));

        dayKeys.clear();

        Calendar cal = (Calendar) currentCalendar.clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth    = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < firstDayOfWeek; i++) {
            dayKeys.add("");
        }

        String monthPrefix = new SimpleDateFormat("yyyy-MM", Locale.getDefault())
                .format(currentCalendar.getTime());

        for (int d = 1; d <= daysInMonth; d++) {
            dayKeys.add(monthPrefix + "-" + String.format("%02d", d));
        }

        while (dayKeys.size() % 7 != 0) {
            dayKeys.add("");
        }

        adapter = new CalendarAdapter();
        calendarGrid.setAdapter(adapter);
    }

    private void saveStressData() {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Integer> entry : stressData.entrySet()) {
            try {
                json.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        prefs.edit().putString("stressData", json.toString()).apply();
        Toast.makeText(getContext(), "✅ Stress record saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadStressData() {
        String json = prefs.getString("stressData", "{}");
        stressData.clear();
        try {
            JSONObject obj = new JSONObject(json);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                stressData.put(key, obj.getInt(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CalendarAdapter extends BaseAdapter {

        @Override public int getCount()           { return dayKeys.size(); }
        @Override public Object getItem(int pos)  { return dayKeys.get(pos); }
        @Override public long getItemId(int pos)  { return pos; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_calendar_day, parent, false);
            }
            TextView tvDay = convertView.findViewById(R.id.tvDay);

            String key = dayKeys.get(position);

            if (key.isEmpty()) {
                tvDay.setText("");
                tvDay.setBackgroundColor(Color.TRANSPARENT);
                return convertView;
            }

            String[] parts = key.split("-");
            tvDay.setText(parts[2].replaceFirst("^0", ""));

            int stress = stressData.containsKey(key) ? stressData.get(key) : STRESS_NONE;

            switch (stress) {
                case STRESS_RELAX:
                    tvDay.setBackgroundColor(Color.parseColor("#A5D6A7"));
                    tvDay.setTextColor(Color.parseColor("#1B5E20"));
                    break;
                case STRESS_MODERATE:
                    tvDay.setBackgroundColor(Color.parseColor("#FFE082"));
                    tvDay.setTextColor(Color.parseColor("#E65100"));
                    break;
                case STRESS_EXTREME:
                    tvDay.setBackgroundColor(Color.parseColor("#EF9A9A"));
                    tvDay.setTextColor(Color.parseColor("#B71C1C"));
                    break;
                default:
                    tvDay.setBackgroundColor(Color.WHITE);
                    tvDay.setTextColor(Color.parseColor("#212121"));
                    break;
            }

            return convertView;
        }
    }
}