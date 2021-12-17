package com.example.covidhelper.database.table;

import androidx.room.PrimaryKey;

public class VaccinationCertificate
{
    public int userID;
    public int dose1Date;
    public String dose1Manufacturer;
    public String dose1Facility;
    public String dose1Batch;

    public int dose2Date;
    public String dose2Manufacturer;
    public String dose2Facility;
    public String dose2Batch;

    public VaccinationCertificate (int userID, int dose1Date, String dose1Manufacturer, String dose1Facility, String dose1Batch, int dose2Date, String dose2Manufacturer, String dose2Facility, String dose2Batch) {
        this.userID = userID;
        this.dose1Date = dose1Date;
        this.dose1Manufacturer = dose1Manufacturer;
        this.dose1Facility = dose1Facility;
        this.dose1Batch = dose1Batch;

        this.dose2Date = dose2Date;
        this.dose2Manufacturer = dose2Manufacturer;
        this.dose2Facility = dose2Facility;
        this.dose2Batch = dose2Batch;
    }

}
