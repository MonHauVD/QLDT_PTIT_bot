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
public class Crawl_hocPhiChuaNop
{
    private ListUser2 listUser;
    private Long chatId;

    public Crawl_hocPhiChuaNop()
    {
    }

    public Crawl_hocPhiChuaNop(ListUser2 listUser, Long chatId) {
        this.listUser = listUser;
        this.chatId = chatId;
    }

    public String start()
    {
        StringBuilder Ans = new StringBuilder();
        User2 user = listUser.getUserByChatId(chatId);
        String responseBody = user.getW_locdstonghophocphisv();
        if(responseBody == null)
        {
            Crawl_getDataFromQLDT crawl_getDataFromQLDT = new Crawl_getDataFromQLDT(listUser, chatId);
            String getDataString = crawl_getDataFromQLDT.start();
            user = listUser.getUserByChatId(chatId);
            responseBody = user.getW_locdstonghophocphisv();
            if(responseBody == null)
            {
                return getDataString;
            }
        }

        String stringDate = user.getDate_w_locdstonghophocphisv();
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
        Ans.append("Danh sách học phí chưa nộp:\n");
        boolean conKiHocNaoNoKhong = false;
        JSONObject jsonObject = new JSONObject(responseBody);
        // Navigate through the JSON structure to get to "dtb_tich_luy_he_4"
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray ds_hoc_phi_hoc_kyArray = dataObject.getJSONArray("ds_hoc_phi_hoc_ky");
        for (int i = 0; i < ds_hoc_phi_hoc_kyArray.length(); i++)
        {
            JSONObject hocPhiKiThuI = ds_hoc_phi_hoc_kyArray.getJSONObject(i);
            String con_no = hocPhiKiThuI.getString("con_no");
            Long con_no_value = Long.parseLong(con_no);
            if(con_no.compareTo("0") != 0)
            {
                conKiHocNaoNoKhong = true;
                String ten_hoc_ky = hocPhiKiThuI.getString("ten_hoc_ky");
                String hoc_phi = hocPhiKiThuI.getString("hoc_phi");
                String mien_giam = hocPhiKiThuI.getString("mien_giam");
                String tong_hoc_bong = hocPhiKiThuI.getString("tong_hoc_bong");
                String phai_thu = hocPhiKiThuI.getString("phai_thu");
                String don_gia = hocPhiKiThuI.getString("don_gia");
                
                Ans.append((i + 1) + ". " + ten_hoc_ky + ":\n");
                Ans.append("Học phí: " + hoc_phi + "\n");
                Ans.append("Miễn giảm: " + mien_giam + "\n");
                Ans.append("Tổng học bổng: " + tong_hoc_bong + "\n");
                Ans.append("Phải thu: " + phai_thu + "\n");
                Ans.append("Đơn giá (1 tín): " + don_gia + "\n\n");
            }
        }
        if(!conKiHocNaoNoKhong)
        {
            Ans.append("Học phí đã đóng đủ!");
        }
                
        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_hocPhiChuaNop crawl = new Crawl_hocPhiChuaNop(lsUser2, 1L);
        System.out.println("Crawl_hocPhiChuaNop: " + crawl.start());
    }

}
