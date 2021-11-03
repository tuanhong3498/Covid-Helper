package com.example.covidhelper.ui.dashboard.tools.SOP;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.covidhelper.R;

public class SopFragment extends Fragment
{

    AutoCompleteTextView autoCompleteTextViewState;

    private SopViewModel mViewModel;

    public static SopFragment newInstance()
    {
        return new SopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_sop, container, false);

        autoCompleteTextViewState = root.findViewById(R.id.SOP_autoTextView_state);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SopViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, states);
        autoCompleteTextViewState.setAdapter(arrayAdapter);
    }

}