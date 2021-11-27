package com.example.covidhelper.ui.dashboard.tools.vaccine;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidhelper.R;
import com.example.covidhelper.model.VaccinationStage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class VaccinationFragment extends Fragment
{
    // TODO: replace it using data from DB
    // hardcode variables
    String stage = "Dose 1";

    // UI elements
    // Status icons
    private ImageView iconLeft;
    private ImageView iconCenter;
    private ImageView iconRight;
    private MaterialCardView iconLeftHolder;
    private MaterialCardView iconRightHolder;
    private View iconLeftBar;
    private View iconRightBar;
    // Registration Form
    private MaterialCardView registrationForm;
    private TextInputEditText textInputName;
    private TextInputEditText textInputIC;
    private AutoCompleteTextView stateDropDown;
    private TextInputEditText textInputPostcode;
    private MaterialButton buttonSubmitRegistrationFrom;
    // Registration info
    private MaterialCardView registrationInfoCard;
    private TextView textViewName;
    private TextView textViewICPassport;
    private TextView textViewCurrentStayingState;
    private TextView textViewPostcode;
    private MaterialButton buttonChangeInfo;
    // Appointment info
    private MaterialCardView appointmentCard;
    private TextView textViewLocation;
    private TextView textViewAppointmentDate;
    private TextView textViewAppointmentTime;
    private MaterialButton buttonChangeDate;
    private MaterialButton buttonConfirmAppointment;
    private TextView textViewAppointmentConfirmed;
    // Wait 14 days message
    private TextView waitMessage;

    // private variable
    private VaccinationStage vaccinationStage;
    private String[] states;

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

        iconLeft = root.findViewById(R.id.vaccine_status_icon_previous);
        iconCenter = root.findViewById(R.id.vaccine_status_icon_current);
        iconRight = root.findViewById(R.id.vaccine_status_icon_next);
        iconLeftHolder = root.findViewById(R.id.vaccine_status_icon_previous_holder);
        iconRightHolder = root.findViewById(R.id.vaccine_status_icon_next_holder);
        iconLeftBar = root.findViewById(R.id.vaccine_status_icon_previous_bar);
        iconRightBar = root.findViewById(R.id.vaccine_status_icon_next_bar);

        // Status title
        TextView textViewTitle = root.findViewById(R.id.vaccine_status_title_current);

        registrationForm = root.findViewById(R.id.vaccine_registration_form);
        textInputName = root.findViewById(R.id.vaccine_text_box_name);
        textInputIC = root.findViewById(R.id.vaccine_text_box_ic);
        stateDropDown = root.findViewById(R.id.vaccine_autoTextView_state);
        textInputPostcode = root.findViewById(R.id.vaccine_text_box_postcode);
        buttonSubmitRegistrationFrom = root.findViewById(R.id.vaccine_button_submit);

        registrationInfoCard = root.findViewById(R.id.vaccine_registration_information);
        textViewName = root.findViewById(R.id.vaccine_textView_name);
        textViewICPassport = root.findViewById(R.id.vaccine_textView_ic_passport);
        textViewCurrentStayingState = root.findViewById(R.id.vaccine_textView_state);
        textViewPostcode = root.findViewById(R.id.vaccine_textView_postcode);
        buttonChangeInfo = root.findViewById(R.id.vaccine_button_changeInfo);

        appointmentCard = root.findViewById(R.id.vaccine_appointment_information);
        textViewLocation = root.findViewById(R.id.vaccine_appointment_location);
        textViewAppointmentDate = root.findViewById(R.id.vaccine_appointment_date);
        textViewAppointmentTime = root.findViewById(R.id.vaccine_appointment_time);
        buttonChangeDate= root.findViewById(R.id.vaccine_button_changeDate);
        buttonConfirmAppointment = root.findViewById(R.id.vaccine_button_confirmAppointment);
        textViewAppointmentConfirmed = root.findViewById(R.id.vaccine_appointment_confirmed_message);

        waitMessage = root.findViewById(R.id.vaccine_wait_14days_message);

        vaccinationStage = VaccinationStage.fromString(stage);
        states = getResources().getStringArray(R.array.states);

        initializeStatusIcons();
        textViewTitle.setText(vaccinationStage.getTitle());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        initializeContent();
        super.onViewCreated(view, savedInstanceState);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, states);
        stateDropDown.setAdapter(arrayAdapter);
    }

    private void initializeContent()
    {
        switch(vaccinationStage)
        {
            case REGISTER:
                initializeRegistrationCard();
                break;
            case DOSE_1:
            case DOSE_2:
                showAppointmentInfo();
                break;
            case WAIT_14_DAYS:
                waitMessage.setVisibility(View.VISIBLE);
                break;
            case COMPLETED:
                // TODO: show digital vaccine certificate
        }
    }

    private void initializeRegistrationCard()
    {
        boolean registered;
        // TODO: initialize variable using info from DB
        registered = false;
        if (!registered)
            initializeRegistrationForm();
        else
            showRegisteredInfo();
    }

    private void initializeRegistrationForm()
    {
        // prefill some of the field
        // TODO: get the info from DB
        String userName = "Chua Tuan Hong";
        String icPassport = "940329025831";
        String state = states[0];

        textInputName.setText(userName);
        textInputIC.setText(icPassport);
//        stateDropDown.setText(state);

        registrationForm.setVisibility(View.VISIBLE);

        buttonSubmitRegistrationFrom.setOnClickListener(v ->
        {
            // check if any field is left empty
            if(checkIsFieldEmpty(textInputName, "Please enter your name") ||
                checkIsFieldEmpty(textInputIC, "Please provide your IC or passport number") ||
                checkIsFieldEmpty(stateDropDown, "Please select the state you currently stay") ||
                checkIsFieldEmpty(textInputPostcode, "Please enter the postcode of your current staying location"))
                return;

            // TODO: store the info into DB

            registrationForm.setVisibility(View.GONE);
            showRegisteredInfo();
        });
    }

    private boolean checkIsFieldEmpty(EditText editTextField, String errorMessage)
    {
        if(editTextField.getText().toString().equals(""))
        {
            // note: errorEnabled should be set to true for textInputLayout
            editTextField.setError(errorMessage);
            return true;
        }
        return false;
    }

    private void showRegisteredInfo()
    {
        // TODO: get the info from DB
        String userName = "Chua Tuan Hong";
        String icPassport = "940329025831";
        String state = states[0];
        String postcode = "43900";

        textViewName.setText(userName);
        textViewICPassport.setText(icPassport);
        textViewCurrentStayingState.setText(state);
        textViewPostcode.setText(postcode);

        registrationInfoCard.setVisibility(View.VISIBLE);

        buttonChangeInfo.setOnClickListener(v ->
        {
            registrationInfoCard.setVisibility(View.GONE);
            initializeRegistrationForm();
        });
    }

    private void showAppointmentInfo()
    {
        // TODO: get info from DB
        String location = "Kuala Lumpur Convention Center";
        long time = 1637902800;
        boolean appointmentConfirmed = false;

        textViewLocation.setText(location);
        textViewAppointmentDate.setText(getDate(time));
        textViewAppointmentTime.setText(getTime(time));

        if(appointmentConfirmed)
            hideChangeDateButton();

        appointmentCard.setVisibility(View.VISIBLE);

        buttonChangeDate.setOnClickListener(v ->
        {
            // TODO: add a date picker
        });

        buttonConfirmAppointment.setOnClickListener(v ->
        {
            // TODO: add confirmation message
            hideChangeDateButton();
        });
    }

    private void hideChangeDateButton()
    {
        buttonChangeDate.setVisibility(View.GONE);
        buttonConfirmAppointment.setVisibility(View.GONE);
        textViewAppointmentConfirmed.setVisibility(View.VISIBLE);
    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "EEEE, dd MMM yyyy");
    }

    private String getTime(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "HH:mm aa");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }

    private void initializeStatusIcons()
    {
        if(vaccinationStage.previous() == null)
        {
            iconLeftHolder.setVisibility(View.INVISIBLE);
            iconLeftBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            iconLeft.setImageResource(vaccinationStage.previous().getDrawableID());
        }
        iconCenter.setImageResource(vaccinationStage.getDrawableID());
        if (vaccinationStage.next() == null)
        {
            iconRightHolder.setVisibility(View.INVISIBLE);
            iconRightBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            iconRight.setImageResource(vaccinationStage.next().getDrawableID());
        }

    }
}