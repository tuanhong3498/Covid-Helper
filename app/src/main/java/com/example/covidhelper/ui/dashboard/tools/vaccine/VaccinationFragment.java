package com.example.covidhelper.ui.dashboard.tools.vaccine;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.covidhelper.R;

import java.lang.reflect.Array;

public class VaccinationFragment extends Fragment
{

    AutoCompleteTextView autoCompleteTextViewState;

    private VaccinationViewModel mViewModel;

    public static VaccinationFragment newInstance()
    {
        return new VaccinationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_vaccination, container, false);

        autoCompleteTextViewState = root.findViewById(R.id.vaccine_autoTextView_state);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VaccinationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, states);
        autoCompleteTextViewState.setAdapter(arrayAdapter);
    }
}