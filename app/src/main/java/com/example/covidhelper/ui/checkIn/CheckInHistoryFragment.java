package com.example.covidhelper.ui.checkIn;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.ui.announcement.AnnouncementAdapter;
import com.example.covidhelper.ui.announcement.AnnouncementViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckInHistoryFragment extends Fragment
{
    RecyclerView recyclerView;
    List<Integer> date;
    LinearLayoutManager layoutManagerGroup;
    GroupAdapter adapterGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check_in_history, container, false);

        recyclerView = root.findViewById(R.id.daily_check_in_history_list);
        date = new ArrayList<>();

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);

        // storeData
        checkInViewModel.getCheckInDate(1).observe(requireActivity(), checkInRecordList -> {
            // Update the cached copy of the words in the adapter.
            for (CheckInRecord checkInRecord : checkInRecordList)
            {
                date.add(checkInRecord.recordDate);
            }

            GroupAdapter groupAdapter = new GroupAdapter(inflater,root.getContext(),date);
            recyclerView.setAdapter(groupAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        });


        return root;
    }
}