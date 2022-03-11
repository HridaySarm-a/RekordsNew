package com.main.rekordsnew.Adapters.AutoChallanAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.rekordsnew.Admin.AutoGenerate.Models.AutoChallanModel;
import com.main.rekordsnew.EventBus.ACAClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AutoChallanAdapter extends RecyclerView.Adapter<AutoChallanAdapter.ViewHolder> {

    Context context;
    List<AutoChallanModel> autoChallanModelList;

    public AutoChallanAdapter(Context context, List<AutoChallanModel> autoChallanModelList) {
        this.context = context;
        this.autoChallanModelList = autoChallanModelList;
    }

    @NonNull
    @Override
    public AutoChallanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_challan_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoChallanAdapter.ViewHolder holder, int position) {
        holder.slNo.setText(String.valueOf(position + 1));
        holder.local.setText(String.valueOf(autoChallanModelList.get(position).getLocal()));
        holder.percentage.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "PERCENTAGE"));
        })
        ;holder.minus.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "MINUS"));
        });
        holder.net.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "NET"));
        });
        holder.rate.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "RATE"));
        });
        holder.amount.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "AMOUNT"));
        });
        holder.admin.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "ADMIN"));
        });
        holder.comm.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "COMM"));
        });
        holder.carrying.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "CARRYING"));
        });
        holder.cess.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "CESS"));
        });
        holder.misc.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "MISC"));
        });
        holder.total.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "TOTAL"));
        });
        holder.net_amount.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new ACAClicked(true, position, autoChallanModelList.get(position), "NET_AMOUNT"));
        });
    }

    public void updateData(int position,AutoChallanModel autoChallanModel){
        autoChallanModelList.set(position,autoChallanModel);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return autoChallanModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        //        TextView challanNo,date,rateMain,minusMain;
        TextView slNo, local, percentage, minus, net, rate, amount,
                admin, comm, carrying, cess, misc, total, net_amount;
        IonClick clickListener;

        public void setClickListener(IonClick clickListener) {
            this.clickListener = clickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            challanNo = itemView.findViewById(R.id.aagc_challan_no);
//            date = itemView.findViewById(R.id.aagc_date);
//            rateMain = itemView.findViewById(R.id.aagc_rate_main);
//            minusMain = itemView.findViewById(R.id.aagc_minus_main);

            slNo = itemView.findViewById(R.id.ncrl_slNo);
            slNo.setOnClickListener(this);
            local = itemView.findViewById(R.id.ncrl_local);
            local.setOnClickListener(this);
            percentage = itemView.findViewById(R.id.ncrl_percentage);
            percentage.setOnClickListener(this);
            minus = itemView.findViewById(R.id.ncrl_minus);
            minus.setOnClickListener(this);
            net = itemView.findViewById(R.id.ncrl_net);
            net.setOnClickListener(this);
            rate = itemView.findViewById(R.id.ncrl_rate);
            rate.setOnClickListener(this);
            amount = itemView.findViewById(R.id.ncrl_amount);
            amount.setOnClickListener(this);
            admin = itemView.findViewById(R.id.ncrl_admin);
            admin.setOnClickListener(this);
            comm = itemView.findViewById(R.id.ncrl_comm);
            comm.setOnClickListener(this);
            carrying = itemView.findViewById(R.id.ncrl_carrying);
            carrying.setOnClickListener(this);
            cess = itemView.findViewById(R.id.ncrl_cess);
            cess.setOnClickListener(this);
            misc = itemView.findViewById(R.id.ncrl_misc);
            misc.setOnClickListener(this);
            total = itemView.findViewById(R.id.ncrl_total);
            total.setOnClickListener(this);
            net_amount = itemView.findViewById(R.id.ncrl_net_amount);
            net_amount.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onClicked(view,getAdapterPosition());
        }
    }
}
