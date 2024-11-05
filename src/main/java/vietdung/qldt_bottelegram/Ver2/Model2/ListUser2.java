/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietdung.qldt_bottelegram.Ver2.Model2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TranVietDung
 */
public class ListUser2
{

    List<User2> lsUser;

    public ListUser2()
    {
        this.lsUser = new ArrayList<User2>();
    }

    public ListUser2(List<User2> lsUser)
    {
        this.lsUser = lsUser;
    }

    public List<User2> getLsUser()
    {
        return lsUser;
    }

    public void setLsUser(List<User2> lsUser)
    {
        this.lsUser = lsUser;
    }

    @Override
    public String toString()
    {
        return "ListUser{" + "lsUser=" + lsUser + '}';
    }

    public void addUserIfNotExist(User2 newUser)
    {
        // Kiểm tra xem user đã tồn tại trong list chưa
        boolean exists = false;
        for (User2 user : lsUser)
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

    public User2 getUserByChatId(Long chatId)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
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
        for (User2 user : lsUser)
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
        for (User2 user : lsUser)
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
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setQldtPassword(newQldtPassword);
                // System.out.println("qldtPassword updated to: " + newQldtPassword);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locsinhvieninfo(Long chatId, String new_w_locsinhvieninfo, String new_date_w_locsinhvieninfo)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locsinhvieninfo(new_w_locsinhvieninfo);
                user.setDate_w_locsinhvieninfo(new_date_w_locsinhvieninfo);
                System.out.println("w_locsinhvieninfo updated at: " + new_date_w_locsinhvieninfo);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdshoadondientu(Long chatId, String new_w_locdshoadondientu, String new_date_w_locdshoadondientu)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdshoadondientu(new_w_locdshoadondientu);
                user.setDate_w_locdshoadondientu(new_date_w_locdshoadondientu);
                System.out.println("w_locdshoadondientu updated at: " + new_date_w_locdshoadondientu);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdstkbtuanusertheohocky(Long chatId, String new_w_locdstkbtuanusertheohocky, String new_date_w_locdstkbtuanusertheohocky)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdstkbtuanusertheohocky(new_w_locdstkbtuanusertheohocky);
                user.setDate_w_locdstkbtuanusertheohocky(new_date_w_locdstkbtuanusertheohocky);
                System.out.println("w_locdstkbtuanusertheohocky updated at: " + new_date_w_locdstkbtuanusertheohocky);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdstkbhockytheodoituong(Long chatId, String new_w_locdstkbhockytheodoituong, String new_date_w_locdstkbhockytheodoituong)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdstkbhockytheodoituong(new_w_locdstkbhockytheodoituong);
                user.setDate_w_locdstkbhockytheodoituong(new_date_w_locdstkbhockytheodoituong);
                System.out.println("w_locdstkbhockytheodoituong updated at: " + new_date_w_locdstkbhockytheodoituong);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdslichthisvtheohocky(Long chatId, String new_w_locdslichthisvtheohocky, String new_date_w_locdslichthisvtheohocky)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdslichthisvtheohocky(new_w_locdslichthisvtheohocky);
                user.setDate_w_locdslichthisvtheohocky(new_date_w_locdslichthisvtheohocky);
                System.out.println("w_locdslichthisvtheohocky updated at: " + new_date_w_locdslichthisvtheohocky);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdsdiemsinhvien(Long chatId, String new_w_locdsdiemsinhvien, String new_date_w_locdsdiemsinhvien)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdsdiemsinhvien(new_w_locdsdiemsinhvien);
                user.setDate_w_locdsdiemsinhvien(new_date_w_locdsdiemsinhvien);
                System.out.println("w_locdsdiemsinhvien updated at: " + new_date_w_locdsdiemsinhvien);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }

    public void update_w_locdstonghophocphisv(Long chatId, String new_w_locdstonghophocphisv, String new_date_w_locdstonghophocphisv)
    {
        // Duyệt qua danh sách lsUser để tìm user có chatId khớp
        for (User2 user : lsUser)
        {
            if (user.getChatId().compareTo(chatId) == 0)
            {
                // Cập nhật giá trị qldtPassword
                user.setW_locdstonghophocphisv(new_w_locdstonghophocphisv);
                user.setDate_w_locdstonghophocphisv(new_date_w_locdstonghophocphisv);
                System.out.println("w_locdstonghophocphisv updated at: " + new_date_w_locdstonghophocphisv);
                return;  // Thoát vòng lặp sau khi cập nhật
            }
        }
        // Nếu không tìm thấy user với chatId tương ứng
        System.out.println("User with chatId " + chatId + " not found.");
    }
}
