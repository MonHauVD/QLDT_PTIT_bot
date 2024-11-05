/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver1.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TranVietDung
 */
public class ListUser
{

    List<User> lsUser;

    public ListUser()
    {
        this.lsUser = new ArrayList<User>();
    }

    public ListUser(List<User> lsUser)
    {
        this.lsUser = lsUser;
    }

    public List<User> getLsUser()
    {
        return lsUser;
    }

    public void setLsUser(List<User> lsUser)
    {
        this.lsUser = lsUser;
    }

    @Override
    public String toString()
    {
        return "ListUser{" + "lsUser=" + lsUser + '}';
    }

    public void addUserIfNotExist(User newUser)
    {
        // Kiểm tra xem user đã tồn tại trong list chưa
        boolean exists = false;
        for (User user : lsUser)
        {
//            System.out.println("this user: " + user);
//            System.out.println("that user: " + newUser);
//            System.out.println("compare: " + (user.equals(newUser)));
            if (user.equals(newUser))
            {
                exists = true;
                break;
            }
        }

        // Nếu chưa tồn tại, thêm vào danh sách
        if (!exists)
        {
            lsUser.add(newUser);
            System.out.println("User added to the list.");
        } else
        {
//            System.out.println("User with chatId " + newUser.getChatId() + " already exists.");
        }
    }

    public User getUserByChatId(Long chatId)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Trả về user tìm thấy
                return user;
            }
        }
        // Nếu không tìm thấy, trả về null
        return null;
    }
    
    public boolean isUserHasAccQLDTByChatId(Long chatId)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                if(user.getQldtUsername() == null || user.getQldtPassword() == null)
                    return false;
            }
        }
        // Nếu không tìm thấy, trả về null
        return true;
    }
    
    public void updateQldtUsername(Long chatId, String newQldtUsername)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtUsername
                user.setQldtUsername(newQldtUsername);
                System.out.println("qldtUsername updated to: " + newQldtUsername);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void updateQldtPassword(Long chatId, String newQldtPassword)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setQldtPassword(newQldtPassword);
                System.out.println("qldtPassword updated to: " + newQldtPassword);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

}
