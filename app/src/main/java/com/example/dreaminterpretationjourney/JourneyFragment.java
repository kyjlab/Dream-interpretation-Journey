package com.example.dreaminterpretationjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JourneyFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private EditText diaryEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String selectedDate;

    public JourneyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey, container, false);

        calendarView = view.findViewById(R.id.journey_calendar);
        diaryEditText = view.findViewById(R.id.journey_edit_diary);
        saveButton = view.findViewById(R.id.journey_button_save);
        db = FirebaseFirestore.getInstance();

        selectedDate = getTodayDate();
        loadDiary(selectedDate);

        calendarView.setSelectedDate(CalendarDay.today());

        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_NONE);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = String.format("%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
            loadDiary(selectedDate);
        });

        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(
                getResources().getTextArray(R.array.custom_weekdays))
        );

        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(
                getResources().getTextArray(R.array.custom_months))
        );

        saveButton.setOnClickListener(v -> saveDiary(selectedDate, diaryEditText.getText().toString()));

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        loadWrittenDates(uid);

        return view;
    }

    private void saveDiary(String date, String content) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid)
                .collection("diaries").document(date)
                .set(new DiaryEntry(content))
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "저장됨!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadDiary(String date) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid)
                .collection("diaries").document(date)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        diaryEditText.setText(document.getString("content"));
                    } else {
                        diaryEditText.setText("");
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "불러오기 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void loadWrittenDates(String uid) {
        db.collection("users").document(uid)
                .collection("diaries")
                .get()
                .addOnSuccessListener(result -> {
                    List<String> writtenDates = new ArrayList<>();
                    for (DocumentSnapshot doc : result) {
                        writtenDates.add(doc.getId()); // 문서 ID가 날짜 (yyyy-MM-dd)
                    }

                    if (isAdded() && getContext() != null) {
                        calendarView.addDecorator(new DiaryDecorator(getContext(), writtenDates));
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded() && getContext() != null) {
                        Toast.makeText(getContext(), "작성 날짜 불러오기 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
