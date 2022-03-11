package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.EventBus.CallClientClicked;
import com.main.rekordsnew.EventBus.ClientClicked;
import com.main.rekordsnew.EventBus.DeleteClientClicked;
import com.main.rekordsnew.EventBus.EditClientClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.R;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {

    Context context;
    List<ClientRoot> clientRootList;

    public ClientsAdapter(Context context, List<ClientRoot> clientRootList) {
        this.context = context;
        this.clientRootList = clientRootList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_client_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (clientRootList != null){
            Glide.with(context).load(clientRootList.get(position).getPersonalDetails().getImage()).into(holder.image);
        }
        holder.phone.setText(clientRootList.get(position).getPersonalDetails().getPhone());
        holder.address.setText(clientRootList.get(position).getPersonalDetails().getPermanentAddress().getVillage());
        holder.clientName.setText(clientRootList.get(position).getPersonalDetails().getName());
        holder.gardenName.setText(clientRootList.get(position).getGardenDetails().getGardenName());

        holder.delete.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new DeleteClientClicked(true,clientRootList.get(position)));
        });

        holder.call.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new CallClientClicked(true,clientRootList.get(position).getPersonalDetails().getPhone()));
        });

        holder.edit.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new EditClientClicked(true,clientRootList.get(position)));
        });

        holder.message.setOnClickListener(view -> {
            Toast.makeText(context, "This function will be available soon", Toast.LENGTH_SHORT).show();
        });

        holder.setIitemClickListener((view, position12) -> {
            Global.SELECTED_CLIENT = clientRootList.get(position12);
            EventBus.getDefault().postSticky(new ClientClicked(true,clientRootList.get(position12)));
        });

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

        ImageView image;
        TextView gardenName,clientName,address,phone;
        FloatingActionButton delete,edit,message,call;
        IonClick iitemClickListener;


        public void setIitemClickListener(IonClick iitemClickListener) {
            this.iitemClickListener = iitemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.dci_image);
            gardenName = itemView.findViewById(R.id.dci_garden_name);
            clientName = itemView.findViewById(R.id.dci_name);
            address = itemView.findViewById(R.id.dci_address);
            phone = itemView.findViewById(R.id.dci_phone);
            delete = itemView.findViewById(R.id.dci_delete_btn);
            edit = itemView.findViewById(R.id.dci_edit_btn);
            message = itemView.findViewById(R.id.dci_message_btn);
            call = itemView.findViewById(R.id.dci_call_btn);
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
            call.setOnClickListener(this);
            edit.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            iitemClickListener.onClicked(view,getAdapterPosition());
        }
    }
}
