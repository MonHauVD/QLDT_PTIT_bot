/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Model2;

import java.util.Objects;

/**
 *
 * @author TranVietDung
 */
public class User2
{
    private Long chatId;
    private String chatUsername;
    private String qldtUsername;
    private String qldtPassword;
    private String w_locsinhvieninfo;
    private String date_w_locsinhvieninfo;
    private String w_locdshoadondientu;
    private String date_w_locdshoadondientu;
    private String w_locdstkbtuanusertheohocky;
    private String date_w_locdstkbtuanusertheohocky;
    private String w_locdstkbhockytheodoituong;
    private String date_w_locdstkbhockytheodoituong;
    private String w_locdslichthisvtheohocky;
    private String date_w_locdslichthisvtheohocky;
    private String w_locdsdiemsinhvien;
    private String date_w_locdsdiemsinhvien;
    private String w_locdstonghophocphisv;
    private String date_w_locdstonghophocphisv;
    
    public User2(Long chatId)
    {
        this.chatId = chatId;
        this.chatUsername = null;
        this.qldtUsername = null;
        this.qldtPassword = null;
    }
    
    public User2(Long chatId, String chatUsername)
    {
        this.chatId = chatId;
        this.chatUsername = chatUsername;
        this.qldtUsername = null;
        this.qldtPassword = null;
    }
    
    public User2(Long chatId, String chatUsername, String qldtUsername, String qldtPassword)
    {
        this.chatId = chatId;
        this.chatUsername = chatUsername;
        this.qldtUsername = qldtUsername;
        this.qldtPassword = qldtPassword;
    }

    public Long getChatId()
    {
        return chatId;
    }

    public void setChatId(Long chatId)
    {
        this.chatId = chatId;
    }

  

    @Override
    public boolean equals(Object obj) {
        // Kiểm tra nếu cùng tham chiếu (chính là đối tượng)
        if (this == obj) {
            return true;
        }
        // Kiểm tra nếu obj không phải là User hoặc là null
        if (obj == null || this.getClass() != obj.getClass()) {
            
            return false;
        }

        // Ép kiểu obj thành User để so sánh
        User2 otherUser = (User2) obj;
        // So sánh chatId giữa this và otherUser
        if(this.chatId.equals(otherUser.chatId))
        {
//            System.out.println("chatID giong nhau!");
            return true;
        }
            
        return false;
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Long.hashCode(chatId);
    }

    public String getChatUsername()
    {
        return chatUsername;
    }

    public void setChatUsername(String chatUsername)
    {
        this.chatUsername = chatUsername;
    }

    public String getQldtUsername()
    {
        return qldtUsername;
    }

    public void setQldtUsername(String qldtUsername)
    {
        this.qldtUsername = qldtUsername;
    }

    public String getQldtPassword()
    {
        return qldtPassword;
    }

    public void setQldtPassword(String qldtPassword)
    {
        this.qldtPassword = qldtPassword;
    }

    
    public String getW_locsinhvieninfo() {
        return w_locsinhvieninfo;
    }

    public void setW_locsinhvieninfo(String w_locsinhvieninfo) {
        this.w_locsinhvieninfo = w_locsinhvieninfo;
    }

    public String getDate_w_locsinhvieninfo() {
        return date_w_locsinhvieninfo;
    }

    public void setDate_w_locsinhvieninfo(String date_w_locsinhvieninfo) {
        this.date_w_locsinhvieninfo = date_w_locsinhvieninfo;
    }

    public String getW_locdshoadondientu() {
        return w_locdshoadondientu;
    }

    public void setW_locdshoadondientu(String w_locdshoadondientu) {
        this.w_locdshoadondientu = w_locdshoadondientu;
    }

    public String getDate_w_locdshoadondientu() {
        return date_w_locdshoadondientu;
    }

    public void setDate_w_locdshoadondientu(String date_w_locdshoadondientu) {
        this.date_w_locdshoadondientu = date_w_locdshoadondientu;
    }

    public String getW_locdstkbtuanusertheohocky() {
        return w_locdstkbtuanusertheohocky;
    }

    public void setW_locdstkbtuanusertheohocky(String w_locdstkbtuanusertheohocky) {
        this.w_locdstkbtuanusertheohocky = w_locdstkbtuanusertheohocky;
    }

    public String getDate_w_locdstkbtuanusertheohocky() {
        return date_w_locdstkbtuanusertheohocky;
    }

