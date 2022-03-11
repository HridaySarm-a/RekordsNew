package com.main.rekordsnew.LeafCollectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.main.rekordsnew.LeafCollectors.Fragments.LeafHomeFragment;
import com.main.rekordsnew.LeafCollectors.Fragments.LeafMessageFragment;
import com.main.rekordsnew.LeafCollectors.Fragments.LeafProfileFragment;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityLeafCollectorHomeBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class LeafCollectorHomeActivity extends AppCompatActivity {

    ActivityLeafCollectorHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeafCollectorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBottomBar();
    }


    private void initBottomBar() {
        binding.leafHomeBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i){
                case 0:
                    LeafHomeFragment leafHomeFragment = new LeafHomeFragment();
                    changeFragment(leafHomeFragment);
                    break;
                case 1:
                    LeafProfileFragment leafProfileFragment = new LeafProfileFragment();
                    changeFragment(leafProfileFragment);
                    break;
                case 2:
                    LeafMessageFragment leafMessageFragment = new LeafMessageFragment();
                    changeFragment(leafMessageFragment);
                    break;
            }
            return false;
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.leaf_home_container,fragment);
        fragmentTransaction.commit();
    }

}