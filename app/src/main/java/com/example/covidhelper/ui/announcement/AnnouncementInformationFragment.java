package com.example.covidhelper.ui.announcement;

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

public class AnnouncementInformationFragment extends Fragment implements AnnouncementAdapter.RecyclerviewOnClickListener
{

    RecyclerView recyclerView;
    List<String> title,content,time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_announcement_information, container, false);

        recyclerView = root.findViewById(R.id.announcement_list);
        title = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();

        storeDataInArrays();

        AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(inflater, title, content, time,this);
        recyclerView.setAdapter(announcementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    void storeDataInArrays()
    {
        title = Arrays.asList(getResources().getStringArray(R.array.information_announcement_title));
        content = Arrays.asList(getResources().getStringArray(R.array.information_announcement_content));
        time = Arrays.asList(getResources().getStringArray(R.array.information_announcement_time));
    }

    @Override
    public void recyclerviewClick(int position) {

    }
}