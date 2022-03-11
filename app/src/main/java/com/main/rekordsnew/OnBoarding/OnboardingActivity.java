package com.main.rekordsnew.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.main.rekordsnew.Login.LoginActivity;
import com.main.rekordsnew.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getStartedBtn.setOnClickListener(view -> {
            binding.getStartedBtn.startAnim();
            getStarted();
        });


    }

    private void getStarted() {
        binding.getStartedBtn.postOnAnimation(() -> {
            startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        });
    }


}