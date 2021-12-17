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
import com.example.covidhelper.database.DAO.FaqDAO;
import com.example.covidhelper.database.DAO.HotspotDAO;
import com.example.covidhelper.database.DAO.SOPContentDAO;
import com.example.covidhelper.database.DAO.SOPDAO;
import com.example.covidhelper.database.DAO.SelfTestResultDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccinationRecordDAO;
import com.example.covidhelper.database.DAO.VaccineBrandDAO;
import com.example.covidhelper.database.DAO.VaccineRegistrationRecordDAO;
import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CovidTestsConducted;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.DailyVaccineAdministration;
import com.example.covidhelper.database.table.FAQ;
import com.example.covidhelper.database.table.Hotspot;
import com.example.covidhelper.database.table.SOP;
import com.example.covidhelper.database.table.SOPContent;
import com.example.covidhelper.database.table.SelfTestResult;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccinationRecord;
import com.example.covidhelper.database.table.VaccineBrand;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Announcement.class, CheckInRecord.class, Hotspot.class, SelfTestResult.class, SOPContent.class, SOP.class, VaccinationRecord.class, VaccineBrand.class, VaccineRegistrationRecord.class, CovidTestsConducted.class, DailyNewCases.class, DailyNewDeaths.class, DailyVaccineAdministration.class, FAQ.class}, version = 1, exportSchema = false)
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
    public abstract VaccinationRecordDAO getVaccinationRecordDAO();
    public abstract VaccineBrandDAO getVaccineBrandDAO();
    public abstract SOPDAO getSOPDAO();
    public abstract SOPContentDAO getSOPContentDAO();
    public abstract HotspotDAO getHotspotDAO();
    public abstract CovidTestsConductedDAO getCovidTestsConductedDAO();
    public abstract DailyNewCasesDAO getDailyNewCasesDAO();
    public abstract DailyNewDeathsDAO getDailyNewDeathsDAO();
    public abstract DailyVaccineAdministrationDAO getDailyVaccineAdministrationDAO();
    public abstract FaqDAO getFaqDAO();

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
                VaccinationRecordDAO vaccinationRecordDAO = instance.getVaccinationRecordDAO();
                VaccineBrandDAO vaccineBrandDAO = instance.getVaccineBrandDAO();
                SOPDAO sopDAO = instance.getSOPDAO();
                SOPContentDAO sopContentDAO = instance.getSOPContentDAO();
                HotspotDAO hotspotDAO = instance.getHotspotDAO();
                CovidTestsConductedDAO covidTestsConductedDAO = instance.getCovidTestsConductedDAO();
                DailyNewCasesDAO dailyNewCasesDAO = instance.getDailyNewCasesDAO();
                DailyNewDeathsDAO dailyNewDeathsDAO = instance.getDailyNewDeathsDAO();
                DailyVaccineAdministrationDAO dailyVaccineAdministrationDAO = instance.getDailyVaccineAdministrationDAO();
                FaqDAO faqDAO = instance.getFaqDAO();

