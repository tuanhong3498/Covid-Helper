package com.example.covidhelper.ui.announcement;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnouncementAllFragment extends Fragment  implements AnnouncementAdapter.RecyclerviewOnClickListener
{
    View root;
    RecyclerView recyclerView;
    List<String> title,content,time;
    List<Integer> image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_announcement_all, container, false);

        recyclerView = root.findViewById(R.id.announcement_list);
        title = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();
        image = new ArrayList<Integer>(){
            {
                add(getDrawable("dummy_announcement_task"));
                add(getDrawable("dummy_announcement_information"));
                add(getDrawable("dummy_announcement_fake_news1"));
                add(getDrawable("dummy_announcement_fake_news2"));
                add(getDrawable("dummy_announcement_fake_news3"));
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
        List<String> title_task,title_information,title_fake_news,content_task,content_information,content_fake_news,time_task,time_information,time_fake_news;
        title_task = Arrays.asList(getResources().getStringArray(R.array.task_announcement_title));
        title_information = Arrays.asList(getResources().getStringArray(R.array.information_announcement_title));
        title_fake_news = Arrays.asList(getResources().getStringArray(R.array.fake_news_announcement_title));
        content_task = Arrays.asList(getResources().getStringArray(R.array.task_announcement_content));
        content_information = Arrays.asList(getResources().getStringArray(R.array.information_announcement_content));
        content_fake_news = Arrays.asList(getResources().getStringArray(R.array.fake_news_announcement_content));
        time_task = Arrays.asList(getResources().getStringArray(R.array.task_announcement_time));
        time_information = Arrays.asList(getResources().getStringArray(R.array.information_announcement_time));
        time_fake_news = Arrays.asList(getResources().getStringArray(R.array.fake_news_announcement_time));

        title.addAll(title_task);
        title.addAll(title_information);
        title.addAll(title_fake_news);
        content.addAll(content_task);
        content.addAll(content_information);
        content.addAll(content_fake_news);
        time.addAll(time_task);
        time.addAll(time_information);
        time.addAll(time_fake_news);
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