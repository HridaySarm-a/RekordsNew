package com.main.rekordsnew.Admin.ChildFragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Admin.AutoGenerate.AutoGenerateChallanActivity;
import com.main.rekordsnew.LeafCollectors.LeafEntryActivity;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Objects.MrToast;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentAdminAutoGenerateChallanBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AdminAutoGenerateChallanFragment extends Fragment {

    FragmentAdminAutoGenerateChallanBinding binding;
    final Calendar calendar = Calendar.getInstance();
    private String dateStr = "";
    DatePickerDialog.OnDateSetListener date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminAutoGenerateChallanBinding.inflate(getLayoutInflater());
        initDatePicker();
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {

        binding.faagcGenerateBtn.setOnClickListener(view -> {
            if (dateStr.equals("")) {
                MrToast.showWarning(requireActivity(), "No Date ", "Please select a date");
            } else {
                checkDate();
            }
        });

        binding.faagcSelectDateCV.setOnClickListener(view -> {
            new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void checkDate() {
        FirebaseDatabase.getInstance().getReference(Global.collectionRef)
                .child(dateStr)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Intent intent = new Intent(requireActivity(), AutoGenerateChallanActivity.class);
                            intent.putExtra("Date", dateStr);
                            startActivity(intent);
                        } else {
                            MrToast.showError(requireActivity(), "No Data", "Please select another date");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initDatePicker() {
        date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateStr = dateFormat.format(calendar.getTime());
        binding.faagcDateTv.setText(dateStr);
        String[] splitStr = dateStr.split("/");
        dateStr = "";
        for (String s : splitStr) {
            dateStr = new StringBuilder().append(dateStr).append(s).toString();
        }
    }

}