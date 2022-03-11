package com.main.rekordsnew.Admin.ChildFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.rekordsnew.Admin.ChildFragments.EditingnDeletingBottomSheets.ShowClientsBottomSheetDialogFragment;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.R;
import com.main.rekordsnew.Register.RegisterOthersActivity;
import com.main.rekordsnew.databinding.FragmentAdminLeafBinding;

public class AdminLeafFragment extends Fragment {

    private FragmentAdminLeafBinding binding;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminLeafBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.addLeafCollectorCv.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View v = LayoutInflater.from(getContext()).inflate(R.layout.add_delete_edit_dialog_item, null);
            CardView addBtn = v.findViewById(R.id.adedi_add_cv_btn);
            CardView editOrDeleteBtn = v.findViewById(R.id.adedi_edit_cv_btn);

            addBtn.setOnClickListener(v1 -> {
                startActivity(new Intent(requireActivity(), RegisterOthersActivity.class));
                requireActivity().finish();
            });


            editOrDeleteBtn.setOnClickListener(v3 -> {
                try {
                    ShowClientsBottomSheetDialogFragment showClientsBottomSheetDialogFragment = new ShowClientsBottomSheetDialogFragment(Global.leafRef);
                    showClientsBottomSheetDialogFragment.show(getChildFragmentManager(), "EditOrDeleteOther");
                } catch (Exception e) {
                    Log.e("TTT", e.getLocalizedMessage());
                }
            });
            builder.setView(v);
            alertDialog = builder.create();
            alertDialog.show();

        });
    }
//
//    @Override
//    public void onStop() {
//        if (alertDialog != null) {
//            if (alertDialog.isShowing()) {
//                alertDialog.dismiss();
//            }
//        }
//        super.onStop();
//    }
}