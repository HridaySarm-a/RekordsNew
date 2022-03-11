package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.EventBus.DownloadCSVClicked;
import com.main.rekordsnew.EventBus.DownloadPDFClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChallanAdapter extends RecyclerView.Adapter<ChallanAdapter.ViewHolder> {

    Context context;
    List<Challan> challans;

    public ChallanAdapter(Context context, List<Challan> challans) {
        this.context = context;
        this.challans = challans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.challan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.challanNo.setText(String.valueOf(challans.get(position).getChallanNo()));
        holder.rate.setText(String.valueOf(challans.get(position).getRate()));
        holder.percent.setText(challans.get(position).getPercent() + " %");
        holder.date.setText(challans.get(position).getDate());
        holder.downloadPDF.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new DownloadPDFClicked(true, challans.get(position)));

        });

        holder.downloadCSV.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new DownloadCSVClicked(true, challans.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        if (challans != null) {
            return challans.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView challanNo, date, rate, totalAmt, percent;
        CardView downloadCSV, downloadPDF;


        IonClick iitemClick;

        public void setIitemClick(IonClick iitemClick) {
            this.iitemClick = iitemClick;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            challanNo = itemView.findViewById(R.id.ci_challanNo);
            date = itemView.findViewById(R.id.ci_challanDate);
            rate = itemView.findViewById(R.id.ci_challanRate);
            totalAmt = itemView.findViewById(R.id.ci_challan_totalAmt);
            percent = itemView.findViewById(R.id.ci_challan_percent);
            downloadCSV = itemView.findViewById(R.id.ci_download_csv_btn);
            downloadPDF = itemView.findViewById(R.id.ci_download_pdf_btn);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iitemClick.onClicked(view, getAdapterPosition());
        }
    }
}
