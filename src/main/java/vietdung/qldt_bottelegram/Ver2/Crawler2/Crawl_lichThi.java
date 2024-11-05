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
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class Crawl_lichThi
{
    private ListUser2 listUser;
    private Long chatId;

    public Crawl_lichThi()
    {
    }

    public Crawl_lichThi(ListUser2 listUser, Long chatId) {
        this.listUser = listUser;
        this.chatId = chatId;
    }

    public String start()
    {
        StringBuilder Ans = new StringBuilder();
        User2 user = listUser.getUserByChatId(chatId);
        String responseBody = user.getW_locdslichthisvtheohocky();
        if(responseBody == null)
        {
            Crawl_getDataFromQLDT crawl_getDataFromQLDT = new Crawl_getDataFromQLDT(listUser, chatId);
            String getDataString = crawl_getDataFromQLDT.start();
            user = listUser.getUserByChatId(chatId);
            responseBody = user.getW_locdslichthisvtheohocky();
            if(responseBody == null)
            {
                return getDataString;
            }
        }

        String stringDate = user.getDate_w_locdslichthisvtheohocky();
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
        Ans.append("Danh sách lịch thi:\n");
        JSONObject jsonObject = new JSONObject(responseBody);
        // Navigate through the JSON structure to get to "dtb_tich_luy_he_4"
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray dsLichThiArray = dataObject.getJSONArray("ds_lich_thi");
        for (int i = 0; i < dsLichThiArray.length(); i++)
        {
            JSONObject monThiThuI = dsLichThiArray.getJSONObject(i);
            String ten_mon = monThiThuI.getString("ten_mon");
            String ma_mon = monThiThuI.getString("ma_mon");
            String ngay_thi = monThiThuI.getString("ngay_thi");
            String gio_bat_dau = monThiThuI.getString("gio_bat_dau");
            Integer si_so = monThiThuI.getInt("si_so");
            String so_phut = monThiThuI.getString("so_phut");
            String hinh_thuc_thi = monThiThuI.getString("hinh_thuc_thi");
            String dia_diem_thi = monThiThuI.getString("dia_diem_thi");
            Ans.append((i + 1) + ". " + ten_mon + " (" + ma_mon + "):\n");
            Ans.append("Giờ thi: " + gio_bat_dau + " " + ngay_thi + "\n");
            Ans.append("Thời gian thi: " + so_phut + " phút; Sĩ số: " + si_so + "\n");
            Ans.append("Hình thức thi: " + hinh_thuc_thi + "\n");
            Ans.append("Địa điểm thi: " + dia_diem_thi + "\n\n");
        }
        
                
        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_lichThi crawl = new Crawl_lichThi(lsUser2, 1L);
        System.out.println("Crawl_lichThi: " + crawl.start());
    }

}
