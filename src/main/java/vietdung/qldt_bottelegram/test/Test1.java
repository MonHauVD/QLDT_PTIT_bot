package vietdung.qldt_bottelegram.test;

import java.util.List;

import vietdung.qldt_bottelegram.Ver1.Model.ListUser;

public class Test1 {
    private ListUser lsu;
    private int index;

    public Test1(ListUser lsu0, int i) {
        lsu = lsu0;
        index = i;
    }
    
    void bienDoi()
    {
        // lsu.updateQldtPassword(2L, "abc");
        lsu.updateQldtUsername(2L, "abc");
    }
    void bienDoi2(String yString)
    {
        yString = yString + "new2";
    }
    void in()
    {
        System.out.println("test1: " + lsu.getUserByChatId(2L));
    }
}
