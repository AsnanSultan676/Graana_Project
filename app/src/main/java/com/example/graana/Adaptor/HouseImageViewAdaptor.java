package com.example.graana.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graana.Model.HouseImageView;
import com.example.graana.R;

import java.util.List;

public class HouseImageViewAdaptor extends RecyclerView.Adapter<HouseImageViewAdaptor.ViewHolder>{

    public HouseImageViewAdaptor(Context context, List<HouseImageView> imageViewList)
    {
        this.context=context;
        this.imageViewList=imageViewList;
    }
    private Context context;
    private List<HouseImageView> imageViewList;
    @NonNull
    @Override
    public HouseImageViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.house_image_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(imageViewList.get(position).getImagesUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imvImagesOfSelectHouse);

        }
    }



}
