package com.example.covidhelper.ui.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.example.covidhelper.model.Faq;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment
{

    RecyclerView recyclerViewFaq;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerViewFaq = (RecyclerView) root.findViewById(R.id.dashboard_faq_recycler_view);

        addFaqList();

        return root;
    }

    private void addFaqList()
    {
        ArrayList<Faq> faqList = new ArrayList<>();

        for (int i = 0; i < 10; ++i)
        {
            faqList.add(new Faq("Do we need to wear mask?", "Sure"));
        }

        loadFaqItems(recyclerViewFaq, faqList);
    }

    private void loadFaqItems(RecyclerView recyclerView, ArrayList<Faq> faqList)
    {
        FaqAdapter faqAdapter = new FaqAdapter(faqList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);
    }
}