package com.main.rekordsnew.Client.Fragments.ChildHomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.rekordsnew.Client.Operations.ClientViewChallansActivity;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentClientMainOneBinding;

public class ClientMainOneFragment extends Fragment {

    private FragmentClientMainOneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientMainOneBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.clientViewChallanCvBtn.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), ClientViewChallansActivity.class));
        });
    }
}