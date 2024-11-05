/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Model;

import java.util.Objects;

/**
 *
 * @author TranVietDung
 */
public class User
{
    private Long chatId;
    private String chatUsername;
    private String qldtUsername;
    private String qldtPassword;
    
    public User(Long chatId)
    {
        this.chatId = chatId;
        this.chatUsername = null;
        this.qldtUsername = null;
        this.qldtPassword = null;
    }
    
    public User(Long chatId, String chatUsername)
    {
        this.chatId = chatId;
        this.chatUsername = chatUsername;
        this.qldtUsername = null;
        this.qldtPassword = null;
    }
    
    public User(Long chatId, String chatUsername, String qldtUsername, String qldtPassword)
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
        User otherUser = (User) obj;
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

    @Override
    public String toString()
    {
        return "User{" + "chatId=" + chatId + ", chatUsername=" + chatUsername + ", qldtUsername=" + qldtUsername + ", qldtPassword=********"+ '}';
    }
    
    public String print()
    {
        return "User{" + "chatId=" + chatId + ", chatUsername=" + chatUsername + ", qldtUsername=" + qldtUsername + ", qldtPassword=" + qldtPassword + '}';
    }
    
}
