package com.example.vitstop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitstop.Features.Address;
import com.example.vitstop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context applicationContext;
    List<Address> mAddressList;
    private RadioButton mSelectedRadioButton;
    SelectedAddress selectedAddress;
    public AddressAdapter(Context applicationContext, List<Address> mAddressList, SelectedAddress selectedAddress){
        this.applicationContext = applicationContext;
        this.mAddressList = mAddressList;
        this.selectedAddress = selectedAddress;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.single_address_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
            holder.mAddress.setText(mAddressList.get(position).getAddress());
            holder.mRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(Address address:mAddressList){ address.setSelected(false);}
                    mAddressList.get(position).setSelected(true);

                    if(mSelectedRadioButton!=null){
                        mSelectedRadioButton.setChecked(false);
                    }
                    mSelectedRadioButton = (RadioButton) view;
                    mSelectedRadioButton.setChecked(true);
                    selectedAddress.setAddress(mAddressList.get(position).getAddress());
                }

            });

            holder.mRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        Toast.makeText(applicationContext,"checked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mAddress;
        private RadioButton mRadio;
        public ViewHolder(View itemView) {

            super(itemView);
            mAddress = itemView.findViewById(R.id.address);
            mRadio = itemView.findViewById(R.id.select_btn);
        }
    }


    public interface SelectedAddress{
        public void setAddress(String s);
    }
}
