package com.example.dreaminterpretationjourney;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DiaryDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;

    public DiaryDecorator(Context context, List<String> writtenDates) {
        drawable = ContextCompat.getDrawable(context, R.drawable.bg_selected);
        dates = new HashSet<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (String dateStr : writtenDates) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(format.parse(dateStr));
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                dates.add(CalendarDay.from(year, month + 1, day)); // month+1: Calendar uses 0-indexed months
            } catch (Exception ignored) {}
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
