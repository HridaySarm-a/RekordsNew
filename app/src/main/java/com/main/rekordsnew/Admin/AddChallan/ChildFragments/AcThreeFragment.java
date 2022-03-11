package com.main.rekordsnew.Admin.AddChallan.ChildFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Client.POJO.Entry;
import com.main.rekordsnew.EventBus.GenerateChallan;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentAcThreeBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class AcThreeFragment extends Fragment {

    private FragmentAcThreeBinding binding;
    private Challan challan;
    int count = 1;
    List<Entry> entryList = new ArrayList<>();

    public AcThreeFragment(Challan challan) {
        this.challan = challan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAcThreeBinding.inflate(getLayoutInflater());
        updLayout(challan.getCollectors().size());
        return binding.getRoot();
    }


    private void updLayout(int collectorNum) {
        binding.acSlNo.setText(new StringBuilder("Sl. No. ").append(count));
        binding.acSlNo2.setText(new StringBuilder("").append(count).append("/").append(collectorNum));
        Entry entry = new Entry();

        clearForm((ViewGroup) binding.thirdContainer);

        if (count == collectorNum) {
            binding.acNextBtn.setText("GENERATE CHALLAN");
        }

        binding.acNextBtn.setOnClickListener(view -> {
            try {
                entry.setLocal(Float.parseFloat(binding.acLocal.getText().toString()));
                entry.setAdmin(Float.parseFloat(binding.acAdmin.getText().toString()));
                entry.setComm(Float.parseFloat(binding.acComm.getText().toString()));
                entry.setCarrying(Float.parseFloat(binding.acCarryingCharge.getText().toString()));
                entry.setMisc(Float.parseFloat(binding.acMisc.getText().toString()));
            }catch (Exception e){
                Log.e("AC Three",e.getLocalizedMessage());
            }
            entryList.add(entry);
            if (count == collectorNum) {
                challan.setEntries(entryList);
                generateChallan(challan);
            } else {
                count++;
                updLayout(collectorNum);
            }
        });
    }

    private void generateChallan(Challan challan) {
        for (Entry entry :
                challan.getEntries()) {
            entry.setMinus(generateMinus(entry.getLocal(), challan.getPercent()));
            entry.setNet(entry.getLocal() - generateMinus(entry.getLocal(), challan.getPercent()));
            entry.setAmount(entry.getNet() * challan.getRate());
            entry.setTotal(entry.getAdmin() + entry.getCarrying() + entry.getMisc());
            entry.setNetAmount(entry.getNet() + entry.getTotal());
        }
        EventBus.getDefault().postSticky(new GenerateChallan(true, challan));
    }

    private float generateMinus(float local, float percent) {
        return (local / 100) * percent;
    }


    private void clearForm(ViewGroup group) {
        for (int i = 0, c = group.getChildCount(); i < c; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

}