package vietdung.qldt_bottelegram.test;

import java.util.ArrayList;
import java.util.List;

import vietdung.qldt_bottelegram.Ver1.Model.ListUser;
import vietdung.qldt_bottelegram.Ver1.Model.User;

public class Test2 {
    static ListUser listUser;
    public static void main(String[] args) {
        // List <String> ls1 = new ArrayList<>();
        // ls1.add("aaa");
        // ls1.add("abc");
        // Test1 test1 = new Test1(ls1, 1);
        // test1.bienDoi();
        // System.out.println(ls1.get(1));
        // System.out.println();
        // test1.in();
        // System.out.println();
        // test1.bienDoi2(ls1.get(1));
        // System.out.println(ls1.get(1));
        listUser = new ListUser();
        listUser.addUserIfNotExist(new User(1L, "a dung"));
        listUser.addUserIfNotExist(new User(2L, "a long"));
        System.out.println(listUser.getUserByChatId(2L));
        Test1 test1 = new Test1(listUser, 1);
        test1.bienDoi();
        System.out.println(listUser.getUserByChatId(2L));
        test1.in();

    }
}
