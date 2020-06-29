package com.example.graana.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graana.HouseDetailsActivity;
import com.example.graana.Model.House;
import com.example.graana.R;

import java.util.List;

public class HouseAdaptor extends RecyclerView.Adapter<HouseAdaptor.ViewHolder> {

    private List<House> houses;

    public HouseAdaptor(List<House> houses)
    {
        this.houses=houses;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.house_layout,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull HouseAdaptor.ViewHolder holder, int position) {

        String name=houses.get(position).getOwnerName();
        String address=houses.get(position).getAddress();
        String phone=houses.get(position).getOwnerPhone();
        String rent=houses.get(position).getRent();
        holder.setHouse(name,address,phone,rent);

    }

    @Override
    public int getItemCount() {
        return houses.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView  tvOwnerName;
        TextView tvAddress;
        TextView tvOwnerPhone;
        TextView tvRent;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvOwnerName=itemView.findViewById(R.id.tvHouseLayout_HouseOwner);
            tvAddress=itemView.findViewById(R.id.tvHouseLayout_HouseAddress);
            tvOwnerPhone=itemView.findViewById(R.id.tvHouseLayout_HouseOwnerNumber);
            tvRent=itemView.findViewById(R.id.tvHouseLayout_HouseRent);
        }
        void setHouse(final String name, String address, String phone, String rent)
        {
            tvOwnerName.setText(name);
            tvAddress.setText(address);
            tvOwnerPhone.setText(phone);
            tvRent.setText(rent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),HouseDetailsActivity.class);
                    itemView.getContext().startActivity(intent);

                    Toast.makeText(v.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
