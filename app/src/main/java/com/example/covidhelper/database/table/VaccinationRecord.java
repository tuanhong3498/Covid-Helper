package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys =
            {@ForeignKey(entity = User.class,
            parentColumns = "userID",
            childColumns = "userID"),
            @ForeignKey(entity = VaccineBrand.class,
            parentColumns = "vaccineBrandID",
            childColumns = "vaccineBrand")},
        indices =
                {@Index(value = {"userID", "dosage"}, unique = true),
                @Index(value = "vaccineBrand")})
public class VaccinationRecord
{
    @PrimaryKey(autoGenerate = true)
    public int vaccinationRecordID;

    public int userID;
    public int dosage;
    public String vaccinationLocation;
    public long vaccinationTime;
    public boolean appointmentConfirmed;
    public int vaccineBrand;
    public String vaccineBatch;

    public VaccinationRecord(int userID, int dosage, String vaccinationLocation, long vaccinationTime, boolean appointmentConfirmed, int vaccineBrand, String vaccineBatch) {
        this.userID = userID;
        this.dosage = dosage;
        this.vaccinationLocation = vaccinationLocation;
        this.vaccinationTime = vaccinationTime;
        this.appointmentConfirmed = appointmentConfirmed;
        this.vaccineBrand = vaccineBrand;
        this.vaccineBatch = vaccineBatch;
    }
}