//                userDAO.deleteAll();

                //Seeding some dummy data
                sopContentDAO.insert(new SOPContent("Phase 1", "require vaccination", "allowed","require vaccination", "not allowed","require vaccination", "not allowed", "not allowed", "not allowed"));
                sopContentDAO.insert(new SOPContent("Phase 2", "require vaccination", "allowed","require vaccination", "not allowed","require vaccination", "allowed", "not allowed", "not allowed"));
                sopContentDAO.insert(new SOPContent("Phase 3", "require vaccination", "allowed","require vaccination", "allowed","require vaccination", "allowed", "allowed", "not allowed"));
                sopContentDAO.insert(new SOPContent("Phase 4", "require vaccination", "allowed","allowed", "allowed","allowed", "allowed", "allowed", "allowed"));

                sopDAO.insert(new SOP("Johor", "Phase 4"));
                sopDAO.insert(new SOP("Kedah", "Phase 2"));
                sopDAO.insert(new SOP("Kelantan", "Phase 4"));
                sopDAO.insert(new SOP("Melacca", "Phase 4"));
                sopDAO.insert(new SOP("Pahang", "Phase 4"));
                sopDAO.insert(new SOP("Perak", "Phase 4"));
                sopDAO.insert(new SOP("Perlis", "Phase 4"));
                sopDAO.insert(new SOP("Pinang", "Phase 4"));
                sopDAO.insert(new SOP("Sabah", "Phase 1"));
                sopDAO.insert(new SOP("Sarawak", "Phase 3"));
                sopDAO.insert(new SOP("Selangor", "Phase 4"));
                sopDAO.insert(new SOP("Sembilan", "Phase 4"));
                sopDAO.insert(new SOP("Terengganu", "Phase 4"));
                sopDAO.insert(new SOP("Kuala Lumpur", "Phase 4"));
                sopDAO.insert(new SOP("Labuan", "Phase 4"));
                sopDAO.insert(new SOP("Putrajaya", "Phase 4"));

                userDAO.insert(new User("1", "Alice", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Registration"));
                userDAO.insert(new User("2", "Bob", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Dose 1"));
                userDAO.insert(new User("3", "Coco", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Dose 2"));
                userDAO.insert(new User("4", "David", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Wait 14 Days"));
                userDAO.insert(new User("5", "Eve", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Fully Vaccinated"));
                userDAO.insert(new User("6", "Ant", "1234", "123@abc", "Johor", "1234", "Low Risk", "Low Symptom", "Registration"));

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
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
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

                vaccineBrandDAO.insert(new VaccineBrand(1, "Pfizer", "COMIRNATY Concentrate for Dispersion for Injection"));
                vaccineBrandDAO.insert(new VaccineBrand(2, "Sinovac", "Pharmaniaga Bhd"));

                vaccinationRecordDAO.insert(new VaccinationRecord(2, 1, "Compleks Sukan Pagoh", 1641272400, false, 1, "KF28"));
                vaccinationRecordDAO.insert(new VaccinationRecord(3, 1, "Compleks Sukan Pagoh", 1640048400, true, 1, "IF17"));
                vaccinationRecordDAO.insert(new VaccinationRecord(4, 1, "Compleks Sukan Pagoh", 1638594000, true, 2, "VE28"));
                vaccinationRecordDAO.insert(new VaccinationRecord(5, 1, "Compleks Sukan Pagoh", 1637289000, true, 1, "CU29"));

                vaccinationRecordDAO.insert(new VaccinationRecord(3, 2, "Compleks Sukan Pagoh", 1641272400, false, 1, "OU78"));
                vaccinationRecordDAO.insert(new VaccinationRecord(4, 2, "Compleks Sukan Pagoh", 1639803600, true, 2, "BI57"));
                vaccinationRecordDAO.insert(new VaccinationRecord(5, 2, "Compleks Sukan Pagoh", 1638498334, true, 1, "VI27"));



                hotspotDAO.insert(new Hotspot("Kuala Lumpur",1000,3.1385036,101.6169484));
                hotspotDAO.insert(new Hotspot("Johor",2000,2.0491758,102.9494518));

                covidTestsConductedDAO.insert(new CovidTestsConducted(1637769600, 94368, 29707, 4.56f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1637856000, 70616, 29871, 4.53f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1637942400, 56325, 28599, 4.47f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638028800, 70390, 21549, 4.40f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638115200, 116418, 30871, 4.38f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638201600, 105200, 40665, 4.27f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638288000, 97716, 35787, 4.27f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638374400, 84072, 32965, 4.27f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638460800, 73835, 31879, 4.25f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638547200, 59322, 30687, 4.20f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638633600, 70675, 24428, 4.19f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638720000, 130045, 30662, 4.14f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638806400, 107691, 42531, 4.13f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638892800, 106894, 39484, 4.02f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1638979200, 92997, 32646, 3.94f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1639065600, 79782, 35022, 3.84f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1639152000, 60783, 30685, 3.81f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1639238400, 79576, 25483, 3.68f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1639324800, 127822, 32539, 3.59f));
                covidTestsConductedDAO.insert(new CovidTestsConducted(1639411200, 97809, 39591, 3.55f));

                dailyNewCasesDAO.insert(new DailyNewCases(1637769600, 6144, 5635.14f));
                dailyNewCasesDAO.insert(new DailyNewCases(1637856000, 5501, 5513.14f));
                dailyNewCasesDAO.insert(new DailyNewCases(1637942400, 5097, 5404.29f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638028800, 4239, 5316.43f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638115200, 4087, 5202.43f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638201600, 4879, 5100.29f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638288000, 5439, 5055.14f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638374400, 5806, 5006.86f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638460800, 5551, 5014.00f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638547200, 4896, 4985.29f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638633600, 4298, 4993.71f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638720000, 4262, 5018.71f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638806400, 4965, 5031.00f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638892800, 5020, 4971.14f));
                dailyNewCasesDAO.insert(new DailyNewCases(1638979200, 5446, 4919.71f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639065600, 5058, 4849.29f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639152000, 4626, 4810.71f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639238400, 3490, 4695.29f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639324800, 3504, 4587.00f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639411200, 4097, 4463.00f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639497600, 3900, 4303.00f));
                dailyNewCasesDAO.insert(new DailyNewCases(1639584000, 4262, 4133.86f));

                dailyNewDeathsDAO.insert(new DailyNewDeaths(1637769600, 48, 43.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1637856000, 45, 43.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1637942400, 40, 43.43f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638028800, 29, 44.14f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638115200, 61, 43.86f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638201600, 55, 45.00f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638288000, 49, 46.71f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638374400, 47, 46.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638460800, 17, 42.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638547200, 36, 42.00f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638633600, 40, 43.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638720000, 38, 40.29f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638806400, 66, 41.86f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638892800, 28, 38.86f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1638979200, 41, 38.00f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639065600, 44, 41.86f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639152000, 31, 41.14f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639238400, 17, 37.86f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639324800, 29, 36.57f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639411200, 48, 34.00f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639497600, 33, 34.71f));
                dailyNewDeathsDAO.insert(new DailyNewDeaths(1639584000, 37, 34.14f));

                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637510400, 25652344, 25383936, 0)); // accumulated data before 23/11/2021
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637596800, 5472, 10985, 18931.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637683200, 5346, 9023, 17008.71f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637769600, 5966, 6299, 14648.43f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637856000, 4988, 6956, 12842.71f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1637942400, 2527, 5520, 12625.71f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638028800, 2089, 3783, 12361.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638115200, 4581, 7021, 11508.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638201600, 4745, 8919, 11109.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638288000, 4795, 8255, 10920.57f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638374400, 5468, 9164, 11258.71f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638460800, 4445, 7015, 11189.57f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638547200, 3238, 4169, 11098.14f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638633600, 1719, 2802, 10905.14f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638720000, 4820, 6422, 10853.71f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638806400, 4690, 6906, 10558.29f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638892800, 4713, 6522, 10299.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1638979200, 4842, 7637, 9991.43f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1639065600, 3944, 6695, 9874.14f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1639152000, 1407, 2389, 9358.29f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1639238400, 1472, 2025, 9212.00f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1639324800, 4033, 5001, 8896.57f));
                dailyVaccineAdministrationDAO.insert(new DailyVaccineAdministration(1639411200, 4105, 5016, 8543.00f));

                faqDAO.insert(new FAQ("What is CovidHelper?",
                                        "CovidHelper is an application created to facilitate residents in Malaysia. The main features of the application include delivering latest information to the public and providing tools to help the Malaysia government to manage the Covid-19 situation in Malaysia."));
                faqDAO.insert(new FAQ("How does COVID-19 spread?",
                                        "The virus primarily spreads between people through close contact and via aerosols and respiratory droplets that are exhaled when talking, breathing, or otherwise exhaling, as well as those produced from coughs or sneezes."));
                faqDAO.insert(new FAQ("Can the coronavirus disease be transmitted through water?",
                                        "Drinking water is not transmitting COVID-19. And, if you swim in a swimming pool or in a pond, you cannot get COVID-19 through water. But what can happen, if you go to a swimming pool, which is crowded and if you are close to other the people and if someone is infected, then you can be of course affected."));
                faqDAO.insert(new FAQ("Where was COVID-19 first discovered?",
                                        "The first known infections from SARS-CoV-2 were discovered in Wuhan, China. The original source of viral transmission to humans remains unclear, as does whether the virus became pathogenic before or after the spillover event."));
                faqDAO.insert(new FAQ("Who are categorised as high risk groups?",
                                        "People especially those at high risk are advised to limit hospital visits especially for non-essential matters. This is to prevent the spread of COVID-19 infection in hospitals. Those at risk are: Children, Golden citizens aged 65 and older, Patients with chronic illness, Patients with low immune system, Pregnant woman."));
                faqDAO.insert(new FAQ("What is the treatment for COVID-19 infection?",
                                        "To date, there have been no specific treatments or antiviral for COVID-19 infection. Treatment is given only to relieve the symptoms of the patient."));
                faqDAO.insert(new FAQ("If I use a facemask or N95, does it help to reduce COVID-19 transmission?",
                                        "The use of face masks and surgical masks or 3- ply face masks is recommended as it helps reduce the spread of the virus and is more practical for the general public. It is to prevent the exposure of others to the drops and spills of people who wear this mouth and nose mask."));
                faqDAO.insert(new FAQ("What is Covid-19?",
                                        "COVID-19 is a disease caused by a virus called SARS-CoV-2. Most people with COVID-19 have mild symptoms, but some people can become severely ill. Although most people with COVID-19 get better within weeks of illness, some people experience post-COVID conditions."));


            });
        }
    };


}
