package com.example.covidhelper.ui.announcement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>
{
    LayoutInflater inflater;
    AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener;

    List<String> announcementTitle, announcementContent, announcementTime;
    List<Integer> announcementImage;

    public AnnouncementAdapter(LayoutInflater inf, List<Integer> announcementImage, List<String> announcementTitle, List<String> announcementContent, List<String> announcementTime, RecyclerviewOnClickListener recyclerviewOnClickListener)
    {
        this.announcementTitle = announcementTitle;
        this.announcementContent = announcementContent;
        this.announcementTime = announcementTime;
        this.announcementImage = announcementImage;

        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
        inflater = inf;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_announcement, parent, false);
        return new AnnouncementViewHolder(view, recyclerviewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.AnnouncementViewHolder holder, int position) {
        holder.announcement_title.setText(announcementTitle.get(position));
        holder.announcement_content.setText(announcementContent.get(position));
        holder.announcement_time.setText(announcementTime.get(position));
        holder.announcement_image.setImageResource(announcementImage.get(position));
    }

    @Override
    public int getItemCount() {
        return announcementTitle.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder
    {
        TextView announcement_title, announcement_content, announcement_time;
        ImageView announcement_image;
        AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener;
        int position;

        public AnnouncementViewHolder(@NonNull View itemView, AnnouncementAdapter.RecyclerviewOnClickListener recyclerviewOnClickListener) {
            super(itemView);

            this.recyclerviewOnClickListener = recyclerviewOnClickListener;

            announcement_title = itemView.findViewById(R.id.announcement_title);
            announcement_content = itemView.findViewById(R.id.announcement_content);
            announcement_time = itemView.findViewById(R.id.announcement_time);
            announcement_image = itemView.findViewById(R.id.announcement_image);

            itemView.setOnClickListener(v ->
                    recyclerviewOnClickListener.recyclerviewClick(position));
        }
    }

    public interface RecyclerviewOnClickListener
    {
        void recyclerviewClick(int position);
    }

}
