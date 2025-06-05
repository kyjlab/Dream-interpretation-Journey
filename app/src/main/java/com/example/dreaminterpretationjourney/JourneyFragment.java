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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JourneyFragment extends Fragment {

    private CalendarView calendarView;
    private EditText diaryEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String selectedDate;

    public JourneyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        diaryEditText = view.findViewById(R.id.diaryEditText);
        saveButton = view.findViewById(R.id.saveButton);
        db = FirebaseFirestore.getInstance();

        selectedDate = getTodayDate();
        loadDiary(selectedDate);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadDiary(selectedDate);
        });

        saveButton.setOnClickListener(v -> saveDiary(selectedDate, diaryEditText.getText().toString()));

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
}
