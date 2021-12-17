package com.example.covidhelper.ui.checkIn;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CheckInRecordDetails;
import com.example.covidhelper.ui.announcement.AnnouncementAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    LayoutInflater inflater;
    private Context context;
    List<String> checkInDate;

    GroupAdapter(LayoutInflater inf, Context context, List<String> checkInDate){
        this.inflater = inf;
        this.context = context;
        this.checkInDate = checkInDate;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_daily_check_in, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        long dateLong = (long)(checkInDate.get(position));
//        String dateStr = dateFormat.format(dateLong*100000000);
        holder.tvDate.setText(checkInDate.get(position));

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance((Application) context.getApplicationContext());
        CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);

        // storeData
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        checkInViewModel.getDailyCheckInDate(1,checkInDate.get(position)).observe((LifecycleOwner) context, dailyCheckInRecordList -> {
            List<String> checkInPlace, checkInAddress, checkInTime;
            checkInTime = new ArrayList<>();
            checkInPlace = new ArrayList<>();
            checkInAddress = new ArrayList<>();

            // Update the cached copy of the words in the adapter.
            for (CheckInRecordDetails checkInRecord : dailyCheckInRecordList)
            {
//                long timeLong = dateLong*100000000+(long)checkInRecord.recordTime;
//                String timeStr = timeFormat.format(timeLong);
//                String[] timeSplit = timeStr.split(" ");

                checkInTime.add(getTime(checkInRecord.recordTime));
                checkInPlace.add(checkInRecord.recordPlace);
                checkInAddress.add(checkInRecord.recordAddress);
            }

            MemberAdapter memberAdapter = new MemberAdapter(checkInTime,checkInPlace,checkInAddress);
            LinearLayoutManager layoutManagerMember = new LinearLayoutManager(context);
            holder.rvRecord.setLayoutManager(layoutManagerMember);
            holder.rvRecord.setAdapter(memberAdapter);
        });

    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "EEEE, dd MMM yyyy");
    }

    private String getTime(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "hh:mm aa");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        return checkInDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        RecyclerView rvRecord;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.check_in_time);
            rvRecord = itemView.findViewById(R.id.check_in_list);
        }
    }

}
