package com.example.covidhelper.ui.dashboard.tools.selfTest;

import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

public class ReportSelfTestFragment extends Fragment
{

    private ReportSelfTestViewModel mViewModel;

    // UI elements
    private MaterialCardView positiveCard;
    private MaterialCardView negativeCard;
    private MaterialCardView invalidCard;

    private MaterialButton buttonSubmit;

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

        buttonSubmit = root.findViewById(R.id.selfTest_button_submit);

        setCardOnClickListener(positiveCard);
        setCardOnClickListener(negativeCard);
        setCardOnClickListener(invalidCard);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        buttonSubmit.setOnClickListener(v ->
        {
            String result = "";
            if(positiveCard.isChecked())
                result = "positive";
            else if(negativeCard.isChecked())
                result = "negative";
            else if(invalidCard.isChecked())
                result = "invalid";
            else
            {
                new MaterialAlertDialogBuilder(requireContext())
                        .setMessage("Please select your result before submission")
                        .setPositiveButton("Got it", null)
                        .show();
                return;
            }

            Date currentTime = new java.util.Date();
            long unixTimeSecond = currentTime.getTime()/1000L;
            // TODO: set currentTime and result to DB

            if (result.equals("invalid"))
            {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Important message")
                        .setMessage("You are advised to re-conduct the test or visit your nearest hospital/clinic to do a swab test")
                        .setPositiveButton("Got it", null)
                        .show();
            }

            // return to dashboard
            NavController navController = Navigation.findNavController(view);
            navController.navigate(ReportSelfTestFragmentDirections.actionReportSelfTestFragmentToDashboardFragment());

        });
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
                // flip the checked status of the card
                // if checked -> uncheck it or vice versa
                card.setChecked(!card.isChecked());
                // uncheck all other card
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