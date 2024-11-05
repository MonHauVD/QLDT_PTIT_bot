/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Crawler2;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.net.http.HttpHeaders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import vietdung.qldt_bottelegram.Ver2.Model2.ListUser2;
import vietdung.qldt_bottelegram.Ver2.Model2.User2;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.RequestId;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 * @author TranVietDung
 */
public class Crawl_layDanhSachMonDaHoc
{
    private ListUser2 listUser;
    private Long chatId;

    public Crawl_layDanhSachMonDaHoc()
    {
    }

    public Crawl_layDanhSachMonDaHoc(ListUser2 listUser, Long chatId) {
        this.listUser = listUser;
        this.chatId = chatId;
    }

    public String start()
    {
        StringBuilder Ans = new StringBuilder();
        User2 user = listUser.getUserByChatId(chatId);
        String responseBody = user.getW_locdsdiemsinhvien();
        if(responseBody == null)
        {
            Crawl_getDataFromQLDT crawl_getDataFromQLDT = new Crawl_getDataFromQLDT(listUser, chatId);
            String getDataString = crawl_getDataFromQLDT.start();
            user = listUser.getUserByChatId(chatId);
            responseBody = user.getW_locdsdiemsinhvien();
            if(responseBody == null)
            {
                return getDataString;
            }
        }

        String stringDate = user.getDate_w_locdsdiemsinhvien();
        SimpleDateFormat inputFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
        try {
            Date lastUpdateDate = inputFormatter.parse(stringDate);
            String formattedDate = outputFormatter.format(lastUpdateDate);
            System.out.println("Dữ liệu được cập nhật lần cuối lúc: " + formattedDate);
            Ans.append("Dữ liệu được cập nhật lần cuối lúc: " + formattedDate + "\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Ans.append("Danh sách các môn đã học:\n");

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray ds_diem_hockyArray = dataObject.getJSONArray("ds_diem_hocky");

        for (int i = 0; i < ds_diem_hockyArray.length(); i++)
        {
            JSONObject kiHocThuI = ds_diem_hockyArray.getJSONObject(i);
            String ten_hoc_ky = kiHocThuI.getString("ten_hoc_ky");
            Ans.append(ten_hoc_ky + ":\n");

            JSONArray ds_diem_mon_hoc = kiHocThuI.getJSONArray("ds_diem_mon_hoc");
            for(int j = 0; j < ds_diem_mon_hoc.length(); j++)
            {
                String ten_mon = ds_diem_mon_hoc.getJSONObject(j).getString("ten_mon");
                Ans.append((j+1) + ". " + ten_mon + "\n");
            }
            Ans.append("\n");
        }          

        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_layDanhSachMonDaHoc crawl = new Crawl_layDanhSachMonDaHoc(lsUser2, 1L);
        System.out.println("Crawl_layDanhSachMonDaHoc: " + crawl.start());
    }

}
