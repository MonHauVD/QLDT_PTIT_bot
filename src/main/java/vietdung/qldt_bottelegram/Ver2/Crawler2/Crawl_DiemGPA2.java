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
public class Crawl_DiemGPA2
{
    private ListUser2 listUser;
    private Long chatId;

    public Crawl_DiemGPA2()
    {
    }

    public Crawl_DiemGPA2(ListUser2 listUser, Long chatId) {
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
        
        JSONObject jsonObject = new JSONObject(responseBody);
        // Navigate through the JSON structure to get to "dtb_tich_luy_he_4"
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray dsDiemHockyArray = dataObject.getJSONArray("ds_diem_hocky");
        
        JSONObject firstSemester = dsDiemHockyArray.getJSONObject(0);
        String dtbTichLuyHe4 = firstSemester.getString("dtb_tich_luy_he_4");
        System.out.println("The value of dtb_tich_luy_he_4 is: " + dtbTichLuyHe4);
        Ans.append("Điểm GPA: ").append(dtbTichLuyHe4);
        
                
        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_DiemGPA2 crawl = new Crawl_DiemGPA2(lsUser2, 1L);
        System.out.println("Crawl_DiemGPA: " + crawl.start());
    }

}
