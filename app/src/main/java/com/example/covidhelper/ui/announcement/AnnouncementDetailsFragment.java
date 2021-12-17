package com.example.covidhelper.ui.announcement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidhelper.R;

public class AnnouncementDetailsFragment extends Fragment {
    TextView announcement_title, announcement_time, announcement_details;
    ImageView announcement_image;
    Integer image;
    String title, time, details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_announcement_details, container, false);

        AnnouncementDetailsFragmentArgs args = AnnouncementDetailsFragmentArgs.fromBundle(getArguments());
        image = args.getImage();
        title = args.getTitle();
        time = args.getTime();
        details = args.getDetails();

        announcement_title = root.findViewById(R.id.announcement_title);
        announcement_time = root.findViewById(R.id.announcement_time);
        announcement_details = root.findViewById(R.id.announcement_details);
        announcement_image = root.findViewById(R.id.announcement_image);
        announcement_title.setText(title);
        announcement_time.setText(time);
        announcement_details.setText(details);
        announcement_image.setImageResource(image);

        return root;
    }
}