package com.main.rekordsnew.Admin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.rekordsnew.Accountant.AccountantHomeActivity;
import com.main.rekordsnew.Client.ClientHomeActivity;
import com.main.rekordsnew.EventBus.SwitchClientClicked;
import com.main.rekordsnew.EventBus.SwitchOtherClicked;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.LeafCollectors.LeafCollectorHomeActivity;
import com.main.rekordsnew.Login.LoginActivity;
import com.main.rekordsnew.MedicalStaff.MedicalHomeActivity;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Prefrences.Prefs;
import com.main.rekordsnew.ProfileSwithing.ChangePasswordBottomSheetDialogFragment;
import com.main.rekordsnew.ProfileSwithing.SwitchAccountBottomSheetDialogFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentAdminProfileBinding;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AdminProfileFragment extends Fragment {

    private FragmentAdminProfileBinding binding;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(getLayoutInflater());
        ICurrentFragment currentFragment = (ICurrentFragment) getContext();
        if (currentFragment != null) {
            currentFragment.currentFragment("ADMIN_PROFILE");
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Checking credentials...");
        initButtons();
    }

    private void initButtons() {
        binding.adminLogoutTv.setOnClickListener(view -> {
            Prefs.saveLoggedUser(getContext(), "", "");
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        binding.switchAccountTv.setOnClickListener(v -> {
            PowerMenu powerMenu = new PowerMenu.Builder(getContext())// list has "Novel", "Poerty", "Art"
                    .addItem(new PowerMenuItem("Client", false)) // add an item.
                    .addItem(new PowerMenuItem("Accountant", false))
                    .addItem(new PowerMenuItem("Medical Staff", false))
                    .addItem(new PowerMenuItem("Leaf Collector", false))// aad an item list.
                    .setAnimation(MenuAnimation.ELASTIC_CENTER) // Animation start point (TOP | LEFT).
                    .setMenuRadius(10f) // sets the corner radius.
                    .setMenuShadow(10f) // sets the shadow.
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.black))
                    .setTextGravity(Gravity.CENTER)
                    .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(ContextCompat.getColor(getContext(), R.color.design_default_color_primary_dark))
                    .setOnMenuItemClickListener((position, item) -> {
                        switch (position) {
                            case 0:
                                showBottomSheet("Client");
                                break;
                            case 1:
                                showBottomSheet("Accountant");
                                break;
                            case 2:
                                showBottomSheet("Medical");
                                break;
                            case 3:
                                showBottomSheet("Leaf");
                                break;
                        }
                    })
                    .build();
            powerMenu.showAsDropDown(v);

        });

        binding.changePasswordTv.setOnClickListener(view -> {
            ChangePasswordBottomSheetDialogFragment changePasswordBottomSheet = new ChangePasswordBottomSheetDialogFragment("Admin");
            changePasswordBottomSheet.show(getChildFragmentManager(), "ChangePassword");
        });

    }

    private void showBottomSheet(String type) {
        SwitchAccountBottomSheetDialogFragment switchAccountBottomSheet = new SwitchAccountBottomSheetDialogFragment(type);
        switchAccountBottomSheet.show(getChildFragmentManager(), "SwitchAccount");
    }


    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onClientClicked(SwitchClientClicked clientClicked) {
        if (clientClicked.isClicked()) {
            Global.SELECTED_CLIENT = clientClicked.getClient();
            startActivity(new Intent(requireActivity(), ClientHomeActivity.class));
            requireActivity().finish();
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onOtherClicked(SwitchOtherClicked otherClicked) {
        if (otherClicked.isClicked()) {
            switch (otherClicked.getTYPE()) {
                case "Accountant":
                    Global.CURRENT_OTHER_USER = otherClicked.getOther();
                    startActivity(new Intent(requireActivity(), AccountantHomeActivity.class));
                    requireActivity().finish();
                    break;
                case "Medical":
                    Global.CURRENT_OTHER_USER = otherClicked.getOther();
                    startActivity(new Intent(requireActivity(), MedicalHomeActivity.class));
                    requireActivity().finish();
                    break;
                case "Leaf":
                    Global.CURRENT_OTHER_USER = otherClicked.getOther();
                    startActivity(new Intent(requireActivity(), LeafCollectorHomeActivity.class));
                    requireActivity().finish();
                    break;
            }
            EventBus.getDefault().removeAllStickyEvents();
        }
    }


}