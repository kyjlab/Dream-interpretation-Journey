package com.example.dreaminterpretationjourney;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment{

    private Button writeJourney, viewInterpretation, viewCalendar, settings;
    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        writeJourney = view.findViewById(R.id.home_btn_writeJourney);
        viewInterpretation = view.findViewById(R.id.home_btn_interpretation);
        viewCalendar = view.findViewById(R.id.home_btn_viewCalendar);
        settings = view.findViewById(R.id.home_btn_settings);

        writeJourney.setOnClickListener(v -> navigateTo(new JourneyFragment()));
        viewInterpretation.setOnClickListener(v -> navigateTo(new InterpretaionFragment()));
        viewCalendar.setOnClickListener(v -> navigateTo(new JourneyFragment()));
        settings.setOnClickListener(v -> navigateTo(new AccountFragment()));

        return view;
    }
    private void navigateTo(Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
