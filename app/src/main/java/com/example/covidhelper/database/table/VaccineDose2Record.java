package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"),
        indices = {@Index(value = {"userID"})})
public class VaccineDose2Record {
    @PrimaryKey(autoGenerate = true)
    public int vaccineDose2RecordID;

    public int userID;
    public String dose2Location;
    public int dose2Date;
    public String dose2Time;
    public boolean dose2AppointmentConfirmed;
    public String dose2Manufacturer;
    public String dose2Facility;
    public String dose2Batch;

    public VaccineDose2Record(int userID, String dose2Location, int dose2Date, String dose2Time, boolean dose2AppointmentConfirmed, String dose2Manufacturer, String dose2Facility, String dose2Batch) {
        this.userID = userID;
        this.dose2Location = dose2Location;
        this.dose2Date = dose2Date;
        this.dose2Time = dose2Time;
        this.dose2AppointmentConfirmed = dose2AppointmentConfirmed;
        this.dose2Manufacturer = dose2Manufacturer;
        this.dose2Facility = dose2Facility;
        this.dose2Batch = dose2Batch;
    }
}