package com.example.covidhelper.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.covidhelper.database.DAO.AnnouncementDAO;
import com.example.covidhelper.database.DAO.CheckInRecordDAO;
import com.example.covidhelper.database.DAO.CovidTestsConductedDAO;
import com.example.covidhelper.database.DAO.DailyNewCasesDAO;
import com.example.covidhelper.database.DAO.DailyNewDeathsDAO;
import com.example.covidhelper.database.DAO.DailyVaccineAdministrationDAO;
import com.example.covidhelper.database.DAO.HotspotDAO;
import com.example.covidhelper.database.DAO.SOPContentDAO;
import com.example.covidhelper.database.DAO.SOPDAO;
import com.example.covidhelper.database.DAO.SelfTestResultDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccineDose1RecordDAO;
import com.example.covidhelper.database.DAO.VaccineDose2RecordDAO;
import com.example.covidhelper.database.DAO.VaccineRegistrationRecordDAO;
import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CovidTestsConducted;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.DailyVaccineAdministration;
import com.example.covidhelper.database.table.Hotspot;
import com.example.covidhelper.database.table.SOP;
import com.example.covidhelper.database.table.SOPContent;
import com.example.covidhelper.database.table.SelfTestResult;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineDose1Record;
import com.example.covidhelper.database.table.VaccineDose2Record;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Announcement.class, CheckInRecord.class, Hotspot.class, SelfTestResult.class, SOPContent.class, SOP.class, VaccineDose1Record.class, VaccineDose2Record.class, VaccineRegistrationRecord.class, CovidTestsConducted.class, DailyNewCases.class, DailyNewDeaths.class, DailyVaccineAdministration.class}, version = 3, exportSchema = false)
public abstract class CovidHelperDatabase extends RoomDatabase
{
    public static final String DB_NAME = "CovidHelperDatabase";
    private static volatile CovidHelperDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO getUserDAO();
    public abstract AnnouncementDAO getAnnouncementDao();
    public abstract CheckInRecordDAO getCheckInRecordDAO();
    public abstract SelfTestResultDAO getSelfTestResultDAO();
    public abstract VaccineRegistrationRecordDAO getVaccineRegistrationRecordDAO();
    public abstract VaccineDose1RecordDAO getVaccineDose1RecordDAO();
    public abstract VaccineDose2RecordDAO getVaccineDose2RecordDAO();
    public abstract SOPDAO getSOPDAO();
    public abstract SOPContentDAO getSOPContentDAO();
    public abstract HotspotDAO getHotspotDAO();
    public abstract CovidTestsConductedDAO getCovidTestsConductedDAO();
    public abstract DailyNewCasesDAO getDailyNewCasesDAO();
    public abstract DailyNewDeathsDAO getDailyNewDeathsDAO();
    public abstract DailyVaccineAdministrationDAO getDailyVaccineAdministrationDAO();

