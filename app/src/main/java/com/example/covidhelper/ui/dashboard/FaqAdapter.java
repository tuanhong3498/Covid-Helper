package com.example.covidhelper.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.model.Faq;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder>
{
    private List<Faq> faqList;

    private static int currentPosition = -1;

    public FaqAdapter(List<Faq> faqList)
    {
        this.faqList = faqList;
    }


    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_layout, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position)
    {
        Faq faq = faqList.get(position);
        holder.textViewQuestion.setText(faq.getQuestion());
        holder.textViewAnswer.setText(faq.getAnswer());

        setAnswerVisibility(currentPosition == position, holder);

        holder.linearLayoutHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (currentPosition != holder.getAdapterPosition())
                    currentPosition = holder.getAdapterPosition();
                else currentPosition = -1;

                notifyDataSetChanged();
            }
        });
    }

    private void setAnswerVisibility(boolean visible, FaqViewHolder holder)
    {
        if (visible)
        {
            holder.textViewAnswer.setVisibility(View.VISIBLE);
            holder.imageViewEndTriangle.setRotation(180);
        }
        else
        {
            holder.textViewAnswer.setVisibility(View.GONE);
            holder.imageViewEndTriangle.setRotation(0);
        }
    }

    @Override
    public int getItemCount()
    {
        return faqList.size();
    }

    static class FaqViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewQuestion;
        TextView textViewAnswer;
        ImageView imageViewEndTriangle;
        LinearLayout linearLayoutHeader;

        public FaqViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewQuestion = (TextView) itemView.findViewById(R.id.faq_question);
            textViewAnswer = (TextView) itemView.findViewById(R.id.faq_answer);
            imageViewEndTriangle = (ImageView) itemView.findViewById(R.id.faq_EndTriangle);

            linearLayoutHeader = (LinearLayout) itemView.findViewById(R.id.faq_header);
        }
    }
}
