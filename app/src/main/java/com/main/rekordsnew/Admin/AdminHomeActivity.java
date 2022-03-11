package com.main.rekordsnew.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.main.rekordsnew.Admin.Fragments.AdminHomeFragment;
import com.main.rekordsnew.Admin.Fragments.AdminMessageFragment;
import com.main.rekordsnew.Admin.Fragments.AdminProfileFragment;
import com.main.rekordsnew.Admin.GrandchildFragments.AdminClientChallansFragment;
import com.main.rekordsnew.Edit_Update.EditClientActivity;
import com.main.rekordsnew.Edit_Update.EditOthersActivity;
import com.main.rekordsnew.EventBus.ClientClicked;
import com.main.rekordsnew.EventBus.EditClientClicked;
import com.main.rekordsnew.EventBus.EditOtherClicked;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityAdminHomeBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class AdminHomeActivity extends AppCompatActivity implements ICurrentFragment {

    private ActivityAdminHomeBinding binding;
    boolean doubleBackToExitPressedOnce = false;
    private String CURRENT_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBottomBar();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initBottomBar() {
        binding.homeBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i) {
                case 0:
                    AdminHomeFragment homeFragment = new AdminHomeFragment();
                    changeFragment(homeFragment);
                    break;
                case 1:
                    AdminProfileFragment profileFragment = new AdminProfileFragment();
                    changeFragment(profileFragment);
                    break;
                case 2:
                    AdminMessageFragment messageFragment = new AdminMessageFragment();
                    changeFragment(messageFragment);
                    break;
            }

            return false;

        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        binding.homeBottomBar.setItemActiveIndex(0);
        binding.homeBottomBar.setSelected(true);
        changeFragment(new AdminHomeFragment());
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEditClientClicked(EditClientClicked event) {
        if (event.isSuccess()) {
            Intent i = new Intent(this, EditClientActivity.class);
            Gson gson = new Gson();
            String obj = gson.toJson(event.getClient());
            i.putExtra("ClientObj", obj);
            startActivity(i);
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEditOtherClicked(EditOtherClicked event) {
        if (event.isSuccess()) {
            Intent i = new Intent(this, EditOthersActivity.class);
            Gson gson = new Gson();
            String obj = gson.toJson(event.getOtherModel());
            i.putExtra("OtherObj", obj);
            i.putExtra("REF", event.getRef());
            startActivity(i);
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onClientClicked(ClientClicked event) {
        binding.homeBottomBar.setVisibility(View.GONE);
        changeFragment(new AdminClientChallansFragment(event.getClient()));
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void currentFragment(String fragment) {
        CURRENT_FRAGMENT = fragment;
//        if (CURRENT_FRAGMENT.equals("ADMIN_LEAF_COLLECTORS") || CURRENT_FRAGMENT.equals("ADMIN_MEDICAL_STAFF") ||CURRENT_FRAGMENT.equals("ADMIN_ACCOUNTANT")||CURRENT_FRAGMENT.equals("ADMIN_CLIENT")){
//            FragmentManager manager = getSupportFragmentManager();
//            HomeFragment mReceiverFragment = (HomeFragment)manager.findFragmentById(R.id.dashboard_container);
//            mReceiverFragment.youGotMsg(fragment);
//        }

    }

    @Override
    public void onBackPressed() {
        switch (CURRENT_FRAGMENT) {
            case "ADMIN_HOME":
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed((Runnable) () -> doubleBackToExitPressedOnce = false, 2000);
                break;
            case "ADMIN_PROFILE":
            case "ADMIN_MESSAGES":
                AdminHomeFragment homeFragment = new AdminHomeFragment();
                changeFragment(homeFragment);
                binding.homeBottomBar.setSelected(true);
                binding.homeBottomBar.setItemActiveIndex(0);
                break;
            case "ADMIN_CLIENT_CHALLAN":
                binding.homeBottomBar.setVisibility(View.VISIBLE);
                changeFragment(new AdminHomeFragment());
                break;
            case "ADMIN_LEAF_COLLECTORS":
            case "ADMIN_MEDICAL_STAFF":
            case "ADMIN_ACCOUNTANT":
            case "ADMIN_CLIENTS":
                Intent intent = new Intent();
                intent.putExtra("backPressed", true);
                intent.setAction("action");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                break;

        }
    }

}