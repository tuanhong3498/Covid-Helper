package com.example.covidhelper.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.covidhelper.database.DAO.AnnouncementDAO;
import com.example.covidhelper.database.DAO.CheckInPlaceDAO;
import com.example.covidhelper.database.DAO.CheckInRecordDAO;
import com.example.covidhelper.database.DAO.CheckInRecordDetailsDAO;
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
import com.example.covidhelper.database.DAO.VaccinationCertificateDAO;
import com.example.covidhelper.database.DAO.VaccinationRecordDAO;
import com.example.covidhelper.database.DAO.VaccineBrandDAO;
import com.example.covidhelper.database.DAO.VaccineRegistrationRecordDAO;
import com.example.covidhelper.database.table.Announcement;
import com.example.covidhelper.database.table.CheckInPlace;
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

@Database(entities = {User.class, Announcement.class, CheckInRecord.class, CheckInPlace.class, Hotspot.class, SelfTestResult.class, SOPContent.class, SOP.class, VaccinationRecord.class, VaccineBrand.class, VaccineRegistrationRecord.class, CovidTestsConducted.class, DailyNewCases.class, DailyNewDeaths.class, DailyVaccineAdministration.class, FAQ.class}, version = 1, exportSchema = false)
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
    public abstract CheckInPlaceDAO getCheckPlaceDAO();
    public abstract CheckInRecordDetailsDAO getCheckInRecordDetailsDAO();
    public abstract SelfTestResultDAO getSelfTestResultDAO();
    public abstract VaccineRegistrationRecordDAO getVaccineRegistrationRecordDAO();
    public abstract VaccinationRecordDAO getVaccinationRecordDAO();
    public abstract VaccinationCertificateDAO getVaccinationCertificateDAO();
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
                CheckInPlaceDAO checkInPlaceDAO = instance.getCheckPlaceDAO();
                CheckInRecordDetailsDAO checkInRecordDetailsDAO = instance.getCheckInRecordDetailsDAO();
                SelfTestResultDAO selfTestResultDAO = instance.getSelfTestResultDAO();
                VaccineRegistrationRecordDAO vaccineRegistrationRecordDAO = instance.getVaccineRegistrationRecordDAO();
                VaccinationRecordDAO vaccinationRecordDAO = instance.getVaccinationRecordDAO();
                VaccineBrandDAO vaccineBrandDAO = instance.getVaccineBrandDAO();
                VaccinationCertificateDAO vaccinationCertificateDAO = instance.getVaccinationCertificateDAO();
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
                sopContentDAO.insert(new SOPContent("Phase 3", "require vaccination", "allowed","require vaccination", "allowed","require vaccination", "allowed", "allowed", "not allowed"));
                sopContentDAO.insert(new SOPContent("Phase 4", "require vaccination", "allowed","allowed", "allowed","allowed", "allowed", "allowed", "allowed"));

                sopDAO.insert(new SOP("Johor", "Phase 4"));
                sopDAO.insert(new SOP("Kedah", "Phase 4"));
                sopDAO.insert(new SOP("Kelantan", "Phase 4"));
                sopDAO.insert(new SOP("Melacca", "Phase 4"));
                sopDAO.insert(new SOP("Pahang", "Phase 4"));
                sopDAO.insert(new SOP("Perak", "Phase 4"));
                sopDAO.insert(new SOP("Perlis", "Phase 4"));
                sopDAO.insert(new SOP("Pinang", "Phase 4"));
                sopDAO.insert(new SOP("Sabah", "Phase 4"));
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

                announcementDAO.insert(new Announcement(1, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , "dummy_announcement_task"
                        , 1633046400));
                announcementDAO.insert(new Announcement(1, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , "dummy_announcement_information", 1633046400));
                announcementDAO.insert(new Announcement(1, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , "dummy_announcement_fake_news1", 1633046400));
                announcementDAO.insert(new Announcement(2, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , "dummy_announcement_task", 1633046400));
                announcementDAO.insert(new Announcement(2, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , "dummy_announcement_information", 1633046400));
                announcementDAO.insert(new Announcement(2, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , "dummy_announcement_fake_news1", 1633046400));
                announcementDAO.insert(new Announcement(3, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , "dummy_announcement_task", 1633046400));
                announcementDAO.insert(new Announcement(3, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , "dummy_announcement_information", 1633046400));
                announcementDAO.insert(new Announcement(3, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , "dummy_announcement_fake_news1", 1633046400));
                announcementDAO.insert(new Announcement(4, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , "dummy_announcement_task", 1633046400));
                announcementDAO.insert(new Announcement(4, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , "dummy_announcement_information", 1633046400));
                announcementDAO.insert(new Announcement(4, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , "dummy_announcement_fake_news1", 1633046400));
                announcementDAO.insert(new Announcement(5, "task"
                        , "GKVSTF Now National Rapid Response Task Force For Covid-19"
                        , "The Greater Klang Valley Special Task Force (GKVSTF) has been transformed into the National Rapid Response Task Force, a multidiscipline national response unit set up to proactively control Covid-19 as the country prepares to transition towards endemicity"
                        , "dummy_announcement_task", 1633046400));
                announcementDAO.insert(new Announcement(5, "information"
                        , "Covid-19: 95.7% of Malaysian adult population fully vaccinated as of Nov 3"
                        , "Based on data at the CovidNow of Ministry, 97.8% of the adult population have also received at least one dose of a two-dose Covid-19 vaccine."
                        , "dummy_announcement_information", 1633046400));
                announcementDAO.insert(new Announcement(5, "fake news"
                        , "COVID-19 was created in a lab"
                        , "Scientific researchers also conclude that COVID-19 seems to have evolved naturally, rather than being man-made. COVID-19’s RNA sequences imply a virus which originated in bats, before infecting an unidentified animal species. Its structure doesn’t look like viruses that are known to infect humans, which makes it different from what a human would intentionally create. It’s also difficult to engineer with current technology."
                        , "dummy_announcement_fake_news1", 1633046400));

                checkInPlaceDAO.insert(new CheckInPlace("Radio Amatur Station", "9W2AJL - Radio Amatur Station, 6, Jalan Warisan Permai 2/13, Kota Warisan, 43900 Sepang, Selangor"));
                checkInPlaceDAO.insert(new CheckInPlace("Sinar Service EnterpriseGame", "Sinar Service Enterprise, No. 99, Jalan Warisan megah 1/9 Kota Warisan, 43900, Sepang, Selangor, 43900"));
                checkInPlaceDAO.insert(new CheckInPlace("Cozy Apartment Near KLIA", "Block K, Megah Villa Apartment, Kota Warisan, 43900 Sepang, Selangor"));



                checkInRecordDAO.insert(new CheckInRecord(1, "03 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(1, "03 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(1, "03 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(1, "02 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(1, "02 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(1, "02 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(1, "01 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(1, "01 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(1, "01 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(1, "30 Sep 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(1, "30 Sep 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(1, "30 Sep 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(2, "03 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(2, "03 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(2, "03 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(3, "02 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(3, "02 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(3, "02 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(3, "01 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(3, "01 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(3, "01 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(4, "02 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(4, "02 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(4, "02 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(4, "01 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(4, "01 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(4, "01 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(5, "02 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(5, "02 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(5, "02 Oct 2021", 1633008806,3));
                checkInRecordDAO.insert(new CheckInRecord(5, "01 Oct 2021", 1632962006,1));
                checkInRecordDAO.insert(new CheckInRecord(5, "01 Oct 2021",1632976406,2));
                checkInRecordDAO.insert(new CheckInRecord(5, "01 Oct 2021", 1633008806,3));


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
