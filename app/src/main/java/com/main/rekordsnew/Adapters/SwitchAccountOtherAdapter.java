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
import com.main.rekordsnew.EventBus.SwitchOtherClicked;
import com.main.rekordsnew.Interface.IonClick;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwitchAccountOtherAdapter extends RecyclerView.Adapter<SwitchAccountOtherAdapter.ViewHolder> {

    Context context;
    List<OtherModel> otherModelList;
    String TYPE;

    public SwitchAccountOtherAdapter(Context context, List<OtherModel> otherModelList, String TYPE) {
        this.context = context;
        this.otherModelList = otherModelList;
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.switch_account_other_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Glide.with(context).load(otherModelList.get(position).getImage()).into(holder.image);
            holder.phone.setText(otherModelList.get(position).getPhone());
            holder.name.setText(otherModelList.get(position).getName());
            holder.setIitemClick((view, position1) -> {
                EventBus.getDefault().postSticky(new SwitchOtherClicked(true,otherModelList.get(position),TYPE));
            });
        }catch (NullPointerException e){
            Log.e("NULLER",e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (otherModelList != null){
            return otherModelList.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView image;
        TextView name,phone;
        IonClick iitemClick;

        public void setIitemClick(IonClick iitemClick) {
            this.iitemClick = iitemClick;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.saoi_image);
            name = itemView.findViewById(R.id.saoi_name);
            phone = itemView.findViewById(R.id.saoi_phone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iitemClick.onClicked(view,getAdapterPosition());
        }
    }
}
