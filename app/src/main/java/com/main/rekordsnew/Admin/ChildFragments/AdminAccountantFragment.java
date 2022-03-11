package com.main.rekordsnew.Admin.ChildFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.rekordsnew.Admin.ChildFragments.EditingnDeletingBottomSheets.ShowClientsBottomSheetDialogFragment;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.R;
import com.main.rekordsnew.Register.RegisterOthersActivity;
import com.main.rekordsnew.databinding.FragmentAdminAccountantBinding;

public class AdminAccountantFragment extends Fragment {

    private FragmentAdminAccountantBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminAccountantBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.adminAccountantsAdeCvBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View v = LayoutInflater.from(getContext()).inflate(R.layout.add_delete_edit_dialog_item, null);
            CardView addBtn = v.findViewById(R.id.adedi_add_cv_btn);
            CardView editOrDeleteBtn = v.findViewById(R.id.adedi_edit_cv_btn);

            addBtn.setOnClickListener(v1 -> {
                Intent intent = new Intent(requireActivity(), RegisterOthersActivity.class);
                startActivity(intent);
                requireActivity().finish();
            });


            editOrDeleteBtn.setOnClickListener(v3 -> {
                ShowClientsBottomSheetDialogFragment showClientsBottomSheetDialogFragment = new ShowClientsBottomSheetDialogFragment(Global.accountantRef);
                showClientsBottomSheetDialogFragment.show(getChildFragmentManager(), "EditOrDeleteOther");
            });
            builder.setView(v);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
    }
}