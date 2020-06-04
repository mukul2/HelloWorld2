package com.doctor.telemedicine.Data;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.reflect.TypeToken;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.model.AmbulanceModel;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModel2;
import com.doctor.telemedicine.model.AppointmentModelNew;
import com.doctor.telemedicine.model.BlogModel;
import com.doctor.telemedicine.model.ChamberInfo;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.DeptModel;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.NoticeModel;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.model.PrescriptionRequestModel;
import com.doctor.telemedicine.model.RecomentationModel;
import com.doctor.telemedicine.model.SpacialistModel;
import com.doctor.telemedicine.model.TestList;
import com.doctor.telemedicine.model.TestRecomendationModel;
import com.doctor.telemedicine.model.TestRecommendationInfo;
import com.doctor.telemedicine.model.VideoCallModel;
import com.sinch.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkl on 2/14/2019.
 */

public class Data {
    public static boolean isBadgeShowing = false;
    public static String NOW_HITTING_SERVICE ;
    public static String CURRENCY_USD = "USD" ;
    public static String PAY_PAL_CLIENT_ID = "AbZcTBiyddfmw5Cnh6oTKaURCmALlmN18LsGxz4WDZY6714koqcQNfJ2Qm-AIQNhWk-TjqstCTHpBQXD" ;


    public static BottomNavigationMenuView menuView;
    public static BottomNavigationItemView itemView;
    public static  View notificationBadge;;
    public static BottomNavigationView bottom_nav;
    public static boolean USER_ENABLED=false;
    public static AmbulanceModel NOW_SHOWING_AMBULANCE;
    public static NoticeModel NOW_SHOWING_NOTICE;
    public static SessionManager SESSION_MANAGER;
    public static String PHOTO_BASE = "http://telemedicine.drshahidulislam.com/";
    public static BlogModel NOW_SHOWING_BLOG;
    public static String TYPE_OF_ACTIVITY;
    public static String TYPE_OF_CALL;
    public static String ADMIN_ID="1";
    public static String CALL_TYPE_AUDIO="audio";
    public static String CALL_TYPE_VIDEO="video";
    public static PrescriptionRequestModel REQUEST_TO_PRESCRIBE;
    public static String TYPE_DOCTOR = "d";
    public static String TYPE_PATIENT = "p";
    public static List<SpacialistModel> spacialist = new ArrayList<>();
    public static List<DoctorModel> searchResult = new ArrayList<>();
    public static DoctorModel singleDrModel;
    public static TestRecomendationModel testList;
    public static AppointmentModelNew appointmentModel;
    public static AppointmentModel2 drServingModel;
    public static VideoCallModel CurentCallDr;
    public static ChamberInfo CHAMBER_TO_BOOK;

    public static List<Day> days = new ArrayList<>();
    public static String USER_NAME;
    public static String TEMP_LINK;

    public static String BaseUrl = "";
    public static String FACEBOOK_LINK = "";
    public static int STATUS_PENDING = 0;
    public static int STATUS_APPROVED = 1;
    public static int STATUS_TEST_RECOMMENED = 2;
    public static int STATUS_SERVED = 3;
    public static int STATUS_CANCEL = 4;
    public static int STATUS_DELETE = 5;

    public static String getColorCode(int pos) {
        List<String> colors = new ArrayList<>();
       /* colors.add("#DC7633");
        colors.add("#2E4053");
        colors.add("#2ECC71");
        colors.add("#27AE60");
        colors.add("#48C9B0");
        colors.add("#2980B9");
        colors.add("#8E44AD");*/
        // colors.add("#E74C3C");
        colors.add("#3498DB");
        colors.add("#45B39D");
        colors.add("#1F618D");
        return colors.get(pos % colors.size());
    }

    public static List<DeptModel> allDeptOffline() {
        String data = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Allergists/Immunologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Anesthesiologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Cardiologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 4,\n" +
                "        \"name\": \"Colon and Rectal Surgeons \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 5,\n" +
                "        \"name\": \"Critical Care Medicine Specialists\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 6,\n" +
                "        \"name\": \"Dermatologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 7,\n" +
                "        \"name\": \"Gastroenterologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 8,\n" +
                "        \"name\": \"Infectious Disease Specialists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 9,\n" +
                "        \"name\": \"Neurologists \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 10,\n" +
                "        \"name\": \"Urologists \"\n" +
                "    }\n" +
                "]";

        Gson gson = new Gson();
        Type type = new TypeToken<List<DeptModel>>() {
        }.getType();
        return gson.fromJson(data, type);


    }

    public static List<String> getDistricts() {
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("DHAKA");
        categories.add("FARIDPUR");
        categories.add("GAZIPUR");
        categories.add("GOPALGANJ");
        categories.add("JAMALPUR");
        categories.add("KISHOREGONJ");
        categories.add("MADARIPUR");
        categories.add("MANIKGANJ");
        categories.add("MUNSHIGANJ");
        categories.add("MYMENSINGH");
        categories.add("NARAYANGANJ");
        categories.add("NARSINGDI");
        categories.add("NETRAKONA");
        categories.add("RAJBARI");
        categories.add("SHARIATPUR");
        categories.add("SHERPUR");
        categories.add("TANGAIL");
        categories.add("BARGUNA");
        categories.add("BARISAL");
        categories.add("BHOLA");
        categories.add("JHALOKATI");
        categories.add("PATUAKHALI");
        categories.add("PIROJPUR ");
        categories.add("BANDARBAN");
        categories.add("BRAHMANBARIA");
        categories.add("CHANDPUR");
        categories.add("CHITTAGONG");
        categories.add("COMILLA");
        categories.add("COX'S BAZAR");
        categories.add("COX'S BAZAR");
        categories.add("KHAGRACHHARI");
        categories.add("LAKSHMIPUR");
        categories.add("NOAKHALI");
        categories.add("RANGAMATI ");
        categories.add("BAGERHAT");
        categories.add("CHUADANGA");
        categories.add("JESSORE");
        categories.add("JHENAIDAH");
        categories.add("KHULNA");
        categories.add("KUSHTIA");
        categories.add("MAGURA");
        categories.add("MEHERPUR");
        categories.add("NARAIL");
        categories.add("SATKHIRA");
        categories.add("BOGRA");
        categories.add("CHAPAINABABGANJ");
        categories.add("JOYPURHAT");
        categories.add("PABNA");
        categories.add("NAOGAON");
        categories.add("NATORE");
        categories.add("RAJSHAHI");
        categories.add("SIRAJGANJ");
        categories.add("DINAJPUR");
        categories.add("GAIBANDHA");
        categories.add("KURIGRAM");
        categories.add("LALMONIRHAT");
        categories.add("NILPHAMARI");
        categories.add("PANCHAGARH");
        categories.add("RANGPUR");
        categories.add("THAKURGAON");
        categories.add("HABIGANJ");
        categories.add("MAULVIBAZAR");
        categories.add("SUNAMGANJ");
        categories.add("SYLHET");
        return categories;
    }

    public static List<String> getAllStatusTypes() {
        List<String> list = new ArrayList<>();
        list.add("Pending");
        list.add("Approved");
        list.add("Test Recommened");
        list.add("Served");
        list.add("Cancel");
        list.add("Delete");
        return list;
    }


}
