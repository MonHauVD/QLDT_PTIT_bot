package vietdung.qldt_bottelegram.Ver2.Crawler2;


/**
 *
 * @author TranVietDung
 */

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QLDT_Crawl2 {
    private ListUser2 listUser;
    private Long chatId;
    private String requestData;

    public QLDT_Crawl2()
    {
    }
    
    public QLDT_Crawl2(ListUser2 listUser, Long chatId, String requestData) {
        this.listUser = listUser;
        this.chatId = chatId;
        this.requestData = requestData;
    }

    public String start()
    {
        //Khu vực xử lý các chức năng
        if(this.requestData.equals("/capnhat"))
        {
            Crawl_updateFromQLDT crawl_updateFromQLDT = new Crawl_updateFromQLDT(listUser, chatId);
            return crawl_updateFromQLDT.start();
        }
        else if(this.requestData.equals("/diemgpa"))
        {
            Crawl_DiemGPA2 crawl_DiemGPA = new Crawl_DiemGPA2(listUser, chatId);
            return crawl_DiemGPA.start();
        }
        else if(this.requestData.equals("/tkb"))
        {
            Crawl_TKB2 crawl_TKB = new Crawl_TKB2(listUser, chatId);
            return crawl_TKB.start();
        }
        else if(this.requestData.equals("/check"))
        {
            return "checked!";
        }
        else if(this.requestData.equals("/tkbhomnay"))
        {
            Crawl_TKBHomnay2 crawl_TKBHomnay = new Crawl_TKBHomnay2(listUser, chatId);
            return crawl_TKBHomnay.start();
        }
        else if(this.requestData.equals("/tkbngaymai"))
        {
            Crawl_TKBNgaymai2 crawl_TKBNgaymai = new Crawl_TKBNgaymai2(listUser, chatId);
            return crawl_TKBNgaymai.start();
        }
        else if(this.requestData.equals("/tkbtieptheo"))
        {
            Crawl_TKB_Tieptheo2 crawl_TKB_Tieptheo = new Crawl_TKB_Tieptheo2(listUser, chatId);
            return crawl_TKB_Tieptheo.start();
        }
        else if(this.requestData.equals("/lichthi"))
        {
            Crawl_lichThi crawl_lichThi = new Crawl_lichThi(listUser, chatId);
            return crawl_lichThi.start();
        }
        else if(this.requestData.equals("/hocphichuanop"))
        {
            Crawl_hocPhiChuaNop crawl_hocCrawl_hocPhiChuaNop = new Crawl_hocPhiChuaNop(listUser, chatId);
            return crawl_hocCrawl_hocPhiChuaNop.start();
        }
        else if(this.requestData.equals("/hocphi"))
        {
            Crawl_hocPhi crawl_hoCrawl_hocPhi = new Crawl_hocPhi(listUser, chatId);
            return crawl_hoCrawl_hocPhi.start();
        }
        else if(this.requestData.equals("/thongtincovanhoctap"))
        {
            Crawl_thongTinCoVanHocTap crawl_thCrawl_thongTinCoVanHocTap = new Crawl_thongTinCoVanHocTap(listUser, chatId);
            return crawl_thCrawl_thongTinCoVanHocTap.start();
        }
        else if(this.requestData.equals("/danhsachmondahoc"))
        {
            Crawl_layDanhSachMonDaHoc crawl_lCrawl_layDanhSachMonDaHoc = new Crawl_layDanhSachMonDaHoc(listUser, chatId);
            return crawl_lCrawl_layDanhSachMonDaHoc.start();
        }
        return "QLDT_Crawl chuc nang dang cap nhat: " + requestData;
    }
    
    
    
    public static void main(String[] args) {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "qldtUsername", "qldtPassword"));
        QLDT_Crawl2 crawl = new QLDT_Crawl2(lsUser2, 1L, "requestData");

        System.out.println("QLDT_Crawl: " + crawl.start());
    }

}
