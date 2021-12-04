package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "userID"),
        indices = {@Index(value = {"userID"})})
public class VaccineDose1Record {
    @PrimaryKey(autoGenerate = true)
    public int vaccineDose1RecordID;

    public int userID;
    public String dose1Location;
    public int dose1Date;
    public String dose1Time;
    public boolean dose1AppointmentConfirmed;
    public String dose1Manufacturer;
    public String dose1Facility;
    public String dose1Batch;

    public VaccineDose1Record(int userID, String dose1Location, int dose1Date, String dose1Time, boolean dose1AppointmentConfirmed, String dose1Manufacturer, String dose1Facility, String dose1Batch) {
        this.userID = userID;
        this.dose1Location = dose1Location;
        this.dose1Date = dose1Date;
        this.dose1Time = dose1Time;
        this.dose1AppointmentConfirmed = dose1AppointmentConfirmed;
        this.dose1Manufacturer = dose1Manufacturer;
        this.dose1Facility = dose1Facility;
        this.dose1Batch = dose1Batch;
    }
}
