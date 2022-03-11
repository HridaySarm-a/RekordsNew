package com.main.rekordsnew.Admin.ChildFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.databinding.FragmentAdminDashboardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdminDashboardFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{

    FragmentAdminDashboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(getLayoutInflater());
        initChart();
        initButtons();
        initTeaPrice();
        initVerificationCode();
        return binding.getRoot();
    }

    private void initTeaPrice() {
        FirebaseDatabase.getInstance().getReference(Global.teaPriceRef)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            binding.teaPriceTv.setText(String.valueOf(snapshot.getValue()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initVerificationCode(){
        FirebaseDatabase.getInstance().getReference(Global.vCodeRef)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            binding.dhClientVCodeTv.setText(String.valueOf(snapshot.getValue()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initButtons() {
        binding.dhEditTeaPrice.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Set Tea Price");
//            builder.setMessage("Enter the updated tea price");
//            View view1= LayoutInflater.from(getContext()).inflate(R.layout.tea_price_dialog,null);
//            CustomEditText editText = view1.findViewById(R.id.tpd_edt);
//            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
//
//            }).setPositiveButton("Update", (dialogInterface, i) -> {
//                if (editText.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
//                }   else {
//                    float price = Float.parseFloat(editText.getText().toString());
//
//                    updatePrice(price);
//                    dialogInterface.dismiss();
//                }
//            });
        });

        binding.dhEditVCode.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Verification Code");
//            builder.setMessage("Enter the updated verification code");
//            View view1= LayoutInflater.from(getContext()).inflate(R.layout.tea_price_dialog,null);
//            CustomEditText editText = view1.findViewById(R.id.tpd_edt);
//            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
//
//            }).setPositiveButton("Update", (dialogInterface, i) -> {
//                if (editText.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
//                }   else {
//                    int  price = Integer.parseInt(editText.getText().toString());
//                    updateCode(price);
//                    dialogInterface.dismiss();
//                }
//            });
        });
    }

    private void updateCode(int price) {
        FirebaseDatabase.getInstance().getReference(Global.vCodeRef)
                .setValue(String.valueOf(price))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Tea Price has been updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updatePrice(float price) {
        FirebaseDatabase.getInstance().getReference(Global.teaPriceRef)
                .setValue(String.valueOf(price))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Tea Price has been updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    private void initChart() {
        binding.chart1.getDescription().setEnabled(false);

        // enable touch gestures
        binding.chart1.setTouchEnabled(true);

        binding.chart1.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        binding.chart1.setDragEnabled(true);
        binding.chart1.setScaleEnabled(true);
        binding.chart1.setDrawGridBackground(false);
        binding.chart1.setHighlightPerDragEnabled(true);

        // set an alternative background color
        binding.chart1.setBackgroundColor(Color.WHITE);
        binding.chart1.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
        binding.seekBar1.setProgress(50);
        setData(binding.seekBar1.getProgress(), 50);

        // get the legend (only possible after setting data)
        Legend l = binding.chart1.getLegend();
        l.setEnabled(false);

        XAxis xAxis = binding.chart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });



        YAxis leftAxis = binding.chart1.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(170f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = binding.chart1.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setData(int count, float range) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<>();

        // count = hours
        float to = now + count;

        // increment by 1 hour
        for (float x = now; x < to; x++) {

            float y = getRandom(range, 50);
            values.add(new Entry(x, y)); // add one entry per hour
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        binding.chart1.setData(data);
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        binding.tvXMax.setText(String.valueOf(binding.seekBar1.getProgress()));

        setData(binding.seekBar1.getProgress(), 50);

        // redraw
        binding.chart1.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}