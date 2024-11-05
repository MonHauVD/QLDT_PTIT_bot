package vietdung.qldt_bottelegram.Ver1.Crawler;


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
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.RequestId;


import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QLDT_Crawl {
    private String qldtUsername;
    private String qldtPassword;
    private String requestData;

    public QLDT_Crawl()
    {
    }

    public QLDT_Crawl(String qldtUsername, String qldtPassword, String requestData)
    {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
        this.requestData = requestData;
    }

    
    
    public String start()
    {
        //Khu vực xử lý các chức năng
        if(this.requestData.equals("/diemgpa"))
        {
            Crawl_DiemGPA crawl_DiemGPA = new Crawl_DiemGPA(qldtUsername, qldtPassword);
            return crawl_DiemGPA.start();
        }
        else if(this.requestData.equals("/tkb"))
        {
            Crawl_TKB crawl_TKB = new Crawl_TKB(qldtUsername, qldtPassword);
            return crawl_TKB.start();
        }
        else if(this.requestData.equals("/check"))
        {
            Crawl_checkLogin crawl_checkLogin = new Crawl_checkLogin(qldtUsername, qldtPassword);
            return crawl_checkLogin.start();
        }
        else if(this.requestData.equals("/tkbhomnay"))
        {
            Crawl_TKBHomnay crawl_TKBHomnay = new Crawl_TKBHomnay(qldtUsername, qldtPassword);
            return crawl_TKBHomnay.start();
        }
        else if(this.requestData.equals("/tkbngaymai"))
        {
            Crawl_TKBNgaymai crawl_TKBNgaymai = new Crawl_TKBNgaymai(qldtUsername, qldtPassword);
            return crawl_TKBNgaymai.start();
        }
        else if(this.requestData.equals("/tkbtieptheo"))
        {
            Crawl_TKB_Tieptheo crawl_TKB_Tieptheo = new Crawl_TKB_Tieptheo(qldtUsername, qldtPassword);
            return crawl_TKB_Tieptheo.start();
        }
        return "QLDT_Crawl chuc nang dang cap nhat: " + requestData;
    }
    
    
    
    public static void main(String[] args) {
        // Chay de test rieng chuc nang nay
        QLDT_Crawl crawl = new QLDT_Crawl("qldtUsername", "qldtPassword", "requestData");

        System.out.println("QLDT_Crawl: " + crawl.start());
    }

}
