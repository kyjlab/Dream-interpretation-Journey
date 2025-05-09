package com.example.dreaminterpretationjourney;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);

        // 처음 시작 시 홈 화면 띄우기
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selected = new HomeFragment();
            } else if (id == R.id.nav_interpretaion_ai) {
                selected = new InterpretaionFragment();
            } else if (id == R.id.nav_menu_book) {
                selected = new JourneyFragment();
            } else if (id == R.id.nav_account_circle) {
                selected = new AccountFragment();
            }

            if (selected != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, selected)
                        .commit();
            }
            return true;
        });

    }

}