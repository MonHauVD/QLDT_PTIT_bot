/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Crawler2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author TranVietDung
 */
public class Crawl_TKB_Tieptheo2
{

    private ListUser2 listUser;
    private Long chatId;

    public Crawl_TKB_Tieptheo2()
    {
    }

    public Crawl_TKB_Tieptheo2(ListUser2 listUser, Long chatId) {
        this.listUser = listUser;
        this.chatId = chatId;
    }


    public String start()
    {
        StringBuilder Ans = new StringBuilder();
        User2 user = listUser.getUserByChatId(chatId);
        String responseBody = user.getW_locdstkbtuanusertheohocky();
        if(responseBody == null)
        {
            Crawl_getDataFromQLDT crawl_getDataFromQLDT = new Crawl_getDataFromQLDT(listUser, chatId);
            String getDataString = crawl_getDataFromQLDT.start();
            user = listUser.getUserByChatId(chatId);
            responseBody = user.getW_locdstkbtuanusertheohocky();
            if(responseBody == null)
            {
                return getDataString;
            }
        }
        
        String stringDate = user.getDate_w_locdstkbtuanusertheohocky();
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
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray dsTuanTKBArray = dataObject.getJSONArray("ds_tuan_tkb");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        
//                    try
//                    {
//                        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                        today = dayFormat.parse("28/10/2024 08:58");
//                    } catch (ParseException ex)
//                    {
//                        Logger.getLogger(Crawl_TKB_Tieptheo.class.getName()).log(Level.SEVERE, null, ex);
//                    }
        System.out.println("today: " + today);
        for (int i = 0; i < dsTuanTKBArray.length(); i++)
        {
            JSONObject tkbTuanI = dsTuanTKBArray.getJSONObject(i);
            String ngayBatDau = tkbTuanI.getString("ngay_bat_dau");
//            System.out.print("\"" + ngayBatDau + "\", ");
//            System.out.println("");
            try
            {
                Date startDate = dateFormat.parse(ngayBatDau);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, 6); // End of the week
                calendar.add(Calendar.HOUR_OF_DAY, 23);
                calendar.add(Calendar.MINUTE, 59);
                calendar.add(Calendar.SECOND, 59);
                Date endDate = calendar.getTime();
                Map<Integer, String> dayMap = new HashMap<Integer, String>();
                if (today.after(startDate) && today.before(endDate))
                {
//                    System.out.println("startDate: " + startDate);
//                    System.out.println("endDate: " + endDate);
                    Ans.append("Môn học tiếp theo hôm nay (" + today.getDate() + "/" + (today.getMonth() + 1) + "):\n");
                    JSONArray tkbTuanHienTai = tkbTuanI.getJSONArray("ds_thoi_khoa_bieu");
                    for (int j = 0; j < tkbTuanHienTai.length(); j++)
                    {
                        Integer thu = tkbTuanHienTai.getJSONObject(j).getInt("thu_kieu_so");
                        Integer tietBatDau = tkbTuanHienTai.getJSONObject(j).getInt("tiet_bat_dau");
                        Integer soTiet = tkbTuanHienTai.getJSONObject(j).getInt("so_tiet");
                        Integer tietKetThuc = tietBatDau + soTiet;
                        String nhom = tkbTuanHienTai.getJSONObject(j).getString("ma_nhom");
                        String mon = tkbTuanHienTai.getJSONObject(j).getString("ten_mon");
                        String gVien = tkbTuanHienTai.getJSONObject(j).getString("ten_giang_vien");
                        String phongHoc = tkbTuanHienTai.getJSONObject(j).getString("ma_phong");
                        String gioBatDau = String.format("%02d:00", tietBatDau + 6);
                        String gioKetThuc = String.format("%02d:50", tietKetThuc + 5);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(startDate);
                        calendar2.add(Calendar.DAY_OF_MONTH, thu - 2);
                        Date thatDate = calendar2.getTime();
                        String thatDay = thatDate.getDate() + "/" + (thatDate.getMonth() + 1) + "/" + (thatDate.getYear() - 100 + 2000) + " " + gioBatDau;
//                        System.out.println("thatDay: " + thatDay);    
                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        Date timeGioBatDau = timeFormat.parse(thatDay);

//                        System.out.println("timeGioBatDau" + timeGioBatDau);
                        if (timeGioBatDau.after(today))
                        {
                            StringBuilder monNay;
                            if (!dayMap.containsKey(thu))
                            {
                                monNay = new StringBuilder();

                                monNay.append("\tTiết " + tietBatDau + "-" + tietKetThuc + " (" + gioBatDau + "-" + gioKetThuc + ")" + "\n");
                                monNay.append("\tMôn: " + mon + "\n");
                                monNay.append("\tNhóm: " + nhom + "\n");
                                monNay.append("\tGiảng viên: " + gVien + "\n");
                                monNay.append("\tPhòng : " + phongHoc + "\n\n");
                                dayMap.put(thu, monNay.toString());
                            }
                        }
                    }
                    StringBuilder tmp = new StringBuilder();
                    for (Map.Entry<Integer, String> entry : dayMap.entrySet())
                    {
                        Integer key = entry.getKey();
                        String value = entry.getValue();
                        if (today.getDay() + 1 == key)
                        {
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(startDate);
                            calendar2.add(Calendar.DAY_OF_MONTH, key - 2);
                            Date thatDate = calendar2.getTime();
                            String thatDay = thatDate.getDate() + "/" + (thatDate.getMonth() + 1);
                            tmp.append("Thứ " + key + " (" + thatDay + "):\n");
                            tmp.append(value);
                        }
                    }
                    if (tmp.isEmpty())
                    {
                        Ans = new StringBuilder("Hôm nay (" + today.getDate() + "/" + (today.getMonth() + 1) + "): không còn lịch học\n");
                    } else
                    {
                        Ans.append(tmp);
                    }
                    break;
                }

            } catch (ParseException ex)
            {
                System.err.println("Loi xu ly ngay: " + ex);
            }
        }
            
        return Ans.toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        
        Crawl_TKB_Tieptheo2 crawl = new Crawl_TKB_Tieptheo2(lsUser2, 1L);
        System.out.println("Crawl_TKB: " + crawl.start());
    }

}
