package vietdung.qldt_bottelegram.Ver2.Bot2;

import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;
import java.util.Map;
import vietdung.qldt_bottelegram.Ver2.Crawler2.QLDT_Crawl2;
import vietdung.qldt_bottelegram.Ver2.Crawler2.QLDT_CrawlDiem2;
import vietdung.qldt_bottelegram.Ver2.Model2.ListUser2;
import vietdung.qldt_bottelegram.Ver2.Model2.User2;

public class BotTelegram2 extends TelegramLongPollingBot
{
    private Map<Long, String> stateLogin = new HashMap<>();
    private Map<Long, String> stateChat = new HashMap<>();
    private Map<Long, String> stateFirstTime = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    static ListUser2 lsUser;

    public BotTelegram2()
    {
        lsUser = new ListUser2();
    }

    @Override
    public String getBotUsername()
    {
        // return "QLDT_036564639_bot";
        // return "QLDT_PTIT_bot";
        return "qldtptit_bot";
    }

    @Override
    public String getBotToken()
    {
        // return "7876220735:AAG_kArH7O20BU0-O1UHh8eNQfkNTto3xQM";
        // return "7815332010:AAGUXZESnv1mNudOpRh340cPNN60Fx6Cgq8";
        return "7876293267:AAGYSeloSjlnt6E7lSbEwCq3Wc9RpJYOeHg";
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        var msg = update.getMessage();
        var user = msg.getFrom();
        Long chatId = user.getId();
        String isStatePass = stateLogin.get(chatId);
        if(isStatePass != null && isStatePass.equals("WAITING_FOR_PASSWORD"))
        {
            int lenPass = msg.getText().length();
            StringBuilder sao = new StringBuilder();
            for(int i = 0; i < lenPass; i++)
            {
                sao.append("*");
            }
            System.out.println("ChatID: " + chatId + "\tUser: " + user.getUserName() + "\t" + sao.toString());
        }
        else
        System.out.println("ChatID: " + chatId + "\tUser: " + user.getUserName() + "\t" + msg.getText());
        lsUser.addUserIfNotExist(new User2(chatId, user.getUserName()));

//        sendText(id, msg.getText()); // Echo bot

        

        if (msg.isCommand() && !stateLogin.containsKey(chatId))
        {
            String requestStr = msg.getText().toLowerCase();
            if (requestStr.equals("/login"))
            {
                sendText(chatId, "Nhập Username của bạn:");

                // Lưu trạng thái đăng nhập cho người dùng, có thể sử dụng HashMap để lưu trạng thái
                stateLogin.put(chatId, "WAITING_FOR_USERNAME");
                return;
            }
            else if (requestStr.equals("/help"))
            {
                //Khu vuc them huong dan cau lenh cho cac chuc nang
                String huongDan = "Các cú pháp nhắn:\n"
                        + "/help : Để nhận hướng dẫn.\n"
                        + "/login : Để nhập tài khoản QLĐT.\n"
//                        + "/check : Để kiểm tra tài khoản có đăng nhập thành công không.\n"  
                        + "/capnhat : Để cập nhật dữ liệu từ QLĐT.\n"                      
                        + "/diemgpa : Để lấy điểm GPA.\n"
                        + "/tkb: Để lấy thời khóa biểu tuần.\n"
                        + "/tkbhomnay : Lấy TKB hôm nay.\n"
                        + "/tkbngaymai : Lấy TKB ngày mai.\n"
                        + "/tkbtieptheo : Lấy TKB môn tiếp theo gần nhất trong ngày.\n"
                        + "/diemmonhoc : Lấy điểm môn học.\n"
                        + "/diemgpatheohocki : Lấy điểm gpa theo học kì.\n"
                        + "/lichthi : Lấy lịch thi\n"
                        + "/hocphichuanop: Kiểm tra học phí chưa nộp\n"
                        + "/hocphi : Xem lại học phí của tất cả các kì học\n"
                        + "/thongtincovanhoctap : Lấy thông tin cố vấn học tập\n"
                        + "/danhsachmondahoc : Lấy danh sách môn học\n";
                sendText(chatId, huongDan);
                return;
            } else if (requestStr.equals("/start"))
            {
                String startStr = "Chào mừng bạn đến với QLDT Bot Telegram!\n"
                        + "Để nhận hướng dẫn, nhắn /help";
                sendText(chatId, startStr);
                return;
            }
            else
            {   
//                if(!stateChat.containsKey(chatId))
//                    stateChat.put(chatId, msg.getText());
//                String state = stateLogin.get(chatId);
//                stateChat.remove(chatId);
                if(checkingHadAccQLDT(chatId))
                {
                    //Khu vuc them cac chuc nang
                    if(!stateFirstTime.containsKey(chatId))
                    {
                        stateFirstTime.put(chatId, "First time");
                        sendText(chatId, "Vì trang web thường phản hồi chậm. Vui lòng đợi trong ít phút!");
                    }
                    if (requestStr.equals("/diemmonhoc")) {
                        sendText(chatId, "Nhập tên môn học:");
                        // Lưu trạng thái chon xem diem mon, có thể sử dụng HashMap để lưu trạng thái
                        stateChat.put(chatId, "WAITING_FOR_SUBJECT_NAME");
                        
                        
                        return;
                    } else if (requestStr.equals("/diemgpatheohocki")) 
                    {
                        
                        sendText(chatId, "Nhập năm học dạng yyyy-yyyy: ");
                        stateChat.put(chatId, "WAITING_FOR_YEAR");
                       
                        return;
                    }
                    else if (requestStr.equals("/capnhat")) 
                    {
                        sendText(chatId, "Vì trang web thường phản hồi chậm. Vui lòng đợi trong ít phút!");
                        executorService.submit(() -> {
                            sendText(chatId, CrawlData(chatId, requestStr));
                        });
                    }
                    else
                    {
                        
                        executorService.submit(() -> {
                            sendText(chatId, CrawlData(chatId, requestStr));
                        });
                    }
                }
                
                
                
            }

            return;
        }

        if (stateLogin.containsKey(chatId))
        {
            String state = stateLogin.get(chatId);          

            if (state.equals("WAITING_FOR_USERNAME"))
            {
                // Lấy Username từ người dùng
                String username = msg.getText();
                lsUser.updateQldtUsername(chatId, username);
                sendText(chatId, "Nhập Password của bạn:");

                stateLogin.put(chatId, "WAITING_FOR_PASSWORD");
            } else if (state.equals("WAITING_FOR_PASSWORD"))
            {
                stateLogin.remove(chatId);
                // Lấy Password từ người dùng
                String password = msg.getText();
                lsUser.updateQldtPassword(chatId, password);
                System.out.println("User da nhap username va pass thanh cong: " + lsUser.getUserByChatId(chatId));
//                System.out.println("LsUser: " + lsUser);
                sendText(chatId, "Bạn đã nhập thành công tài khoản!");
            }
        }
            
        if (stateChat.containsKey(chatId))
        {
            String statechat = stateChat.get(chatId);          

            if (statechat.equals("WAITING_FOR_SUBJECT_NAME"))
            {
                String subjectName = msg.getText();

                System.out.println("User da nhap ten mon hoc: " + subjectName);

                if(checkingHadAccQLDT(chatId))
                {
                    if(!stateFirstTime.containsKey(chatId))
                    {
                        stateFirstTime.put(chatId, "First time");
                        sendText(chatId, "Vì trang web thường phản hồi chậm. Vui lòng đợi trong ít phút!");
                    }
                    executorService.submit(() -> {
                        sendText(chatId, CrawlDiem(chatId, "/diemmonhoc", subjectName));
                    });
                }
            } else if (statechat.equals("WAITING_FOR_YEAR"))
            {
                String yearStr = msg.getText();
                String yearPattern = "\\d{4}-\\d{4}";

                if (yearStr.matches(yearPattern)) {         
                    Integer year1 = Integer.parseInt(yearStr.substring(0,4));
                    Integer year2 = Integer.parseInt(yearStr.substring(5));
                    if(year1 != year2 - 1) {
                        sendText(chatId, "Năm học không hợp lệ. Vui lòng nhập lại: ");
                        stateChat.put(chatId, "WAITING_FOR_YEAR");
                    } 
                    else
                    {
                        System.out.println("User đã nhập tên năm học: " + yearStr);
                        stateChat.put(chatId + 1, yearStr); // Lưu trữ year
                        sendText(chatId, "Nhập học kỳ (1/2): ");
                        stateChat.put(chatId, "WAITING_FOR_SEMESTER");
                    }                   
                } else 
                {
                    sendText(chatId, "Vui lòng nhập lại theo định dạng 'yyyy-yyyy': ");
                    stateChat.put(chatId, "WAITING_FOR_YEAR");
                }
            } else if (statechat.equals("WAITING_FOR_SEMESTER"))
            {
                String semesterStr = msg.getText();
                if (semesterStr.equals("1") || semesterStr.equals("2"))
                {                    
                    System.out.println("User đã nhập kỳ học: " + semesterStr);
                    String year = stateChat.get(chatId + 1); // Lấy year từ stateLogin
                    if (checkingHadAccQLDT(chatId))
                    {   
                        if(!stateFirstTime.containsKey(chatId))
                        {
                            stateFirstTime.put(chatId, "First time");
                            sendText(chatId, "Vì trang web thường phản hồi chậm. Vui lòng đợi trong ít phút!");
                        }             
                        executorService.submit(() -> {
                            sendText(chatId, CrawlDiemGPATheoKy(chatId, "/diemgpatheohocki", year, semesterStr));
                        });   
                        
                    }
                } else
                {
                    sendText(chatId, "Vui lòng nhập lại học kỳ 1/2: ");
                    stateChat.put(chatId, "WAITING_FOR_SEMESTER");
                }
            }

//            return;
            }

    }

