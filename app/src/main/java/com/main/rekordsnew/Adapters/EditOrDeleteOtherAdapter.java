package com.main.rekordsnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.rekordsnew.Edit_Update.DeleteOtherClicked;
import com.main.rekordsnew.EventBus.EditOtherClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class EditOrDeleteOtherAdapter extends RecyclerView.Adapter<EditOrDeleteOtherAdapter.ViewHolder> {

    Context context;
    List<OtherModel> clientRootList;
    String ref;

    public EditOrDeleteOtherAdapter(Context context, List<OtherModel> clientRootList, String ref) {
        this.context = context;
        this.clientRootList = clientRootList;
        this.ref = ref;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_or_delete_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.otherName.setText(clientRootList.get(position).getName());
        holder.editBtn.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new EditOtherClicked(true, clientRootList.get(position), ref));
        });
        holder.deleteBtn.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new DeleteOtherClicked(true,clientRootList.get(position),ref));
        });
    }

    @Override
    public int getItemCount() {
        return clientRootList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView otherName;
        FloatingActionButton editBtn, deleteBtn;
        IonClick clickListener;

        public void setClickListener(IonClick clickListener) {
            this.clickListener = clickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherName = itemView.findViewById(R.id.eod_rv_client_name);
            editBtn = itemView.findViewById(R.id.eod_rv_edit_btn);
            deleteBtn = itemView.findViewById(R.id.eod_rc_delete_btn);
            editBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClicked(view, getAdapterPosition());
        }
    }
}
