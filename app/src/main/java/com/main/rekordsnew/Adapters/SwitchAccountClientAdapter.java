package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.EventBus.SwitchClientClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwitchAccountClientAdapter extends RecyclerView.Adapter<SwitchAccountClientAdapter.ViewHolder> {

    Context context;
    List<ClientRoot> clientRootList;


    public SwitchAccountClientAdapter(Context context, List<ClientRoot> clientRootList) {
        this.context = context;
        this.clientRootList = clientRootList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.switch_account_client_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Glide.with(context).load(clientRootList.get(position).getPersonalDetails().getImage()).into(holder.image);
            holder.gardenName.setText(clientRootList.get(position).getGardenDetails().getGardenName());
            holder.phone.setText(clientRootList.get(position).getPersonalDetails().getPhone());
            holder.name.setText(clientRootList.get(position).getPersonalDetails().getName());
            holder.setIitemClick((view, position1) -> {
                EventBus.getDefault().postSticky(new SwitchClientClicked(true,clientRootList.get(position)));
            });
        }catch (NullPointerException e){
            Log.e("Nuller",e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (clientRootList != null){
            return clientRootList.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView image;
        TextView name,phone,gardenName;
        IonClick iitemClick;

        public void setIitemClick(IonClick iitemClick) {
            this.iitemClick = iitemClick;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.saci_image);
            name = itemView.findViewById(R.id.saci_name);
            phone = itemView.findViewById(R.id.saci_phone);
            gardenName = itemView.findViewById(R.id.saci_garden_name);
        }

        @Override
        public void onClick(View view) {
            iitemClick.onClicked(view,getAdapterPosition());
        }
    }
}