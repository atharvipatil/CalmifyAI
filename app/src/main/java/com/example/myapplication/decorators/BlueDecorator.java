package com.example.myapplication.decorators;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Collection;
import java.util.HashSet;

public class BlueDecorator implements DayViewDecorator {
    private final HashSet<CalendarDay> dates;

    public BlueDecorator(Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));
    }
}