    public void sendText(Long who, String what)
    {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try
        {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e)
        {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    public void copyMessage(Long who, Integer msgId)
    {
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString()) //We copy from the user
                .chatId(who.toString()) //And send it back to him
                .messageId(msgId) //Specifying what message
                .build();
        try
        {
            execute(cm);
        } catch (TelegramApiException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private boolean checkingHadAccQLDT(Long chatId)
    {
        if(!lsUser.isUserHasAccQLDTByChatId(chatId))
        {
//            stateChat.put(chatId, mesage);
            sendText(chatId, "Bạn chưa nhập tài khoản QLĐT!\n Nhập Username của bạn:");

            // Lưu trạng thái đăng nhập cho người dùng, có thể sử dụng HashMap để lưu trạng thái
            stateLogin.put(chatId, "WAITING_FOR_USERNAME");
            return false;
        }
        return true;
    }

    static public String CrawlData(Long chatId, String requestData)
    {
        User2 userCrawl = lsUser.getUserByChatId(chatId);
        QLDT_Crawl2 crawl = new QLDT_Crawl2(lsUser, chatId, requestData);

        return crawl.start();
    }
    
    static public String CrawlDiem(Long chatId, String requestData, String subjectName)
    {
        User2 userCrawl = lsUser.getUserByChatId(chatId);
        QLDT_CrawlDiem2 crawl = new QLDT_CrawlDiem2(lsUser, chatId, requestData, subjectName);

        return crawl.start();
    }
    
    static public String CrawlDiemGPATheoKy(Long chatId, String requestData, String year, String semester)
    {
        User2 userCrawl = lsUser.getUserByChatId(chatId);
        QLDT_CrawlDiem2 crawl = new QLDT_CrawlDiem2(lsUser, chatId, requestData, year, semester);

        return crawl.start();
    }

    public static void main(String[] args) throws TelegramApiException
    {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        BotTelegram2 bot = new BotTelegram2();                  //We moved this line out of the register method, to access it later
        botsApi.registerBot(bot);
        System.out.println("Bot started!");
    }

    

}
