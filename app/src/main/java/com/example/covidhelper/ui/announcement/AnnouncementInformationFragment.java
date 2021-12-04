package com.example.covidhelper.ui.announcement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.Announcement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnouncementInformationFragment extends Fragment implements AnnouncementAdapter.RecyclerviewOnClickListener
{

    View root;
    RecyclerView recyclerView;
    List<String> title,content,time;
    List<Integer> image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_announcement_information, container, false);

        recyclerView = root.findViewById(R.id.announcement_list);
        title = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();
        image = new ArrayList<Integer>(){
            {
                add(getDrawable("dummy_announcement_information"));
            }
        };

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        AnnouncementViewModel announcementViewModel = factory.create(AnnouncementViewModel.class);

        // storeData
        announcementViewModel.getInformationAnnouncement(1).observe(requireActivity(), taskAnnouncementList -> {
            // Update the cached copy of the words in the adapter.
            for (Announcement announcement : taskAnnouncementList)
            {
                title.add(announcement.announcementTitle);
                content.add(announcement.announcementContent);
                time.add(Integer.toString(announcement.announcementTime));
            }
            System.out.println("查找失败"+title);

            AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(inflater, image, title, content, time,this);
            recyclerView.setAdapter(announcementAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        });

        return root;
    }

    void storeDataInArrays()
    {
        title = Arrays.asList(getResources().getStringArray(R.array.information_announcement_title));
        content = Arrays.asList(getResources().getStringArray(R.array.information_announcement_content));
        time = Arrays.asList(getResources().getStringArray(R.array.information_announcement_time));
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
        NavController navController = Navigation.findNavController(root);
        AnnouncementAllFragmentDirections.ActionAnnouncementToAnnouncementDetails action = AnnouncementAllFragmentDirections.actionAnnouncementToAnnouncementDetails(image.get(position), title.get(position),time.get(position),content.get(position));

        navController.navigate(action);
    }
}