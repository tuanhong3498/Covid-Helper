package com.example.covidhelper.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationCertificate;
import com.example.covidhelper.ui.Sign.LoginActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //User profile
        ImageView editInform = root.findViewById(R.id.editInform);
        TextView name = root.findViewById(R.id.name);
        TextView idPassport = root.findViewById(R.id.id_passport);
        TextView state = root.findViewById(R.id.state);
        TextView phoneNumber = root.findViewById(R.id.phone_number);
        TextView email = root.findViewById(R.id.email);
        //Risk status
        LinearLayout headerOfRiskStatusCard = root.findViewById(R.id.header_of_riskStatusCard);
        TextView riskStatus = root.findViewById(R.id.risk_status);
        TextView symptomStatus = root.findViewById(R.id.symptom_status);
        //QR code
        ImageView qrCode= root.findViewById(R.id.risk_state_qr_image);
        CardView change_password = root.findViewById(R.id.change_password);
        CardView sign_out = root.findViewById(R.id.sign_out_card);
        //Vaccination certificate
        CardView vaccinationStatusCard = root.findViewById(R.id.vaccination_status_card);
        TextView vaccinationCertificateName = root.findViewById(R.id.content_name);
        TextView vaccinationCertificateIc = root.findViewById(R.id.content_ic);
        TextView vaccinationCertificateDateDose1 = root.findViewById(R.id.content_date_dose1);
        TextView vaccinationCertificateManufacturerDose1 = root.findViewById(R.id.content_manufacturer_dose1);
        TextView vaccinationCertificateFacilityDose1 = root.findViewById(R.id.content_facility_dose1);
        TextView vaccinationCertificateBatchDose1 = root.findViewById(R.id.content_batch_dose1);
        TextView vaccinationCertificateDateDose2 = root.findViewById(R.id.content_date_dose2);
        TextView vaccinationCertificateManufacturerDose2 = root.findViewById(R.id.content_manufacturer_dose2);
        TextView vaccinationCertificateFacilityDose2 = root.findViewById(R.id.content_facility_dose2);
        TextView vaccinationCertificateBatchDose2 = root.findViewById(R.id.content_batch_dose2);



        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        ProfileViewModel profileViewModel = factory.create(ProfileViewModel.class);
        SharedPreferences sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        // storeData
        profileViewModel.getUserInfo(sp.getInt("userID", -1)).observe(requireActivity(), userInfoList -> {
            // Update the cached copy of the words in the adapter.
            for (User user : userInfoList)
            {
                //set data into profile card
                name.setText(user.fullName);
                idPassport.setText(user.iCNumber);
                state.setText(user.livingState);
                phoneNumber.setText(user.phoneNumber);
                email.setText(user.email);
                //set data into risk status card
                riskStatus.setText(user.riskStatus);
                symptomStatus.setText(user.symptomStatus);


                switch (determineRiskStatusCard(user.symptomStatus, user.riskStatus)) {
                    case "Low":
                        headerOfRiskStatusCard.setBackgroundColor(Color.parseColor("#00B2FF"));
                        break;
                    case "Medium":
                        headerOfRiskStatusCard.setBackgroundColor(Color.parseColor("#F8C44F"));
                        break;
                    case "High":
                        headerOfRiskStatusCard.setBackgroundColor(Color.parseColor("#F37878"));
                        break;
                }

                //QR code
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(user.symptomStatus + ", " + user.riskStatus, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    qrCode.setImageBitmap(bitmap);
                    InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                }catch (WriterException e){
                    e.printStackTrace();
                }

                //set data into vaccination certificate card
                if (user.vaccinationStage != null && user.vaccinationStage.equals("Fully Vaccinated")) {
                    vaccinationCertificateName.setText(user.fullName);
                    vaccinationCertificateIc.setText(user.iCNumber);
                    profileViewModel.getVaccinationCertificate(sp.getInt("userID", -1)).observe(requireActivity(), vaccinationCertificateList -> {
                        for (VaccinationCertificate vaccinationCertificate : vaccinationCertificateList) {
                            vaccinationCertificateDateDose1.setText(getDate(vaccinationCertificate.dose1Date));
                            vaccinationCertificateManufacturerDose1.setText(vaccinationCertificate.dose1Manufacturer);
                            vaccinationCertificateFacilityDose1.setText(vaccinationCertificate.dose1Facility);
                            vaccinationCertificateBatchDose1.setText(vaccinationCertificate.dose1Batch);
                            vaccinationCertificateDateDose2.setText(getDate(vaccinationCertificate.dose2Date));
                            vaccinationCertificateManufacturerDose2.setText(vaccinationCertificate.dose2Manufacturer);
                            vaccinationCertificateFacilityDose2.setText(vaccinationCertificate.dose2Facility);
                            vaccinationCertificateBatchDose2.setText(vaccinationCertificate.dose2Batch);
                        }
                    });
                }else {
                    vaccinationStatusCard.setVisibility(View.GONE);
                }
            }
        });

        editInform.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changeInformFragment);
        });
        change_password.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changePasswordFragment);
        });
        sign_out.setOnClickListener(v -> {

            SharedPreferences settings = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            settings.edit().clear().apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
        });
        return root;
    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "EEEE, dd MMM yyyy");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }

    private String determineRiskStatusCard(String symptomStatus, String riskStatus){
        if(symptomStatus.equals("Low Symptom") && riskStatus.equals("Low Risk")){
            return "Low";
        }else if(symptomStatus.equals("High Symptom") || riskStatus.equals("High Risk")){
            return "High";
        }else{
            return "Medium";
        }
    }
}