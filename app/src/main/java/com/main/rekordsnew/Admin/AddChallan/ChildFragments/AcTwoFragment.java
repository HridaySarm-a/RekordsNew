package com.main.rekordsnew.Admin.AddChallan.ChildFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.EventBus.SecondPhaseDone;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentAcTwoBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class AcTwoFragment extends Fragment {

    private FragmentAcTwoBinding binding;
    List<OtherModel> selectedList;
    Challan challan = new Challan();


    public AcTwoFragment(List<OtherModel> selectedList) {
        this.selectedList = selectedList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAcTwoBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.acCard2Continue.setOnClickListener(view -> {
            if (binding.acRate.getText().toString().equals("") || binding.acPercent.getText().toString().equals("")){
                Snackbar.make(view,"Complete all the fields to continue",Snackbar.LENGTH_SHORT).show();
            }else {
                challan.setRate(Float.parseFloat(binding.acRate.getText().toString()));
                challan.setPercent(Float.parseFloat(binding.acPercent.getText().toString()));
                challan.setChallanNo(Integer.parseInt(binding.acChallanNo.getText().toString()));
                challan.setCollectors(selectedList);
                EventBus.getDefault().postSticky(new SecondPhaseDone(true,challan));
            }
        });
    }


}