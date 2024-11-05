package vietdung.qldt_bottelegram.Ver2.Crawler2;

import vietdung.qldt_bottelegram.Ver2.Model2.ListUser2;
import vietdung.qldt_bottelegram.Ver2.Model2.User2;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
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
import java.util.Date;
import java.util.List;
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

public class Crawl_getDataFromQLDT {
    private ListUser2 listUser;
    private Long chatId;

    public Crawl_getDataFromQLDT() {
    }

    public Crawl_getDataFromQLDT(ListUser2 listUser, Long chatId) {
        this.listUser = listUser;
        this.chatId = chatId;
    }

    public String start()
    {
        StringBuilder[] Ans = {new StringBuilder()};
        final int myTimeOut = 30;
        // Thư mục gốc để lưu trữ các profile
        String rootProfilePath = "profileTemp";
        File rootProfileDir = new File(rootProfilePath);
        
        // Kiểm tra và tạo thư mục gốc nếu chưa tồn tại
        try {
            rootProfileDir = new File(rootProfilePath).getCanonicalFile();
            if (!rootProfileDir.exists()) {
                boolean dirCreated = rootProfileDir.mkdirs();
                if (!dirCreated) {
                    System.out.println("Không thể tạo thư mục gốc cho profile, xin kiểm tra quyền ghi của hệ thống!");
                    throw new Exception("Không thể tạo thư mục gốc cho profile, xin kiểm tra quyền ghi của hệ thống!");
                }
            }
        } catch (Exception e) {
        }
        // Tạo thư mục profile ngẫu nhiên bên trong "D:/this_folder"
        File randomProfileDir = createRandomProfileDir(rootProfileDir);

        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        StringBuilder bearerStr = new StringBuilder();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Bat che do khong cua so
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        try {
            if (randomProfileDir != null) {
                options.addArguments("user-data-dir=" + randomProfileDir.getAbsolutePath());
            } else {
                System.out.println("Không thể tạo thư mục profile ngẫu nhiên!");
                throw new Exception ("Không thể tạo thư mục profile ngẫu nhiên!");
            }
        } catch (Exception e) {
        }

        options.addArguments("--disable-application-cache");
        // options.addArguments("--incognito");
        options.addExtensions(new File("chromedriver-win64\\MNMNMCMDKIGAKHLFKCDIMGHNDNMOMFEO_1_0_7_0.crx"));
        WebDriver driver = new ChromeDriver(options);
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        //catch error
        CompletableFuture<Boolean> accessQLDTCheckFuture = new CompletableFuture<>();
        CompletableFuture<Boolean> loginCheckFuture = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locsinhvieninfo = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdshoadondientu = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdstkbtuanusertheohocky = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdstkbhockytheodoituong = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdslichthisvtheohocky = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdsdiemsinhvien = new CompletableFuture<>();
        CompletableFuture<Boolean> doneCheckFuture_w_locdstonghophocphisv = new CompletableFuture<>();
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
                if (responseUrl.contains("w-locsinhvieninfo"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locsinhvieninfo(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locsinhvieninfo.complete(true);
                    System.out.println("get w-locsinhvieninfo successful!\n");
                    Ans[0].append("get w-locsinhvieninfo successful!\n");
                }
                if (responseUrl.contains("w-locdshoadondientu"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdshoadondientu(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdshoadondientu.complete(true);
                    System.out.println("get w-locdshoadondientu successful!\n");
                    Ans[0].append("get w-locdshoadondientu successful!\n");
                }
                if (responseUrl.contains("w-locdstkbtuanusertheohocky"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdstkbtuanusertheohocky(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdstkbtuanusertheohocky.complete(true);
                    System.out.println("get w-locdstkbtuanusertheohocky successful!\n");
                    Ans[0].append("get w-locdstkbtuanusertheohocky successful!\n");
                }
                if (responseUrl.contains("w-locdstkbhockytheodoituong"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdstkbhockytheodoituong(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdstkbhockytheodoituong.complete(true);
                    System.out.println("get w-locdstkbhockytheodoituong successful!\n");
                    Ans[0].append("get w-locdstkbhockytheodoituong successful!\n");
                }
                if (responseUrl.contains("w-locdslichthisvtheohocky"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdslichthisvtheohocky(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdslichthisvtheohocky.complete(true);
                    System.out.println("get w-locdslichthisvtheohocky successful!\n");
                    Ans[0].append("get w-locdslichthisvtheohocky successful!\n");
                }
                if (responseUrl.contains("w-locdsdiemsinhvien"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdsdiemsinhvien(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdsdiemsinhvien.complete(true);
                    System.out.println("get w-locdsdiemsinhvien successful!\n");
                    Ans[0].append("get w-locdsdiemsinhvien successful!\n");
                }
                if (responseUrl.contains("w-locdstonghophocphisv"))
                {   
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    Date today = new Date();
                    listUser.update_w_locdstonghophocphisv(chatId, responseBody, today.toString());
                    doneCheckFuture_w_locdstonghophocphisv.complete(true);
                    System.out.println("get w-locdstonghophocphisv successful!\n");
                    Ans[0].append("get w-locdstonghophocphisv successful!\n");
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
                User2 user = listUser.getUserByChatId(chatId);
                usernameField.sendKeys(user.getQldtUsername());
                passwordField.sendKeys(user.getQldtPassword());

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

                    // driver.get("https://qldt.ptit.edu.vn/#/home");
                    // driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlContains("home"));

                    if(user.getW_locsinhvieninfo() == null)
                    {
                        boolean doneSuccessful_w_locsinhvieninfo_ = doneCheckFuture_w_locsinhvieninfo.get(myTimeOut, TimeUnit.SECONDS);
                    }
                    
                    if(user.getW_locdstkbtuanusertheohocky() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/tkb-tuan");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("tkb-tuan"));

                        boolean doneSuccessful_w_locdstkbtuanusertheohocky_ = doneCheckFuture_w_locdstkbtuanusertheohocky.get(myTimeOut, TimeUnit.SECONDS);
                    }

                    if(user.getW_locdsdiemsinhvien() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/diem");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("diem"));

                        boolean doneSuccessful_w_locdsdiemsinhvien_ = doneCheckFuture_w_locdsdiemsinhvien.get(myTimeOut, TimeUnit.SECONDS);
                    }

                    if(user.getW_locdshoadondientu() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/hoadondientu");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("hoadondientu"));

                        boolean doneSuccessful_w_locdshoadondientu_ = doneCheckFuture_w_locdshoadondientu.get(myTimeOut, TimeUnit.SECONDS);
                    }

                    if(user.getW_locdstkbhockytheodoituong() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/tkb-hocky");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("tkb-hocky"));

                        boolean doneSuccessful_w_locdstkbhockytheodoituong_ = doneCheckFuture_w_locdstkbhockytheodoituong.get(myTimeOut, TimeUnit.SECONDS);
                    }

                    if(user.getW_locdslichthisvtheohocky() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/lichthi");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("lichthi"));

                        boolean doneSuccessful_w_locdslichthisvtheohocky_ = doneCheckFuture_w_locdslichthisvtheohocky.get(myTimeOut, TimeUnit.SECONDS);
                    }

                    if(user.getW_locdstonghophocphisv() == null)
                    {
                        driver.get("https://qldt.ptit.edu.vn/#/hocphi");
                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlContains("hocphi"));

                        boolean doneSuccessful_w_locdstonghophocphisv_ = doneCheckFuture_w_locdstonghophocphisv.get(myTimeOut, TimeUnit.SECONDS);
                    }
                   

                    


                    // Find and click the logout button
                    WebElement logoutButton = driver.findElement(By.xpath("//button[contains(@class, 'btn btn-warning') and contains(text(), 'Đăng xuất')]"));
                    logoutButton.click();

                    // Wait for the logout process to complete (adjust based on behavior)
                    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
                    wait2.until(ExpectedConditions.urlContains("/home")); // Adjust based on the expected URL after logout

                    System.out.println("Logged out successfully.");
                    Ans[0].append("Logged out successfully.");
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Trang QLDT không truy cập được, xin vui lòng thử lại sau!");
            Ans[0].append("Trang QLDT không truy cập được, xin vui lòng thử lại sau!");
        } finally
        {
            // Stop the proxy and close the browser
            driver.quit();
            deleteDirectory(randomProfileDir);
        }
//        System.out.println("Crawl_DiemGPA: " + Ans[0].toString());
        return Ans[0].toString();
    }

     // Phương thức tạo thư mục tạm ngẫu nhiên cho profile bên trong thư mục gốc
    public static File createRandomProfileDir(File rootDir) {
        try {
            // Tạo một thư mục con ngẫu nhiên bên trong thư mục gốc
            Path tempProfilePath = Files.createTempDirectory(rootDir.toPath(), "chrome_profile_");
            File tempProfileDir = tempProfilePath.toFile();
            System.out.println("Đã tạo thư mục profile ngẫu nhiên: " + tempProfileDir.getAbsolutePath());
            return tempProfileDir;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức xóa thư mục sau khi kết thúc
    public static void deleteDirectory(File directory){
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        if (!directory.delete()) {
            System.out.println("Không thể xóa thư mục: " + directory.getAbsolutePath());
        }
    }


    public static void main(String[] args) {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "B21DCCNXXX", "12345678"));
        Crawl_getDataFromQLDT crawl = new Crawl_getDataFromQLDT (lsUser2, 1L);
        System.out.println(crawl.start());
        // System.out.println("Crawl_updateFromQLDT: " + lsUser2.getUserByChatId(1L).printDetail());
    }
    
}
