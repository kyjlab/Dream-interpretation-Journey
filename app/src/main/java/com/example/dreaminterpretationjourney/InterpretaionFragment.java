package com.example.dreaminterpretationjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InterpretaionFragment extends Fragment {

    private EditText inputEditText;
    private Button interpretButton;
    private TextView resultTextView;

    public InterpretaionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interpretaion_ai, container, false);

        inputEditText = view.findViewById(R.id.interpret_edit_writeJourney);
        interpretButton = view.findViewById(R.id.interpret_button_interpretation);
        resultTextView = view.findViewById(R.id.interpret_text_result);

        interpretButton.setOnClickListener(v -> {
            String dream = inputEditText.getText().toString();
            if (!dream.isEmpty()) {
                String result = DreamInterpreter.interpret(dream);
                resultTextView.setText(result);
            } else {
                resultTextView.setText("꿈 내용을 입력해주세요.");
            }
        });

        return view;
    }
}