    public void setDate_w_locdstkbtuanusertheohocky(String date_w_locdstkbtuanusertheohocky) {
        this.date_w_locdstkbtuanusertheohocky = date_w_locdstkbtuanusertheohocky;
    }

    public String getW_locdstkbhockytheodoituong() {
        return w_locdstkbhockytheodoituong;
    }

    public void setW_locdstkbhockytheodoituong(String w_locdstkbhockytheodoituong) {
        this.w_locdstkbhockytheodoituong = w_locdstkbhockytheodoituong;
    }

    public String getDate_w_locdstkbhockytheodoituong() {
        return date_w_locdstkbhockytheodoituong;
    }

    public void setDate_w_locdstkbhockytheodoituong(String date_w_locdstkbhockytheodoituong) {
        this.date_w_locdstkbhockytheodoituong = date_w_locdstkbhockytheodoituong;
    }

    public String getW_locdslichthisvtheohocky() {
        return w_locdslichthisvtheohocky;
    }

    public void setW_locdslichthisvtheohocky(String w_locdslichthisvtheohocky) {
        this.w_locdslichthisvtheohocky = w_locdslichthisvtheohocky;
    }

    public String getDate_w_locdslichthisvtheohocky() {
        return date_w_locdslichthisvtheohocky;
    }

    public void setDate_w_locdslichthisvtheohocky(String date_w_locdslichthisvtheohocky) {
        this.date_w_locdslichthisvtheohocky = date_w_locdslichthisvtheohocky;
    }

    public String getW_locdsdiemsinhvien() {
        return w_locdsdiemsinhvien;
    }

    public void setW_locdsdiemsinhvien(String w_locdsdiemsinhvien) {
        this.w_locdsdiemsinhvien = w_locdsdiemsinhvien;
    }

    public String getDate_w_locdsdiemsinhvien() {
        return date_w_locdsdiemsinhvien;
    }

    public void setDate_w_locdsdiemsinhvien(String date_w_locdsdiemsinhvien) {
        this.date_w_locdsdiemsinhvien = date_w_locdsdiemsinhvien;
    }

    public String getW_locdstonghophocphisv() {
        return w_locdstonghophocphisv;
    }

    public void setW_locdstonghophocphisv(String w_locdstonghophocphisv) {
        this.w_locdstonghophocphisv = w_locdstonghophocphisv;
    }

    public String getDate_w_locdstonghophocphisv() {
        return date_w_locdstonghophocphisv;
    }

    public void setDate_w_locdstonghophocphisv(String date_w_locdstonghophocphisv) {
        this.date_w_locdstonghophocphisv = date_w_locdstonghophocphisv;
    }

    @Override
    public String toString()
    {
        return "User{" + "chatId=" + chatId + ", chatUsername=" + chatUsername + ", qldtUsername=" + qldtUsername + ", qldtPassword=********"+ '}';
    }
    
    public String printDetail() {
        return "User2 [chatId=" + chatId + ", chatUsername=" + chatUsername + ", qldtUsername=" + qldtUsername
                + ", qldtPassword=" + qldtPassword + ", w_locsinhvieninfo=" + w_locsinhvieninfo
                + ", date_w_locsinhvieninfo=" + date_w_locsinhvieninfo + ", w_locdshoadondientu=" + w_locdshoadondientu
                + ", date_w_locdshoadondientu=" + date_w_locdshoadondientu + ", w_locdstkbtuanusertheohocky="
                + w_locdstkbtuanusertheohocky + ", date_w_locdstkbtuanusertheohocky=" + date_w_locdstkbtuanusertheohocky
                + ", w_locdstkbhockytheodoituong=" + w_locdstkbhockytheodoituong + ", date_w_locdstkbhockytheodoituong="
                + date_w_locdstkbhockytheodoituong + ", w_locdslichthisvtheohocky=" + w_locdslichthisvtheohocky
                + ", date_w_locdslichthisvtheohocky=" + date_w_locdslichthisvtheohocky + ", w_locdsdiemsinhvien="
                + w_locdsdiemsinhvien + ", date_w_locdsdiemsinhvien=" + date_w_locdsdiemsinhvien
                + ", w_locdstonghophocphisv=" + w_locdstonghophocphisv + ", date_w_locdstonghophocphisv="
                + date_w_locdstonghophocphisv + "]";
    }

    public String print()
    {
        return "User{" + "chatId=" + chatId + ", chatUsername=" + chatUsername + ", qldtUsername=" + qldtUsername + ", qldtPassword=" + qldtPassword + '}';
    }
    
}
