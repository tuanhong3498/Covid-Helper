package com.example.covidhelper.ui.checkIn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    List<String> checkInPlace, checkInAddress, checkInTime;
    public MemberAdapter(List<String> checkInTime,List<String> checkInPlace, List<String> checkInAddress) {
        this.checkInTime = checkInTime;
        this.checkInPlace = checkInPlace;
        this.checkInAddress = checkInAddress;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_check_in_record, parent, false);
        return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        holder.check_in_time.setText(checkInTime.get(position));
        holder.check_in_place.setText(checkInPlace.get(position));
        holder.check_in_address.setText(checkInAddress.get(position));
    }

    @Override
    public int getItemCount() {
        return checkInTime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView check_in_time, check_in_place, check_in_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_in_time = itemView.findViewById(R.id.check_in_time);
            check_in_place = itemView.findViewById(R.id.check_in_place);
            check_in_address = itemView.findViewById(R.id.check_in_address);
        }
    }
}
