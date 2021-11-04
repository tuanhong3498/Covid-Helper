package com.example.covidhelper.ui.announcement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.checkIn.RecentlyCheckInListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>
{
    LayoutInflater inflater;
    AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener;

    List<String> announcementTitle, announcementContent, announcementTime;

    public AnnouncementAdapter(LayoutInflater inf, List<String> announcementTitle, List<String> announcementContent, List<String> announcementTime, RecyclerviewOnClickListener recyclerviewOnClickListener)
    {
        this.announcementTitle = announcementTitle;
        this.announcementContent = announcementContent;
        this.announcementTime = announcementTime;

        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
        inflater = inf;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_announcement, parent, false);
        return new AnnouncementAdapter.AnnouncementViewHolder(view,recyclerviewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.AnnouncementViewHolder holder, int position) {
        holder.announcement_title.setText(announcementTitle.get(position));
        holder.announcement_content.setText(announcementContent.get(position));
        holder.announcement_time.setText(announcementTime.get(position));
    }

    @Override
    public int getItemCount() {
        return announcementTitle.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout item;
        TextView announcement_title, announcement_content, announcement_time;
        AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener;
        int position;

        public AnnouncementViewHolder(@NonNull View itemView, AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener) {
            super(itemView);

            this.recyclerviewOnClickListener = recyclerviewOnClickListener;

            announcement_title = itemView.findViewById(R.id.announcement_title);
            announcement_content = itemView.findViewById(R.id.announcement_content);
            announcement_time = itemView.findViewById(R.id.announcement_time);

            itemView.setOnClickListener(v ->
                    recyclerviewOnClickListener.recyclerviewClick(position));
        }
    }

    public interface RecyclerviewOnClickListener
    {
        void recyclerviewClick(int position);
    }

}
