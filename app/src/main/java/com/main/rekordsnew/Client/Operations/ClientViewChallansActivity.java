package com.main.rekordsnew.Client.Operations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.main.rekordsnew.Adapters.ClientViewChallanAdapter;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Objects.Common;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.R;
import com.main.rekordsnew.ViewModels.IClientsViewModel;
import com.main.rekordsnew.databinding.ActivityClientViewChallansBinding;

import java.util.ArrayList;
import java.util.List;

public class ClientViewChallansActivity extends AppCompatActivity {

    private ActivityClientViewChallansBinding binding;
    private IClientsViewModel clientsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientViewChallansBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clientsViewModel = ViewModelProviders.of(this).get(IClientsViewModel.class);
        initChallans();
    }



    private void initChallans() {
        if (Global.CURRENT_USER_CLIENT == null){
            List<Challan> tempList = new ArrayList<>();
            if (Global.CURRENT_USER_CLIENT.getChallans() != null){
                for (String key : Global.CURRENT_USER_CLIENT.getChallans().keySet()){
                    tempList.add(Global.CURRENT_USER_CLIENT.getChallans().get(key));
                }
                binding.cvcRv.setLayoutManager(new LinearLayoutManager(this));
                binding.cvcRv.setAdapter(new ClientViewChallanAdapter(this,tempList));
            }
        }else {
            List<Challan> tempList = new ArrayList<>();
            if (Global.CURRENT_USER_CLIENT.getChallans() != null){
                for (String key : Global.CURRENT_USER_CLIENT.getChallans().keySet()){
                    tempList.add(Global.CURRENT_USER_CLIENT.getChallans().get(key));
                }
                binding.cvcRv.setLayoutManager(new LinearLayoutManager(this));
                binding.cvcRv.setAdapter(new ClientViewChallanAdapter(this,tempList));
            }else {
                Toast.makeText(this, "Sorry, you do not have ", Toast.LENGTH_SHORT).show();
            }
        }
      

    }
}