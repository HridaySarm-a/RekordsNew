package com.main.rekordsnew.Admin.AddChallan;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.main.rekordsnew.Admin.AddChallan.ChildFragments.AcOneFragment;
import com.main.rekordsnew.Admin.AddChallan.ChildFragments.AcThreeFragment;
import com.main.rekordsnew.Admin.AddChallan.ChildFragments.AcTwoFragment;
import com.main.rekordsnew.EventBus.CollectorsSelected;
import com.main.rekordsnew.EventBus.GenerateChallan;
import com.main.rekordsnew.EventBus.SecondPhaseDone;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityAddChallanBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AddChallanActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION = 89;
    private ActivityAddChallanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddChallanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                takePerms();
            } else {
                changeFragment(new AcOneFragment());
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            } else {
                changeFragment(new AcOneFragment());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            changeFragment(new AcOneFragment());
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_new_client_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void takePerms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                mGetPermission.launch(intent);
            } catch (Exception e) {
                Log.e("ERRRR", e.getMessage());
            }
        } else {
            Toast.makeText(this, "Old version", Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> mGetPermission = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            changeFragment(new AcOneFragment());
        } else {
            Toast.makeText(this, "Need permissions to continue", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCollectorsSelected(CollectorsSelected event) {
        if (event.isSuccessful()) {
            changeFragment(new AcTwoFragment(event.getSelectedList()));
            EventBus.getDefault().removeStickyEvent(CollectorsSelected.class);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSecondPhaseCompleted(SecondPhaseDone event) {
        if (event.isSuccessfull()) {
            changeFragment(new AcThreeFragment(event.getChallan()));
            EventBus.getDefault().removeStickyEvent(CollectorsSelected.class);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSecondPhaseCompleted(GenerateChallan event) {
        if (event.isSuccess()) {
            Intent intent = new Intent(AddChallanActivity.this, GenerateChallanActivity.class);
            intent.putExtra("Challan", event.getChallan());
            startActivity(intent);
            EventBus.getDefault().removeStickyEvent(CollectorsSelected.class);
        }
    }


}