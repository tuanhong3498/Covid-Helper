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

public class AnnouncementTaskFragment extends Fragment implements AnnouncementAdapter.RecyclerviewOnClickListener
{
    RecyclerView recyclerView;
    List<String> title,content,time;
    List<Integer> image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_announcement_task, container, false);

        recyclerView = root.findViewById(R.id.announcement_list);
        title = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();
        image = new ArrayList<Integer>(){
            {
                add(getDrawable("dummy_announcement_task"));
            }
        };

        storeDataInArrays();

        AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(inflater, image, title, content, time,this);
        recyclerView.setAdapter(announcementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    void storeDataInArrays()
    {
        title = Arrays.asList(getResources().getStringArray(R.array.task_announcement_title));
        content = Arrays.asList(getResources().getStringArray(R.array.task_announcement_content));
        time = Arrays.asList(getResources().getStringArray(R.array.task_announcement_time));
    }
    private int getDrawable(String drawableName)
    {
        int drawableResource = 0;
        if (getContext() != null)
            drawableResource = getContext().getResources().getIdentifier(drawableName, "drawable", this.getContext().getPackageName());
        return drawableResource;
    }

    @Override
    public void recyclerviewClick(int position) {

    }
}