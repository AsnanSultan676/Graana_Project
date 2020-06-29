package com.example.graana.Adaptor;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graana.CatagoryActivity;

import com.example.graana.Model.CatagoryModel;
import com.example.graana.R;

import java.util.List;


public class CatagoryAdaptor extends RecyclerView.Adapter<CatagoryAdaptor.ViewHolder> {


    public CatagoryAdaptor( List<CatagoryModel> catagoryModelList) {
        this.catagoryModelList = catagoryModelList;

    }


    private List<CatagoryModel> catagoryModelList;
    @NonNull
    @Override
    public CatagoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatagoryAdaptor.ViewHolder holder, int position) {

        String icon=catagoryModelList.get(position).getCatagoryIconLink();
        String name=catagoryModelList.get(position).getCatagoryName();
        holder.setCatagory(name);

    }

    @Override
    public int getItemCount() {
        return catagoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView catagoryIcon;
        private TextView catagoryName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catagoryIcon=itemView.findViewById(R.id.catagory_image);
            catagoryName=itemView.findViewById(R.id.catagory_name);

        }
        private void setCatagoryIcon()
        {
            //set Catagory ican here;
        }
        private void setCatagory(final String name)
        {
            catagoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!name.equals("Home")) {
                        Intent intent = new Intent(itemView.getContext(), CatagoryActivity.class);
                        intent.putExtra("CatagoryName", name);
                        itemView.getContext().startActivity(intent);
                    }

                }
            });
        }
    }
}
