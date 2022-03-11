package com.main.rekordsnew.LeafCollectors.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentLeafProfileBinding;

public class LeafProfileFragment extends Fragment {

    FragmentLeafProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLeafProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}