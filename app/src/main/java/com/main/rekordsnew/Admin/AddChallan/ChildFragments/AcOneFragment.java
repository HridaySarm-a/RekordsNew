package com.main.rekordsnew.Admin.AddChallan.ChildFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.main.rekordsnew.Adapters.SCBSAdapter;
import com.main.rekordsnew.EventBus.AddSCBSItem;
import com.main.rekordsnew.EventBus.CollectorsSelected;
import com.main.rekordsnew.EventBus.RemoveSCBSItem;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.ViewModels.LeafViewModel;
import com.main.rekordsnew.databinding.FragmentAcOneBinding;
import com.shawnlin.numberpicker.NumberPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class AcOneFragment extends Fragment {


    private FragmentAcOneBinding binding;
    int num;
    List<OtherModel> selectedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAcOneBinding.inflate(getLayoutInflater());
        initViews();
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.scbsBtn.setOnClickListener(view -> {
            if (selectedList.size() != num) {
                Toast.makeText(getContext(), "Must select " + num + " leaf collectors to continue", Toast.LENGTH_SHORT).show();
            } else {
                EventBus.getDefault().postSticky(new CollectorsSelected(true, selectedList));
            }
        });
    }

    private void initAdapter() {
        LeafViewModel leafViewModel = ViewModelProviders.of(this).get(LeafViewModel.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.scbsRv.setLayoutManager(gridLayoutManager);
        leafViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), otherModelList -> {
            SCBSAdapter adapter = new SCBSAdapter(getContext(), otherModelList, num);
            binding.scbsRv.setAdapter(adapter);
        });
    }

    private void initViews() {
        binding.ac1NumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            binding.ac1NumberPickerTv.setText(String.valueOf(picker.getValue()));
        });

        binding.ac1Continue.setOnClickListener(view -> {
            num = binding.ac1NumberPicker.getValue();
            binding.acCard1.setVisibility(View.GONE);
            binding.ac12.setVisibility(View.VISIBLE);
            initAdapter();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onItemAdded(AddSCBSItem event){
        if (event.isSuccessful()){
            selectedList.add(event.getCollectorNode());
            initViews2();
            EventBus.getDefault().removeStickyEvent(AddSCBSItem.class);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews2() {
        binding.scbsTv.setText("Select "+num+" Leaf Collectors");
        binding.scbsSelectedText.setText(""+ selectedList.size()+"/"+num);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onItemRemoved(RemoveSCBSItem event){
        if (event.isSuccessful()){
            selectedList.remove(event.getCollectorNode());
            initViews2();
            EventBus.getDefault().removeStickyEvent(RemoveSCBSItem.class);
        }
    }


}