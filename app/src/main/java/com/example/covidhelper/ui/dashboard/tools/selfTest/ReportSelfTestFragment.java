package com.example.covidhelper.ui.dashboard.tools.selfTest;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.example.covidhelper.database.table.SelfTestResult;
import com.example.covidhelper.ui.dashboard.tools.vaccine.VaccinationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ReportSelfTestFragment extends Fragment
{
    // internal variable
    private int userID;

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

        SharedPreferences sp = sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("userID", -1);

        positiveCard = root.findViewById(R.id.selfTest_positive_card);
        negativeCard = root.findViewById(R.id.selfTest_negative_card);
        invalidCard = root.findViewById(R.id.selfTest_invalid_card);

        buttonSubmit = root.findViewById(R.id.selfTest_button_submit);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        mViewModel = factory.create(ReportSelfTestViewModel.class);

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
            final String result;
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

            // create a confirmation dialog
            new MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Make sure your selection is correct.  it cannot be changed.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Confirm", (dialog, which) ->
                    {
                        // save the result
                        mViewModel.saveSelfTestResult(new SelfTestResult(userID, result, unixTimeSecond));

                        switch (result)
                        {
                            case "positive":
                                mViewModel.updateRiskStatus(userID, "High Risk");
                                new MaterialAlertDialogBuilder(requireContext())
                                        .setTitle("Important message")
                                        .setMessage("You are now high risk individual. Please self-quarantine until you are contacted by health authority for further instruction.")
                                        .setPositiveButton("Got it", null)
                                        .show();
                                break;
                            case "negative":
                                try
                                {
                                    String riskStatus = mViewModel.getRiskStatus(userID);
                                    if(!riskStatus.equals("Low Risk"))
                                    {
                                        new MaterialAlertDialogBuilder(requireContext())
                                                .setTitle("Important message")
                                                .setMessage("Your risk status will remain as " + riskStatus + ". You are advised to do a swap test to return to Low Risk.")
                                                .setPositiveButton("Got it", null)
                                                .show();
                                    }
                                }
                                catch (ExecutionException | InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                break;
                            case "invalid":
                                new MaterialAlertDialogBuilder(requireContext())
                                        .setTitle("Important message")
                                        .setMessage("You are advised to re-conduct the test or visit your nearest hospital/clinic to do a swab test")
                                        .setPositiveButton("Got it", null)
                                        .show();
                        }

                        // return to dashboard
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(ReportSelfTestFragmentDirections.actionReportSelfTestFragmentToDashboardFragment());
                    })
                    .show();
        });
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