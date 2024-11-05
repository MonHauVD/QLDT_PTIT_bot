/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Crawler;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * @author TranVietDung
 */
public class Crawl_TKBHomnay
{

    private String qldtUsername;
    private String qldtPassword;

    public Crawl_TKBHomnay()
    {
    }

    public Crawl_TKBHomnay(String qldtUsername, String qldtPassword)
    {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
    }

    public String start()
    {
        final int myTimeOut = 20;
        StringBuilder[] Ans =
        {
            new StringBuilder()
        };
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
                if (responseUrl.contains("w-locdstkbtuanusertheohocky"))
                {
                    System.out.println("Url: " + responseUrl);
                    System.out.println("Response headers: " + responseReceived.getResponse().getHeaders().toString());
                    System.out.println("Response status: " + responseReceived.getResponse().getStatus());
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    System.out.println("Response body: " + responseBody);
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dsTuanTKBArray = dataObject.getJSONArray("ds_tuan_tkb");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date today = new Date();
                    for (int i = 0; i < dsTuanTKBArray.length(); i++)
                    {
                        JSONObject tkbTuanI = dsTuanTKBArray.getJSONObject(i);
                        String ngayBatDau = tkbTuanI.getString("ngay_bat_dau");
//                        System.out.print("\"" + ngayBatDau + "\", ");
//                        System.out.println("");
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
                                Ans[0].append("Thời khóa biểu hôm nay (" + startDate.getDate() + "/" + (startDate.getMonth() + 1) + "):\n");
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

                                    StringBuilder monNay;
                                    if (dayMap.containsKey(thu))
                                    {
                                        monNay = new StringBuilder(dayMap.get(thu));
                                    } else
                                    {
                                        monNay = new StringBuilder();
                                    }

                                    monNay.append("\tTiết " + tietBatDau + "-" + tietKetThuc + " (" + gioBatDau + "-" + gioKetThuc + ")" + "\n");
                                    monNay.append("\tMôn: " + mon + "\n");
                                    monNay.append("\tNhóm: " + nhom + "\n");
                                    monNay.append("\tGiảng viên: " + gVien + "\n");
                                    monNay.append("\tPhòng : " + phongHoc + "\n\n");
                                    dayMap.put(thu, monNay.toString());
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
                                    Ans[0] = new StringBuilder("Hôm nay (" + startDate.getDate() + "/" + (startDate.getMonth() + 1) + "): không có lịch học\n");
                                } else
                                {
                                    Ans[0].append(tmp);
                                }
                                break;
                            }

                        } catch (ParseException ex)
                        {
                            System.err.println("Loi xu ly ngay: " + ex);
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
                    // Doi load trang
//                    wait.until(ExpectedConditions.urlContains("/userinfo"));
//                    WebElement infoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Thông tin sinh viên')]")));
//                    Thread.sleep(1000);
//                    System.out.println("An nut xem thoi khoa bieu!");
//                    WebElement xemTKBButton = driver.findElement(By.id("WEB_TKB_1TUAN"));
//                    xemTKBButton.click();
//                    wait.until(ExpectedConditions.urlContains("tkb-tuan"));

                    driver.get("https://qldt.ptit.edu.vn/#/tkb-tuan");
                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlContains("tkb-tuan"));
                    
                    // Wait for a moment before logging out
                    Thread.sleep(2000); // Optional wait time before logging out
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
        Crawl_TKBHomnay crawl = new Crawl_TKBHomnay ("B21DCCNXXX", "12345678");
        System.out.println("Crawl_TKB: " + crawl.start());
    }

}
