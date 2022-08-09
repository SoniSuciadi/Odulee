package com.sonisuciadi.odulee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonisuciadi.odulee.Model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.viewHolder> {
    List<Schedule> mdata;
    private OnItemClickListener onItemClickListener;

    public ScheduleAdapter(List<Schedule> mdata, OnItemClickListener onItemClickListener) {
        this.mdata = mdata;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_schedule,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(mdata.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView judul,tempat, tanggal, deskripsi,id,pembuat;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            judul=itemView.findViewById(R.id.tv_judul);
            tempat=itemView.findViewById(R.id.tv_tempat);
            tanggal=itemView.findViewById(R.id.tv_tanggal);
            deskripsi=itemView.findViewById(R.id.tv_deskripsi);
            id=itemView.findViewById(R.id.tv_id);
            pembuat=itemView.findViewById(R.id.tv_creator);

        }
        public void bind(Schedule item,OnItemClickListener onItemClickListener){
            judul.setText(item.getJudul());
            tempat.setText(item.getTempat());
            tanggal.setText(item.getTanggal());
            deskripsi.setText(item.getDeskripsi());
            id.setText(String.valueOf(item.getId()));
            pembuat.setText("Untuk: "+item.getUser());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item,getPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Schedule item,int position );
    }
}
