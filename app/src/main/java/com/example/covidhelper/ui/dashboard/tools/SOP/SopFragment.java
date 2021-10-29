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
import android.widget.Spinner;

import com.example.covidhelper.R;

public class SopFragment extends Fragment implements AdapterView.OnItemSelectedListener
{

    private SopViewModel mViewModel;

    private Spinner stateSpinner;

    public static SopFragment newInstance()
    {
        return new SopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_sop, container, false);

        stateSpinner = root.findViewById(R.id.SOP_state_spinner);
        stateSpinner.setOnItemSelectedListener(this);

        initializeSpinner();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SopViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initializeSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.states, android.R.layout.simple_spinner_item);

        // Specify the layout to use for the drop down
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        stateSpinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // handler when a spinner item selected
        Object selection = parent.getItemAtPosition(position);
        Log.i("spinner", selection.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}