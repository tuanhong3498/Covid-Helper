package com.example.covidhelper.ui.dashboard.tools.vaccine;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;
import com.example.covidhelper.model.VaccinationStage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VaccinationFragment extends Fragment
{
    int userID;

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
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutIC;
    private TextInputLayout inputLayoutState;
    private TextInputLayout inputLayoutPostcode;
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

        SharedPreferences sp = sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("userID", -1);

        findViewsByIds(root);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        mViewModel = factory.create(VaccinationViewModel.class);

        mViewModel.getUser(userID).observe(requireActivity(), user ->
        {
            VaccinationStage vaccinationStage = VaccinationStage.fromString(user.vaccinationStage);
            initializeStatusIcons(vaccinationStage);

            // Status title
            TextView textViewTitle = root.findViewById(R.id.vaccine_status_title_current);
            textViewTitle.setText(vaccinationStage.getTitle());
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        initializeContent();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, states);
        stateDropDown.setAdapter(arrayAdapter);
    }

    private void initializeContent()
    {
        mViewModel.getUser(userID).observe(requireActivity(), user ->
        {
            VaccinationStage vaccinationStage = VaccinationStage.fromString(user.vaccinationStage);

            switch(vaccinationStage)
            {
                case REGISTER:
                    initializeRegistrationCard();
                    break;
                case DOSE_1:
                case DOSE_2:
                    showAppointmentInfo(vaccinationStage);
                    break;
                case WAIT_14_DAYS:
                    waitMessage.setVisibility(View.VISIBLE);
                    break;
                case COMPLETED:
                    // TODO: show digital vaccine certificate
            }
        });

    }

    private void initializeRegistrationCard()
    {
        mViewModel.getVaccineRegistrationRecord(userID).observe(requireActivity(), vaccineRegistrationRecord ->
        {
            if(vaccineRegistrationRecord == null)
                initializeRegistrationForm(false);   // not registered yet
            else
                showRegisteredInfo();
        });
    }

    private void initializeRegistrationForm(boolean registered)
    {

        mViewModel.getUser(userID).observe(requireActivity(), user ->
        {
            // prefill some of the fields
            textInputName.setText(user.fullName);
            textInputIC.setText(user.iCNumber);
            stateDropDown.setText(user.livingState, false);
        });
        if(registered)
        {
            mViewModel.getVaccineRegistrationRecord(userID).observe(requireActivity(), vaccineRegistrationRecord ->
                    textInputPostcode.setText(vaccineRegistrationRecord.postcode));
        }

        registrationForm.setVisibility(View.VISIBLE);

        buttonSubmitRegistrationFrom.setOnClickListener(v ->
        {
            String username = getTextAndCheckEmpty(inputLayoutName, textInputName, "Please enter your name");
            String icNumber = getTextAndCheckEmpty(inputLayoutIC, textInputIC, "Please provide your IC/Passport number");
            String state = getTextAndCheckEmpty(inputLayoutState, stateDropDown, "Please select the state that you currently stay");
            String postCode = getTextAndCheckEmpty(inputLayoutPostcode, textInputPostcode, "Please enter the postcode of where you currently stay");

            // check if any field is left empty
            if(username.equals("") || icNumber.equals("") || state.equals("") || postCode.equals(""))
                return;

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() ->
            {
                // check if the ic number entered by the user conflict with other user
                boolean icConflict = mViewModel.isICConflict(userID, icNumber);
                handler.post(() ->
                {
                    if(icConflict)
                    {
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Conflict IC number")
                                .setMessage("The entered IC/Passport number has conflicts with that of the other user. Please note that you should only register for your own vaccination.")
                                .setPositiveButton("Ok", (dialog, which) ->
                                {
                                    textInputIC.setText("");
                                })
                                .show();
                    }
                    else
                    {
                        // store or update the user registration record
                        if (!registered)
                            mViewModel.insertVaccineRegistration(new VaccineRegistrationRecord( userID, state, postCode));
                        else
                            mViewModel.updateVaccineRegistrationRecord(userID, state, postCode);

                        // update the user info
                        mViewModel.updateUserName(userID, username);
                        mViewModel.updateICNumber(userID, icNumber);

                        registrationForm.setVisibility(View.GONE);
                        showRegisteredInfo();
                    }
                });
            });
        });
    }

    private String getTextAndCheckEmpty(TextInputLayout editTextHolder, EditText editTextField, String errorMessage)
    {
        String text = editTextField.getText().toString();
        if(text.equals(""))
        {
            // note: errorEnabled should be set to true for textInputLayout
            editTextHolder.setError(errorMessage);
            editTextField.setFocusable(true);
            editTextField.setFocusableInTouchMode(true);
            editTextField.requestFocus();
        }
        else
        {
            editTextHolder.setErrorEnabled(false);
        }
        return text;
    }

    private void showRegisteredInfo()
    {

        mViewModel.getUser(userID).observe(requireActivity(), user ->
        {
            textViewName.setText(user.fullName);
            textViewICPassport.setText(user.iCNumber);
        });

        mViewModel.getVaccineRegistrationRecord(userID).observe(requireActivity(), vaccineRegistrationRecord ->
        {
            textViewCurrentStayingState.setText(vaccineRegistrationRecord.state);
            textViewPostcode.setText(vaccineRegistrationRecord.postcode);
        });

        registrationInfoCard.setVisibility(View.VISIBLE);

        buttonChangeInfo.setOnClickListener(v ->
        {
            registrationInfoCard.setVisibility(View.GONE);
            initializeRegistrationForm(true);
        });
    }

    private void showAppointmentInfo(VaccinationStage vaccinationStage)
    {
        int dosage;
        if (vaccinationStage == VaccinationStage.DOSE_1)
            dosage = 1;
        else
            dosage = 2;

        mViewModel.getVaccinationRecord(userID, dosage).observe(requireActivity(), vaccinationRecord ->
        {
            long time = vaccinationRecord.vaccinationTime;
            final int UNIX_SECOND_DAY = 86400;

            textViewLocation.setText(vaccinationRecord.vaccinationLocation);
            textViewAppointmentDate.setText(getDate(time));
            textViewAppointmentTime.setText(getTime(time));

            if(vaccinationRecord.appointmentConfirmed)
            {
                buttonChangeDate.setVisibility(View.GONE);
                buttonConfirmAppointment.setVisibility(View.GONE);
                textViewAppointmentConfirmed.setVisibility(View.VISIBLE);
            }
            else
            {
                buttonChangeDate.setOnClickListener(v ->
                {
                    // create a date picker
                    CalendarConstraints calendarConstraints = new CalendarConstraints.Builder()
                            .setStart(time *1000)
                            .setEnd((time + UNIX_SECOND_DAY * 21)*1000)
                            .setValidator(DateValidatorPointForward.from((time-UNIX_SECOND_DAY)*1000))
                            .build();
                    MaterialDatePicker<?> datePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Date of vaccination appointment")
                            .setSelection(time * 1000)
                            .setCalendarConstraints(calendarConstraints)
                            .build();
                    datePicker.addOnPositiveButtonClickListener(selection ->
                    {
                        // selected time in millisecond
                        long newDate = (long) datePicker.getSelection();
                        // convert the time from millisecond to second
                        newDate = newDate/1000;
                        // save the new appointment time

                        // create a confirmation dialog
                        long finalNewDate = newDate;
                        new MaterialAlertDialogBuilder(requireContext())
                                .setMessage("Once confirmed, you cannot change the appointment schedule")
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("Confirm", (dialog, which) ->
                                {
                                    mViewModel.confirmVaccineAppointment(userID, dosage);
                                    mViewModel.updateAppointmentTime(userID, dosage, finalNewDate);
                                })
                                .show();
                    });
                    datePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
                });

                buttonConfirmAppointment.setOnClickListener(v ->
                        confirmAppointment(dosage));
            }
        });

        appointmentCard.setVisibility(View.VISIBLE);
    }

    private void confirmAppointment(int dosage)
    {
        // create a confirmation dialog
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage("Once confirmed, you cannot change the appointment schedule")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) ->
                {
                    mViewModel.confirmVaccineAppointment(userID, dosage);
                })
                .show();
    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "EEEE, dd MMM yyyy");
    }

    private String getTime(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "hh:mm aa");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }

    private void initializeStatusIcons(VaccinationStage vaccinationStage)
    {
        if(vaccinationStage.previous() == vaccinationStage)
        {
            // the first stage -> no previous stage
            iconLeftHolder.setVisibility(View.INVISIBLE);
            iconLeftBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            iconLeft.setImageResource(vaccinationStage.previous().getDrawableID());
        }
        iconCenter.setImageResource(vaccinationStage.getDrawableID());
        if (vaccinationStage.next() == vaccinationStage)
        {
            // the last stage -> no next stage
            iconRightHolder.setVisibility(View.INVISIBLE);
            iconRightBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            iconRight.setImageResource(vaccinationStage.next().getDrawableID());
        }

    }


    private void findViewsByIds(View root)
    {
        iconLeft = root.findViewById(R.id.vaccine_status_icon_previous);
        iconCenter = root.findViewById(R.id.vaccine_status_icon_current);
        iconRight = root.findViewById(R.id.vaccine_status_icon_next);
        iconLeftHolder = root.findViewById(R.id.vaccine_status_icon_previous_holder);
        iconRightHolder = root.findViewById(R.id.vaccine_status_icon_next_holder);
        iconLeftBar = root.findViewById(R.id.vaccine_status_icon_previous_bar);
        iconRightBar = root.findViewById(R.id.vaccine_status_icon_next_bar);

        registrationForm = root.findViewById(R.id.vaccine_registration_form);
        textInputName = root.findViewById(R.id.vaccine_text_box_name);
        textInputIC = root.findViewById(R.id.vaccine_text_box_ic);
        stateDropDown = root.findViewById(R.id.vaccine_autoTextView_state);
        textInputPostcode = root.findViewById(R.id.vaccine_text_box_postcode);
        inputLayoutName = root.findViewById(R.id.vaccine_input_layout_name);
        inputLayoutIC = root.findViewById(R.id.vaccine_input_layout_ic);
        inputLayoutState = root.findViewById(R.id.vaccine_input_layout_state);
        inputLayoutPostcode = root.findViewById(R.id.vaccine_input_layout_postcode);
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
    }
}