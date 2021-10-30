package com.example.covidhelper.ui.dashboard.tools.selfTest;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.google.android.material.card.MaterialCardView;

public class ReportSelfTestFragment extends Fragment
{

    private ReportSelfTestViewModel mViewModel;

    private MaterialCardView positiveCard;
    private MaterialCardView negativeCard;
    private MaterialCardView invalidCard;

    public static ReportSelfTestFragment newInstance()
    {
        return new ReportSelfTestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_report_self_test, container, false);

        positiveCard = root.findViewById(R.id.selfTest_positive_card);
        negativeCard = root.findViewById(R.id.selfTest_negative_card);
        invalidCard = root.findViewById(R.id.selfTest_invalid_card);

        setCardOnClickListener(positiveCard);
        setCardOnClickListener(negativeCard);
        setCardOnClickListener(invalidCard);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReportSelfTestViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setCardOnClickListener(MaterialCardView card)
    {
        card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                card.setChecked(!card.isChecked());
                if (card != positiveCard)
                    positiveCard.setChecked(false);
                if (card != negativeCard)
                    negativeCard.setChecked(false);
                if (card != invalidCard)
                    invalidCard.setChecked(false);
            }
        });
    }

}