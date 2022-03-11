package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class ClientViewChallanAdapter extends RecyclerView.Adapter<ClientViewChallanAdapter.ViewHolder> {

    Context context;
    List<Challan> challans;

    public ClientViewChallanAdapter(Context context, List<Challan> challans) {
        this.context = context;
        this.challans = challans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cvc_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.challanNo.setText(String.valueOf(challans.get(position).getChallanNo()));
        holder.amount.setText(String.valueOf(challans.get(position).getTotalAmt()));
        holder.date.setText(challans.get(position).getDate());
        holder.setClickListener((v, position1) -> {
            if (holder.expandableLayout.isExpanded()) {
                holder.expandableLayout.collapse();
            } else {
                holder.expandableLayout.expand();
            }
        });
    }

    @Override
    public int getItemCount() {
        return challans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView challanNo, amount, date;
        IonClick clickListener;
        MaterialButton viewChallan, downloadChallan;
        ExpandableLayout expandableLayout;

        public void setClickListener(IonClick clickListener) {
            this.clickListener = clickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            challanNo = itemView.findViewById(R.id.cvc_rv_challan_no);
            amount = itemView.findViewById(R.id.cvc_rv_amount);
            date = itemView.findViewById(R.id.cvc_rv_date);
            viewChallan = itemView.findViewById(R.id.cvc_rv_view_challan_btn);
            downloadChallan = itemView.findViewById(R.id.cvc_rv_download_challan_btn);
            expandableLayout = itemView.findViewById(R.id.expandable_layout_cvc_rv_item);
            itemView.setOnClickListener(this);
            viewChallan.setOnClickListener(this);
            downloadChallan.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClicked(view, getAdapterPosition());
        }
    }
}
