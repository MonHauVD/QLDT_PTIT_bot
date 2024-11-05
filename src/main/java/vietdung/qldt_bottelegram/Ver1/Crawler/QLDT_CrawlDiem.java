/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Crawler;

/**
 *
 * @author DELL
 */
public class QLDT_CrawlDiem {
    private String qldtUsername;
    private String qldtPassword;
    private String requestData;
    private String subjectName;
    private String year;
    private String semester;

    public QLDT_CrawlDiem() {
    }

    public QLDT_CrawlDiem(String qldtUsername, String qldtPassword, String requestData, String subjectName) {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
        this.requestData = requestData;
        this.subjectName = subjectName;
    }

    public QLDT_CrawlDiem(String qldtUsername, String qldtPassword, String requestData, String year, String semester) {
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
        this.requestData = requestData;
        this.year = year;
        this.semester = semester;
    }
    
    public String start()
    {
        //Khu vực xử lý các chức năng
        if(this.requestData.equals("/diemmonhoc")) 
        {
            Crawl_DiemMonHoc crawl_DiemMonHoc = new Crawl_DiemMonHoc(qldtUsername, qldtPassword, subjectName);
            return crawl_DiemMonHoc.start();
        } else if(this.requestData.equals("/diemgpatheohocki"))
        {          
            Crawl_DiemGPATheoHocKy crawl_DiemGPATheoHocKy = new Crawl_DiemGPATheoHocKy(qldtUsername, qldtPassword, year, semester);
            return crawl_DiemGPATheoHocKy.start();
        }
        return "QLDT_Crawl chuc nang dang cap nhat: " + requestData;
    }
    
    public static void main(String[] args) {
        // Chay de test rieng chuc nang nay
        QLDT_CrawlDiem crawl = new QLDT_CrawlDiem("qldtUsername", "qldtPassword", "requestData", "subjectName");

        System.out.println("QLDT_CrawlDiem: " + crawl.start());
    }

}
