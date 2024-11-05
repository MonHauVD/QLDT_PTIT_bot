/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Crawler;

import java.io.File;
import java.time.Duration;
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

/**
 *
 * @author DELL
 */
public class Crawl_DiemMonHoc {
    private String qldtUsername;
    private String qldtPassword;
    private String subjectName;

    public Crawl_DiemMonHoc() {
    }

    public Crawl_DiemMonHoc(String qldtUsername, String qldtPassword, String subjectName) {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
        this.subjectName = subjectName;
    }

    public String start()
    {
        final int myTimeOut = 20;
        StringBuilder[] Ans = {new StringBuilder()};
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        StringBuilder bearerStr = new StringBuilder();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Bat che do khong cua so
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addExtensions(new File("chromedriver-win64\\MNMNMCMDKIGAKHLFKCDIMGHNDNMOMFEO_1_0_7_0.crx"));
        WebDriver driver = new ChromeDriver(options);
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        //catch error
        CompletableFuture<Boolean> accessQLDTCheckFuture = new CompletableFuture<>();
        CompletableFuture<Boolean> loginCheckFuture = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture = new CompletableFuture<>();
        devTools.addListener(Network.responseReceived(), responseReceived ->
        {
            try
            {
                String responseUrl = responseReceived.getResponse().getUrl();
                RequestId requestId = responseReceived.getRequestId();
                if (responseUrl.contains("qldt.ptit.edu.vn"))
                {
                    int homeResponse = responseReceived.getResponse().getStatus();
                    if (homeResponse == 200 || homeResponse == 304)
                    {
//                        System.out.println("truy cap web QLDT thanh cong!");
                        accessQLDTCheckFuture.complete(true);
                    } else
                    {
                        accessQLDTCheckFuture.complete(Boolean.FALSE);
                    }
                }
                if (responseUrl.contains("api/auth/login"))
                {

                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    if (responseReceived.getResponse().getStatus().compareTo(400) == 0)
                    {
                        System.out.println("login không thanh cong!");
                        loginCheckFuture.complete(false);
                    } else
                    {
                        System.out.println("login thanh cong!");
                        loginCheckFuture.complete(true);
                    }
                }
                if (responseUrl.contains("w-locdsdiemsinhvien?hien_thi_mon_theo_hkdk"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    //System.out.println("Response body: " + responseBody);
                    
                    JSONObject jsonObject = new JSONObject(responseBody);
                    // xu ly json lay diem
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dsDiemHocKyArray = dataObject.getJSONArray("ds_diem_hocky");
                    
                    System.out.println(dsDiemHocKyArray.toString());

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
                                        Ans[0].append("Điểm môn " + tenMon + " chưa được cập nhật!\n");
                                    } 
                                    else
                                    {
                                        Double diemGiuaKi = subject.getDouble("diem_giua_ky");
                                        Double diemThi = subject.getDouble("diem_thi");
                                        Double diemTK = subject.getDouble("diem_tk");
                                        String diemTKChu = subject.getString("diem_tk_chu");
                                        Double diemTKSo = subject.getDouble("diem_tk_so");

                                        Ans[0].append("Điểm môn " + tenMon + ":\n");
                                        Ans[0].append("\tĐiểm giữa kỳ: " + diemGiuaKi + "\n");
                                        Ans[0].append("\tĐiểm thi: " + diemThi + "\n");
                                        Ans[0].append("\tĐiểm tổng kết: " + diemTK + "\n");
                                        Ans[0].append("\tĐiểm tổng kết chữ: " + diemTKChu + "\n");
                                        Ans[0].append("\tĐiểm tổng kết số: " + diemTKSo + "\n");
                                    }
                                }
                            }
                            if(ok == 1)
                                break;
                        }
                        if(ok == 0) {
                            Ans[0].append("Không tìm thấy môn " + subjectName + "!\n");
                        }
                    }
                    doneCheckFuture.complete(true);
                }
            } catch (Exception e)
            {
                System.out.println("error: " + e);
            }
        });

        try
        {

            // Navigate to the login page
            driver.get("https://qldt.ptit.edu.vn/#/home");
            // Wait for the username field to be visible and enter credentials
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(myTimeOut));

            boolean accessQLDTSuccessfull = accessQLDTCheckFuture.get(myTimeOut, TimeUnit.SECONDS);
            if (accessQLDTSuccessfull)
            {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))); // Adjust if necessary
                Thread.sleep(2000);
                // Find the username and password input fields
                WebElement usernameField = driver.findElement(By.name("username")); // Use the actual locator for the username input
                WebElement passwordField = driver.findElement(By.name("password")); // Use the actual locator for the password input

                // Enter the credentials
                usernameField.sendKeys(qldtUsername);
                passwordField.sendKeys(qldtPassword);

                // Find and click the login button
                WebElement loginButton = driver.findElement(By.xpath("//button[contains(@class, 'btn btn-primary') and contains(text(), 'Đăng nhập')]"));
                loginButton.click();

                // Wait for redirection to the user info page
                boolean loginSuccessful = loginCheckFuture.get(myTimeOut, TimeUnit.SECONDS);
                if (loginSuccessful == false)
                {
                    Ans[0] = new StringBuilder("Đăng nhập không thành công. Vui lòng kiểm tra tên đăng nhập và mật khẩu.");
                } else
                {
//                    // Doi load trang
//                    wait.until(ExpectedConditions.urlContains("/userinfo"));
//                    WebElement infoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Thông tin sinh viên')]")));
//                    Thread.sleep(1000);
//                    
//                    WebElement xemDiemButton = driver.findElement(By.id("WEB_DIEM"));
//                    xemDiemButton.click();
                    driver.get("https://qldt.ptit.edu.vn/#/diem");
                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlContains("diem"));

                    // Wait for a moment before logging out
//                    Thread.sleep(2000); // Optional wait time before logging out
                    boolean doneSuccessful = doneCheckFuture.get(myTimeOut, TimeUnit.SECONDS);
                    // Find and click the logout button
                    WebElement logoutButton = driver.findElement(By.xpath("//button[contains(@class, 'btn btn-warning') and contains(text(), 'Đăng xuất')]"));
                    logoutButton.click();

                    // Wait for the logout process to complete (adjust based on behavior)
                    wait.until(ExpectedConditions.urlContains("/home")); // Adjust based on the expected URL after logout

                    System.out.println("Logged out successfully.");
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Trang QLDT không truy cập được, xin vui lòng thử lại sau!");
            Ans[0] = new StringBuilder("Trang QLDT không truy cập được, xin vui lòng thử lại sau!");
        } finally
        {
            // Stop the proxy and close the browser
            driver.quit();
        }
//        System.out.println("Crawl_DiemGPA: " + Ans[0].toString());
        return Ans[0].toString();
    }

    public static void main(String[] args)
    {
        // Chay de test rieng chuc nang nay
        Crawl_DiemMonHoc crawl = new Crawl_DiemMonHoc("B21DCCNXXX", "12345", "Lập trình mạng");
        System.out.println("Crawl_DiemMonHoc: " + crawl.start());
    }
}
