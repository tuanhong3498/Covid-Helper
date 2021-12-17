package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.covidhelper.database.table.CheckInRecordDetails;
import com.example.covidhelper.database.table.VaccinationCertificate;

import java.util.List;

@Dao
public interface VaccinationCertificateDAO
{
    @Query("SELECT t1.userID, t1.dose1Date, t1.dose1Manufacturer, t1.dose1Facility, t1.dose1Batch, t2.dose2Date, t2.dose2Manufacturer, t2.dose2Facility, t2.dose2Batch FROM (SELECT VaccinationRecord.userID,VaccinationRecord.vaccinationTime AS dose1Date,VaccineBrand.manufacturer AS dose1Manufacturer,VaccinationRecord.vaccinationLocation AS dose1Facility, VaccinationRecord.vaccineBatch AS dose1Batch from VaccinationRecord, VaccineBrand WHERE VaccinationRecord.vaccineBrand=VaccineBrand.vaccineBrandID AND VaccinationRecord.userID=:userID AND VaccinationRecord.dosage=1)  t1 JOIN  (SELECT VaccinationRecord.userID,VaccinationRecord.vaccinationTime AS dose2Date,VaccineBrand.manufacturer AS dose2Manufacturer,VaccinationRecord.vaccinationLocation AS dose2Facility, VaccinationRecord.vaccineBatch AS dose2Batch from VaccinationRecord, VaccineBrand WHERE VaccinationRecord.vaccineBrand=VaccineBrand.vaccineBrandID AND VaccinationRecord.userID=:userID AND VaccinationRecord.dosage=2) t2  ON t1.userID = t2.userID")
    LiveData<List<VaccinationCertificate>> getVaccinationCertificate(int userID);
}
