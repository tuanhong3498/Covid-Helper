package com.example.covidhelper.ui.checkIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.announcement.AnnouncementAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckInFragment extends Fragment implements RecentlyCheckInListAdapter.RecyclerviewOnClickListener
{
    RecyclerView recyclerView;
    List<String> place,address,time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_check_in, container, false);

        recyclerView = root.findViewById(R.id.all_announcements_recyclerview);
        place = new ArrayList<>();
        address = new ArrayList<>();
        time = new ArrayList<>();

        storeDataInArrays();

        RecentlyCheckInListAdapter recentlyCheckInListAdapter = new RecentlyCheckInListAdapter(inflater, time, place, address,this);
        recyclerView.setAdapter(recentlyCheckInListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }
    void storeDataInArrays()
    {
        time = Arrays.asList(getResources().getStringArray(R.array.check_in_time));
        place = Arrays.asList(getResources().getStringArray(R.array.check_in_place));
        address = Arrays.asList(getResources().getStringArray(R.array.check_in_address));
    }

    @Override
    public void recyclerviewClick(int position) {

    }
}