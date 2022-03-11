package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.main.rekordsnew.EventBus.AddSCBSItem;
import com.main.rekordsnew.EventBus.RemoveSCBSItem;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SCBSAdapter extends RecyclerView.Adapter<SCBSAdapter.ViewHolder> {

    Context context;
    List<OtherModel> leafCollectors;
    List<OtherModel> tempList;
    int num;

    public SCBSAdapter(Context context, List<OtherModel> leafCollectors, int num) {
        this.context = context;
        this.leafCollectors = leafCollectors;
        this.num = num;
        tempList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scbs_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(leafCollectors.get(position).getImage()).into(holder.image);
        holder.phone.setText(leafCollectors.get(position).getPhone());
        holder.name.setText(leafCollectors.get(position).getName());

        holder.setIitemClickListener((view, position1) -> {
            if (tempList.contains(leafCollectors.get(position))) {
                tempList.remove(leafCollectors.get(position));
                EventBus.getDefault().postSticky(new RemoveSCBSItem(true, leafCollectors.get(position)));
                holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            } else if (tempList.size() == 10) {
                Toast.makeText(context, "Maximum limit reached", Toast.LENGTH_SHORT).show();
            } else {
                EventBus.getDefault().postSticky(new AddSCBSItem(true, leafCollectors.get(position)));
                tempList.add(leafCollectors.get(position));
                holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow_low));
            }

        });

    }

    @Override
    public int getItemCount() {
        return leafCollectors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CircleImageView image;
        TextView name, phone;
        IonClick iitemClickListener;
        LinearLayout root;

        public void setIitemClickListener(IonClick iitemClickListener) {
            this.iitemClickListener = iitemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.scbsi_image);
            name = itemView.findViewById(R.id.scbsi_name);
            phone = itemView.findViewById(R.id.scbsi_phone);
            root = itemView.findViewById(R.id.scbsi_root);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iitemClickListener.onClicked(view, getAdapterPosition());
        }
    }
}
