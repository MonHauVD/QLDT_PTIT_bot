/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Crawler;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.RequestId;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author TranVietDung
 */
public class Crawl_checkLogin
{

    private String qldtUsername;
    private String qldtPassword;

    public Crawl_checkLogin()
    {
    }

    public Crawl_checkLogin(String qldtUsername, String qldtPassword)
    {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
    }
    
    public void testing()
    {
        final int myTimeOut = 20;
        StringBuilder[] Ans = {new StringBuilder()};
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        StringBuilder bearerStr = new StringBuilder();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Bat che do khong cua so
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addExtensions(new File("chromedriver-win64\\MNMNMCMDKIGAKHLFKCDIMGHNDNMOMFEO_1_0_7_0.crx"));
        WebDriver driver = new ChromeDriver(options);
        try
        {

            // Navigate to the login page
            driver.get("https://www.google.com.vn/?hl=vi");
        }
        catch(Exception e)
        {
        
        }
            
    }
    public String start()
    {
//        boolean isLoginSuccess = true;
//        boolean isError = false;
//        if(isLoginSuccess)
//        {
//            return "Tài khoản chính xác!";
//        }
//        else if (isError)
//        {
//            return "Lỗi trang QLDT! Vui lòng thử lại sau!";
//        }
//        else
//        {
//            return "Tài khoản bị sai! Vui lòng gõ /login để nhập lại tài khoản!";
//        }
        return "Chuc nang nay tam thoi chua hoat dong!";
    }
    
    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        Crawl_checkLogin crawl = new Crawl_checkLogin("qldtUsername", "qldtPassword");
        System.out.println("Crawl_checkLogin: " + crawl.start());
        
        crawl.testing();
    }
    
}
