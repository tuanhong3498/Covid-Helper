package com.example.covidhelper.ui.checkIn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.announcement.AnnouncementAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    LayoutInflater inflater;
    private Context context;
    List<String> checkInDate, checkInTime, checkInPlace, checkInAddress;

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
        holder.tvDate.setText(checkInDate.get(position));

        checkInTime = new ArrayList<>();
        checkInPlace = new ArrayList<>();
        checkInAddress = new ArrayList<>();

        storeDataInArrays();

        MemberAdapter memberAdapter = new MemberAdapter(checkInTime,checkInPlace,checkInAddress);
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(context);
        holder.rvRecord.setLayoutManager(layoutManagerMember);
        holder.rvRecord.setAdapter(memberAdapter);
    }

    void storeDataInArrays()
    {
        checkInTime = Arrays.asList(context.getResources().getStringArray(R.array.check_in_time));
        checkInPlace = Arrays.asList(context.getResources().getStringArray(R.array.check_in_place));
        checkInAddress = Arrays.asList(context.getResources().getStringArray(R.array.check_in_address));
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
