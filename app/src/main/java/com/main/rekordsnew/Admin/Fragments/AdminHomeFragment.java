package com.main.rekordsnew.Admin.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.main.rekordsnew.Admin.ChildFragments.AdminAccountantFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminAutoGenerateChallanFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminClientFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminDashboardFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminLeafFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminMedicalFragment;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentAdminHomeBinding;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;
    private String CURRENT_CHILD_FRAGMENT;
    private BroadcastReceiver receiver;
    private ICurrentFragment currentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminHomeBinding.inflate(getLayoutInflater());
        initTab();
        registerReceiver();
        return binding.getRoot();
    }

    private void initTab() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.homE, AdminDashboardFragment.class)
                .add("CHALLAN", AdminAutoGenerateChallanFragment.class)
                .add(R.string.clientS, AdminClientFragment.class)
                .add(R.string.leafCollectoR, AdminLeafFragment.class)
                .add(R.string.medicaL, AdminMedicalFragment.class)
                .add(R.string.accountant, AdminAccountantFragment.class)
                .create());
        binding.dashboardViewpager.setAdapter(adapter);
        binding.dashboardTab.setViewPager(binding.dashboardViewpager);
        binding.dashboardTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        CURRENT_CHILD_FRAGMENT = "ADMIN_HOME";
                        currentFragment = (ICurrentFragment) getContext();
                        currentFragment.currentFragment("ADMIN_HOME");
                        break;
                    case 1:
                        CURRENT_CHILD_FRAGMENT = "ADMIN_CLIENTS";
                        currentFragment = (ICurrentFragment) getContext();
                        currentFragment.currentFragment("ADMIN_CLIENTS");
                        break;
                    case 2:
                        CURRENT_CHILD_FRAGMENT = "ADMIN_LEAF_COLLECTORS";
                        currentFragment = (ICurrentFragment) getContext();
                        currentFragment.currentFragment("ADMIN_LEAF_COLLECTORS");
                        break;
                    case 3:
                        CURRENT_CHILD_FRAGMENT = "ADMIN_MEDICAL_STAFF";
                        currentFragment = (ICurrentFragment) getContext();
                        currentFragment.currentFragment("ADMIN_MEDICAL_STAFF");
                        break;
                    case 4:
                        CURRENT_CHILD_FRAGMENT = "ADMIN_ACCOUNTANT";
                        currentFragment = (ICurrentFragment) getContext();
                        currentFragment.currentFragment("ADMIN_ACCOUNTANT");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    boolean isClicked = intent.getBooleanExtra("backPressed", false);
                    if (isClicked) {
                        binding.dashboardViewpager.setCurrentItem(0);
                    }
                } else {
                    Toast.makeText(getContext(), "Intent is null", Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("action");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

}