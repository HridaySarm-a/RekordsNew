package com.main.rekordsnew.Client.Fragments;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.rekordsnew.Admin.ChildFragments.AdminAccountantFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminClientFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminDashboardFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminLeafFragment;
import com.main.rekordsnew.Admin.ChildFragments.AdminMedicalFragment;
import com.main.rekordsnew.Client.Fragments.ChildHomeFragments.ClientMainOneFragment;
import com.main.rekordsnew.Client.Fragments.ChildHomeFragments.ClientMainTwoFragment;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentClientHomeBinding;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class ClientHomeFragment extends Fragment {

    FragmentClientHomeBinding binding;
    private String CURRENT_CHILD_FRAGMENT;
    private ICurrentFragment currentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientHomeBinding.inflate(getLayoutInflater());
        initTab();
        return binding.getRoot();
    }

    private void initTab() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.homE, ClientMainOneFragment.class)
                .add(R.string.laborData, ClientMainTwoFragment.class)
                .create());
        binding.clientHomeDashboardViewpager.setAdapter(adapter);
        binding.clientHomeDashboardTab.setViewPager(binding.clientHomeDashboardViewpager);
        binding.clientHomeDashboardTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        CURRENT_CHILD_FRAGMENT = "CLIENT_MAIN_ONE";
//                        currentFragment = (ICurrentFragment) getContext();
//                        currentFragment.currentFragment("ADMIN_HOME");
                        break;
                    case 1:
                        CURRENT_CHILD_FRAGMENT = "CLIENT_MAIN_TWO";
//                        currentFragment = (ICurrentFragment) getContext();
//                        currentFragment.currentFragment("ADMIN_CLIENTS");
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
}