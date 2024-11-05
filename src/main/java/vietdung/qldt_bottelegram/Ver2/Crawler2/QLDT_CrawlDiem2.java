/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Crawler2;

import vietdung.qldt_bottelegram.Ver2.Model2.ListUser2;
import vietdung.qldt_bottelegram.Ver2.Model2.User2;

/**
 *
 * @author DELL
 */
public class QLDT_CrawlDiem2 {
    private ListUser2 listUser;
    private Long chatId;
    private String requestData;
    private String subjectName;
    private String year;
    private String semester;

    public QLDT_CrawlDiem2() {
    }

    public QLDT_CrawlDiem2(ListUser2 listUser, Long chatId, String requestData, String subjectName) {
        this.listUser = listUser;
        this.chatId = chatId;
        this.requestData = requestData;
        this.subjectName = subjectName;
    }

    public QLDT_CrawlDiem2(ListUser2 listUser, Long chatId, String requestData, String year, String semester) {
        this.listUser = listUser;
        this.chatId = chatId;
        this.requestData = requestData;
        this.year = year;
        this.semester = semester;
    }
    
    public String start()
    {
        //Khu vực xử lý các chức năng
        if(this.requestData.equals("/diemmonhoc")) 
        {
            Crawl_DiemMonHoc2 crawl_DiemMonHoc = new Crawl_DiemMonHoc2(listUser, chatId, subjectName);
            return crawl_DiemMonHoc.start();
        } else if(this.requestData.equals("/diemgpatheohocki"))
        {          
            Crawl_DiemGPATheoHocKy2 crawl_DiemGPATheoHocKy = new Crawl_DiemGPATheoHocKy2(listUser, chatId, year, semester);
            return crawl_DiemGPATheoHocKy.start();
        }
        return "QLDT_Crawl chuc nang dang cap nhat: " + requestData;
    }
    
    public static void main(String[] args) {
        // Chay de test rieng chuc nang nay
        ListUser2 lsUser2 = new ListUser2();
        lsUser2.addUserIfNotExist(new User2(1L, "a long", "qldtUsername", "qldtPassword"));
        
        QLDT_CrawlDiem2 crawl = new QLDT_CrawlDiem2(lsUser2, 1L, "requestData", "subjectName");

        System.out.println("QLDT_CrawlDiem: " + crawl.start());
    }

}
