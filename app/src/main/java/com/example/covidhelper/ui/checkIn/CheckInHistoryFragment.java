package com.example.covidhelper.ui.checkIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckInHistoryFragment extends Fragment
{
    RecyclerView recyclerView;
    List<String> date;
    LinearLayoutManager layoutManagerGroup;
    GroupAdapter adapterGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check_in_history, container, false);

        recyclerView = root.findViewById(R.id.daily_check_in_history_list);
        date = new ArrayList<>();

        storeDataInArrays();

        GroupAdapter groupAdapter = new GroupAdapter(inflater,root.getContext(),date);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }
    void storeDataInArrays()
    {
        date = Arrays.asList(getResources().getStringArray(R.array.check_in_date));
    }
}