/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Crawler2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.RequestId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import vietdung.qldt_bottelegram.Ver2.Model2.ListUser2;
import vietdung.qldt_bottelegram.Ver2.Model2.User2;

/**
 *
 * @author DELL
 */
public class Crawl_DiemMonHoc2 {
    private ListUser2 listUser;
    private Long chatId;
    private String subjectName;

    public Crawl_DiemMonHoc2() {
    }

    public Crawl_DiemMonHoc2(ListUser2 listUser, Long chatId, String subjectName) {
        this.listUser = listUser;
        this.chatId = chatId;
        this.subjectName = subjectName;
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
        // xu ly json lay diem
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray dsDiemHocKyArray = dataObject.getJSONArray("ds_diem_hocky");
        
        // System.out.println(dsDiemHocKyArray.toString());

        if (dsDiemHocKyArray.length() > 0)
        {
            int ok = 0;
            for(int i = 0; i < dsDiemHocKyArray.length(); i++) 
            {
                JSONObject semester = dsDiemHocKyArray.getJSONObject(i);
                JSONArray dsDiemMonHocArray = semester.getJSONArray("ds_diem_mon_hoc");
                for(int j = 0; j < dsDiemMonHocArray.length(); j++) 
                {
                    JSONObject subject = dsDiemMonHocArray.getJSONObject(j);
                    String tenMon = subject.getString("ten_mon");
                    if(subjectName.equals(tenMon)) {
                        ok = 1;
                        
                        if(subject.getString("diem_giua_ky").isEmpty())
                        {
                            Ans.append("Điểm môn " + tenMon + " chưa được cập nhật!\n");
                        } 
                        else
                        {
                            Double diemGiuaKi = subject.getDouble("diem_giua_ky");
                            Double diemThi = subject.getDouble("diem_thi");
                            Double diemTK = subject.getDouble("diem_tk");
                            String diemTKChu = subject.getString("diem_tk_chu");
                            Double diemTKSo = subject.getDouble("diem_tk_so");

                            Ans.append("Điểm môn " + tenMon + ":\n");
                            Ans.append("\tĐiểm giữa kỳ: " + diemGiuaKi + "\n");
                            Ans.append("\tĐiểm thi: " + diemThi + "\n");
                            Ans.append("\tĐiểm tổng kết: " + diemTK + "\n");
                            Ans.append("\tĐiểm tổng kết chữ: " + diemTKChu + "\n");
                            Ans.append("\tĐiểm tổng kết số: " + diemTKSo + "\n");
                        }
                    }
                }
                if(ok == 1)
                    break;
            }
            if(ok == 0) {
                Ans.append("Không tìm thấy môn " + subjectName + "!\n");
            }
        }
        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_DiemMonHoc2 crawl = new Crawl_DiemMonHoc2(lsUser2, 1L, "Lập trình mạng");
        System.out.println("Crawl_DiemMonHoc: " + crawl.start());
    }
}
