package com.example.covidhelper.ui.checkIn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.announcement.AnnouncementAdapter;

public class RecentlyCheckInListAdapter extends RecyclerView.Adapter<RecentlyCheckInListAdapter.CheckInRecordViewHolder>
{
    LayoutInflater inflater;
    RecyclerviewOnClickListener recyclerviewOnClickListener;

    List<String> checkInPlace, checkInAddress, checkInTime;

    public RecentlyCheckInListAdapter(LayoutInflater inf, List<String> checkInTime,List<String> checkInPlace, List<String> checkInAddress, RecentlyCheckInListAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener)
    {
        this.checkInTime = checkInTime;
        this.checkInPlace = checkInPlace;
        this.checkInAddress = checkInAddress;

        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
        inflater = inf;
    }

    @NonNull
    @Override
    public CheckInRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.view_check_in_record, parent, false);
        return new CheckInRecordViewHolder(view,recyclerviewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInRecordViewHolder holder, int position)
    {
        holder.check_in_time.setText(checkInTime.get(position));
        holder.check_in_place.setText(checkInPlace.get(position));
        holder.check_in_address.setText(checkInAddress.get(position));
    }

    @Override
    public int getItemCount()
    {
        return checkInTime.size();
    }

    public static class CheckInRecordViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout item;
        TextView check_in_time, check_in_place, check_in_address;
        RecyclerviewOnClickListener recyclerviewOnClickListener;
        int position;

        public CheckInRecordViewHolder(@NonNull View itemView, RecyclerviewOnClickListener recyclerviewOnClickListener)
        {
            super(itemView);
            this.recyclerviewOnClickListener = recyclerviewOnClickListener;

            check_in_time = itemView.findViewById(R.id.check_in_time);
            check_in_place = itemView.findViewById(R.id.check_in_place);
            check_in_address = itemView.findViewById(R.id.check_in_address);

            itemView.setOnClickListener(v ->
                    recyclerviewOnClickListener.recyclerviewClick(position));
        }
    }

    public interface RecyclerviewOnClickListener
    {
        void recyclerviewClick(int position);
    }
}
