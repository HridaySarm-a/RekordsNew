package com.main.rekordsnew.Admin.ChildFragments.EditingnDeletingBottomSheets;

import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.main.rekordsnew.Adapters.EditOrDeleteOtherAdapter;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Edit_Update.DeleteOtherClicked;
import com.main.rekordsnew.Objects.MrToast;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.ViewModels.IClientsViewModel;
import com.main.rekordsnew.ViewModels.OtherViewModel;
import com.main.rekordsnew.databinding.FragmentShowClientsBottonSheetDialogBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ShowClientsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    FragmentShowClientsBottonSheetDialogBinding binding;
    OtherViewModel otherViewModel;
    String ref;

    public ShowClientsBottomSheetDialogFragment(String ref) {
        this.ref = ref;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShowClientsBottonSheetDialogBinding.inflate(getLayoutInflater());
        otherViewModel = new ViewModelProvider(this, new OtherViewModel(requireActivity().getApplication(), ref)).get(OtherViewModel.class);
        showClients();
        return binding.getRoot();
    }

    private void showClients() {
        otherViewModel.getOthersMutableLiveData().observe(getViewLifecycleOwner(), otherModels -> {
            binding.scdsdRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.scdsdRv.setAdapter(new EditOrDeleteOtherAdapter(getContext(), otherModels, ref));
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDeleteOtherClicked(DeleteOtherClicked event) {
        if (event.isSuccess()) {
            deleteClient(event.getOtherModel(), event.getRef());
            EventBus.getDefault().removeStickyEvent(DeleteOtherClicked.class);
        }
    }

    private void deleteClient(OtherModel otherModel, String ref) {
        FirebaseDatabase.getInstance().getReference(ref)
                .child(otherModel.getKey())
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        MrToast.showSuccess(requireActivity(), "Deleted", ref + " has been deleted successfully");
                        otherViewModel.LoadOthers(ref);
                    } else {
                        MrToast.showError(requireActivity(), "Failed", "Failed to delete " + ref);
                    }
                }).addOnFailureListener(e -> {
            Log.e("SHOW ", e.getLocalizedMessage());
        });
    }


}
