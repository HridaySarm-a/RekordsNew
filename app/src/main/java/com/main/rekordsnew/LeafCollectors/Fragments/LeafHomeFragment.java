package com.main.rekordsnew.LeafCollectors.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.rekordsnew.LeafCollectors.LeafEntryActivity;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentLeafHomeBinding;

public class LeafHomeFragment extends Fragment {

    FragmentLeafHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLeafHomeBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.addNewEntryBtnLeaf.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), LeafEntryActivity.class));
            requireActivity().finish();
        });
    }
}