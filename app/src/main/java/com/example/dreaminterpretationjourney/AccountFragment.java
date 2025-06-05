package com.example.dreaminterpretationjourney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private TextView userEmail;

    private Button changePwd, logoutBtn, deleteBtn;

    public AccountFragment (){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        userEmail = view.findViewById(R.id.account_text_email);

        changePwd = view.findViewById(R.id.account_btn_change_pwd);
        logoutBtn = view.findViewById(R.id.account_btn_logout);
        deleteBtn = view.findViewById(R.id.account_btn_delete);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userEmail.setText(email);

        changePwd.setOnClickListener(v -> {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(getActivity(), "비밀번호 재설정 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(getActivity(), "메일 전송 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 뒤로가기 방지
            startActivity(intent);
        });

        deleteBtn.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                // 로그인 화면으로 이동 (예시: LoginActivity)
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "회원 탈퇴 실패: 재로그인 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        return view;
    }
}
