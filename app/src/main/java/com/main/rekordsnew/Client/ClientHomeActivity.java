package com.main.rekordsnew.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.main.rekordsnew.Client.Fragments.ClientHomeFragment;
import com.main.rekordsnew.Client.Fragments.ClientMessageFragment;
import com.main.rekordsnew.Client.Fragments.ClientProfileFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityClientHomeBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class ClientHomeActivity extends AppCompatActivity {

    private ActivityClientHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBottomBar();
    }

    private void initBottomBar() {
        binding.clientBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i){
                case 0:
                    ClientHomeFragment clientHomeFragment = new ClientHomeFragment();
                    changeFragment(clientHomeFragment);
                    break;
                case 1:
                    ClientProfileFragment clientProfileFragment = new ClientProfileFragment();
                    changeFragment(clientProfileFragment);
                    break;
                case 2:
                    ClientMessageFragment clientMessageFragment = new ClientMessageFragment();
                    changeFragment(clientMessageFragment);
                    break;
            }
            return false;
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.client_container,fragment);
        fragmentTransaction.commit();
    }

}