    public static CovidHelperDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (CovidHelperDatabase.class) {
                if (instance == null) {
                    // singleton instance
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CovidHelperDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            //super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDAO userDAO = instance.getUserDAO();
                AnnouncementDAO announcementDAO = instance.getAnnouncementDao();
                CheckInRecordDAO checkInRecordDAO = instance.getCheckInRecordDAO();
                SelfTestResultDAO selfTestResultDAO = instance.getSelfTestResultDAO();
                VaccineRegistrationRecordDAO vaccineRegistrationRecordDAO = instance.getVaccineRegistrationRecordDAO();
                VaccineDose1RecordDAO vaccineDose1RecordDAO = instance.getVaccineDose1RecordDAO();
                VaccineDose2RecordDAO vaccineDose2RecordDAO = instance.getVaccineDose2RecordDAO();
                SOPDAO sopDAO = instance.getSOPDAO();
                SOPContentDAO sopContentDAO = instance.getSOPContentDAO();
                HotspotDAO hotspotDAO = instance.getHotspotDAO();
                CovidTestsConductedDAO covidTestsConductedDAO = instance.getCovidTestsConductedDAO();
                DailyNewCasesDAO dailyNewCasesDAO = instance.getDailyNewCasesDAO();
                DailyNewDeathsDAO dailyNewDeathsDAO = instance.getDailyNewDeathsDAO();
                DailyVaccineAdministrationDAO dailyVaccineAdministrationDAO = instance.getDailyVaccineAdministrationDAO();

//                userDAO.deleteAll();

                //Seeding some dummy data
                sopContentDAO.insert(new SOPContent("Phase 3", "fully vaccinated", "can","fully vaccinated", "can","fully vaccinated", "can", "cannot", "cannot"));

                sopDAO.insert(new SOP("Johor", "Phase 3"));
                sopDAO.insert(new SOP("Kedah", "Phase 3"));
                sopDAO.insert(new SOP("Kelantan", "Phase 3"));
                sopDAO.insert(new SOP("Melacca", "Phase 3"));
                sopDAO.insert(new SOP("Pahang", "Phase 3"));
                sopDAO.insert(new SOP("Perak", "Phase 3"));
                sopDAO.insert(new SOP("Perlis", "Phase 3"));
                sopDAO.insert(new SOP("Pinang", "Phase 3"));
                sopDAO.insert(new SOP("Sabah", "Phase 3"));
                sopDAO.insert(new SOP("Sarawak", "Phase 3"));
                sopDAO.insert(new SOP("Selangor", "Phase 3"));
                sopDAO.insert(new SOP("Sembilan", "Phase 3"));
                sopDAO.insert(new SOP("Terengganu", "Phase 3"));
                sopDAO.insert(new SOP("Kuala Lumpur", "Phase 3"));
                sopDAO.insert(new SOP("Labuan", "Phase 3"));
                sopDAO.insert(new SOP("Putrajaya", "Phase 3"));


                userDAO.insert(new User("1", "Alice", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Registration"));
                userDAO.insert(new User("2", "Bob", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Dose 1"));
                userDAO.insert(new User("3", "Coco", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Dose 2"));
                userDAO.insert(new User("4", "David", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Wait 14 Days"));
                userDAO.insert(new User("5", "Eve", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Fully Vaccinated"));

                announcementDAO.insert(new Announcement(1, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , 1633046400));
                announcementDAO.insert(new Announcement(1, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , 1633046400));
                announcementDAO.insert(new Announcement(1, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure does not look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , 1633046400));
                announcementDAO.insert(new Announcement(2, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , 1633046400));
                announcementDAO.insert(new Announcement(2, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , 1633046400));
                announcementDAO.insert(new Announcement(2, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , 1633046400));
                announcementDAO.insert(new Announcement(3, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , 1633046400));
                announcementDAO.insert(new Announcement(3, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , 1633046400));
                announcementDAO.insert(new Announcement(3, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , 1633046400));
                announcementDAO.insert(new Announcement(4, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , 1633046400));
                announcementDAO.insert(new Announcement(4, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , 1633046400));
                announcementDAO.insert(new Announcement(4, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , 1633046400));
                announcementDAO.insert(new Announcement(5, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , 1633046400));
                announcementDAO.insert(new Announcement(5, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , 1633046400));
                announcementDAO.insert(new Announcement(5, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , 1633046400));

                checkInRecordDAO.insert(new CheckInRecord(1, 16333, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16333, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16333, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16332, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16332, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16332, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16331, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16331, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16331, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16330, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16330, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(1, 16330, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(2, 16333, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(2, 16333, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(2, 16333, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16332, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16332, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16332, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16331, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16331, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(3, 16331, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16332, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16332, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16332, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16331, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16331, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(4, 16331, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16332, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16332, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16332, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16331, "Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor", 5600000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16331, "Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900", 88537000));
                checkInRecordDAO.insert(new CheckInRecord(5, 16331, "Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor", 25600000));


                vaccineRegistrationRecordDAO.insert(new VaccineRegistrationRecord(1, "Johor", "1234"));
                vaccineRegistrationRecordDAO.insert(new VaccineRegistrationRecord(2, "Johor", "1234"));
                vaccineRegistrationRecordDAO.insert(new VaccineRegistrationRecord(3, "Johor", "1234"));
                vaccineRegistrationRecordDAO.insert(new VaccineRegistrationRecord(4, "Johor", "1234"));
                vaccineRegistrationRecordDAO.insert(new VaccineRegistrationRecord(5, "Johor", "1234"));

                vaccineDose1RecordDAO.insert(new VaccineDose1Record(2, "Compleks Sukan Pagoh", 1633046400, "08:30 AM", false, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));
                vaccineDose1RecordDAO.insert(new VaccineDose1Record(3, "Compleks Sukan Pagoh", 1633046400, "08:30 AM", true, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));
                vaccineDose1RecordDAO.insert(new VaccineDose1Record(4, "Compleks Sukan Pagoh", 1633046400, "08:30 AM", true, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));
                vaccineDose1RecordDAO.insert(new VaccineDose1Record(5, "Compleks Sukan Pagoh", 1633046400, "08:30 AM", true, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));

                vaccineDose2RecordDAO.insert(new VaccineDose2Record(3, "Compleks Sukan Pagoh", 1634342400, "08:30 AM",false, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));
                vaccineDose2RecordDAO.insert(new VaccineDose2Record(4, "Compleks Sukan Pagoh", 1634342400, "08:30 AM",true, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));
                vaccineDose2RecordDAO.insert(new VaccineDose2Record(5, "Compleks Sukan Pagoh", 1634342400, "08:30 AM",true, "COMIRNATY Concentrate for Dispersion for Injection", "Kompleks Sukan Pagoh", "KH9374"));




                hotspotDAO.insert(new Hotspot("Kuala Lumpur",1000,3.1385036,101.6169484));
                hotspotDAO.insert(new Hotspot("Johor",2000,2.0491758,102.9494518));

                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400,99474, 46182, 9.9f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400,96673, 43082, 9.4f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*2,100825, 37382, 9.3f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*3,67827, 34369, 9.0f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*4,70297, 27174, 8.7f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*5,141200, 35180, 8.5f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*6,113545, 44228, 8.3f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*7,110446, 39793, 7.8f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*8,100153, 35360, 7.5f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*9,90455, 32691, 7.3f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*10,67260, 28749, 6.9f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*11,67441, 24980, 6.8f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*12,128940, 32291, 6.5f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1633046400+86400*13,108487, 39859, 5.9f));

                dailyNewCasesDAO.insert(new DailyNewCases(1633046400, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*2, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*3, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*4, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*5, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*6, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*7, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*8, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*9, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*10, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*11, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*12, 10915, 9915));
                dailyNewCasesDAO.insert(new DailyNewCases(1633046400+86400*13, 10915, 9915));

                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400, 128, 130));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400, 98, 110));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*2, 87, 100));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*3, 95, 92));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*4, 87, 93));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*5, 86, 91));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*6, 79, 87));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*7, 64, 80));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*8, 76, 79));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*9, 67, 75));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*10, 59, 70));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*11, 30, 60));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*12, 6, 40));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1633046400+86400*13, 0, 30));

                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400, 104345, 141128, 250000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400, 92431, 130349, 230000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*2, 98803, 116808, 220000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*3, 128890, 104156, 225000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*4, 124836, 106760, 230000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*5, 114476, 101857, 225000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*6, 109107, 93232, 210000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*7, 75148, 105938, 190000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*8, 40414, 108574, 160000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*9, 32574, 101929, 150000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*10, 53817, 139902, 160000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*11, 56926, 157390, 180000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*12, 52587, 159964, 190000));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1633046400+86400*13, 41614, 168136, 200000));



            });
        }
    };


}
