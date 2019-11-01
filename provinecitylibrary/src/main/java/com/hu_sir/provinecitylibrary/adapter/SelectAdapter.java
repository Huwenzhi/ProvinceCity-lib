package com.hu_sir.provinecitylibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hu_sir.provinecitylibrary.R;
import com.hu_sir.provinecitylibrary.info.AreaInfo;
import com.hu_sir.provinecitylibrary.info.CityInfo;
import com.hu_sir.provinecitylibrary.info.ProvinceInfo;
import com.hu_sir.provinecitylibrary.info.StreetInfo;
import com.hu_sir.provinecitylibrary.interfaces.MultiItemEntity;
import com.hu_sir.provinecitylibrary.interfaces.OnItemClickListener;

import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter {

    Context context;
    List<MultiItemEntity> provinceInfos;
    OnItemClickListener onItemClickListener;


    public SelectAdapter(Context context, List<MultiItemEntity> provinceInfos) {
        this.context = context;
        this.provinceInfos = provinceInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_name_list, null);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        SelectViewHolder selectViewHolder = (SelectViewHolder) holder;
        MultiItemEntity itemEntity = provinceInfos.get(position);
        if (itemEntity.getItemType() == 0) {
            ProvinceInfo provinceInfo = (ProvinceInfo) itemEntity;
            selectViewHolder.adr.setText(provinceInfo.getName());
        } else if (itemEntity.getItemType() == 1) {
            CityInfo cityInfo = (CityInfo) itemEntity;
            selectViewHolder.adr.setText(cityInfo.getName());
        } else if (itemEntity.getItemType() == 2) {
            AreaInfo areaInfo = (AreaInfo) itemEntity;
            selectViewHolder.adr.setText(areaInfo.getName());
        } else if (itemEntity.getItemType() == 3) {
            StreetInfo streetInfo = (StreetInfo) itemEntity;
            selectViewHolder.adr.setText(streetInfo.getName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(SelectAdapter.this, holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinceInfos.size();
    }

    public void setOnItemClickListerner(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class SelectViewHolder extends RecyclerView.ViewHolder {
        private TextView adr;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);

            adr = itemView.findViewById(R.id.item_tv);
        }
    }
}
