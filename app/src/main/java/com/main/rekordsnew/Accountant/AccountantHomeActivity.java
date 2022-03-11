package com.main.rekordsnew.Accountant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityAccountantDashboardBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AccountantHomeActivity extends AppCompatActivity {

    private ActivityAccountantDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountantDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}