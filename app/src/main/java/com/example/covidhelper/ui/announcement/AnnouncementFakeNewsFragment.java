package com.example.covidhelper.ui.announcement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.Announcement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnnouncementFakeNewsFragment extends Fragment implements AnnouncementAdapter.RecyclerviewOnClickListener
{

    View root;
    RecyclerView recyclerView;
    List<String> title,content,time;
    List<Integer> image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_announcement_fake_news, container, false);
        SharedPreferences sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        recyclerView = root.findViewById(R.id.announcement_list);
        title = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();
        image = new ArrayList<>();
//        {
//            {
//                add(getDrawable("dummy_announcement_fake_news1"));
//                add(getDrawable("dummy_announcement_fake_news2"));
//                add(getDrawable("dummy_announcement_fake_news3"));
//            }
//        };

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        AnnouncementViewModel announcementViewModel = factory.create(AnnouncementViewModel.class);

        // storeData
        announcementViewModel.getFakeNewsAnnouncement(sp.getInt("userID", -1)).observe(requireActivity(), taskAnnouncementList -> {
            // Update the cached copy of the words in the adapter.
            for (Announcement announcement : taskAnnouncementList)
            {
                title.add(announcement.announcementTitle);
                content.add(announcement.announcementContent);
                time.add(getDate(announcement.announcementTime));
                image.add(getDrawable(announcement.announcementImage));
            }
//            System.out.println("查找失败"+title);

            AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(inflater, image, title, content, time,this);
            recyclerView.setAdapter(announcementAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        });


        return root;
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

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "dd MMM yyyy, hh:mm aa");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }
}