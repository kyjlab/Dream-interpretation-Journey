package com.example.dreaminterpretationjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class InterpretaionFragment extends Fragment {
    public InterpretaionFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interpretaion_ai, container, false);
    }
}
