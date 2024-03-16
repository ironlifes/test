package com.czy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//操作类
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;
    public void start(){
        while (true) {
            System.out.println("==欢迎进入众筹系统==");
            System.out.println("1、登录");
            System.out.println("2、注册");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:

                    break;
                case 2:
                    registerAccount();
                    break;
                default:
                    System.out.println("没有该操作~");
            }
        }
    }

//注册界面
    private void registerAccount(){
        System.out.println("==用户注册==");

        Account acc=new Account();
        System.out.println("请输入您的姓名：");
        acc.setUserName(sc.next());

        System.out.println("请输入您绑定的邮箱：");
        acc.setEmail(sc.next());

        System.out.println("请输入您的个人介绍：");
        acc.setIntroduction(sc.next());

        while (true) {
            System.out.println("请输入您的账户密码：");
            String passWord=sc.next();
            System.out.println("请确定您的账户密码：");
            String okPassWord=sc.next();
            if(passWord.equals(okPassWord)){
                acc.setPassWord(passWord);
                break;
            }else{
                System.out.println("两次输入的密码不同，请重新输入~");
            }
        }

        acc.setCardId(createCardId());

        acc.setMoney(1000);

        accounts.add(acc);
        System.out.println("恭喜您注册成功，您的账号是："+acc.getCardId());
    }

//获取账号
    private String createCardId() {
        while (true) {
            String cardId = "";
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                int n = r.nextInt(10);
                cardId += n;
            }
            Account acc = getAccountCardId(cardId);
            if (acc == null) {
                return cardId;
            }
        }
    }

//查找账号是否存在
    private Account getAccountCardId(String cardId){
        for(int i=0;i<accounts.size();i++){
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }

//登录界面
    private void login(){
        System.out.println("==用户登录==");
        while (true) {
            System.out.println("请输入您的账号：");
            String cardId=sc.next();
            Account acc=getAccountCardId(cardId);
            if(acc==null){
                System.out.println("您输入的账号不存在，请重新输入：");
            }else{
                while (true) {
                    System.out.println("请输入您的密码：");
                    String passWord=sc.next();
                    if(passWord.equals(acc.getPassWord())){
                        System.out.println("登录成功~");
                        loginAcc=acc;
                        showUserCommand();
                    }else{
                        System.out.println("密码错误，请重新输入~");
                    }
                }
            }
        }
    }

//登录成功界面
    private void showUserCommand(){
        System.out.println("您可以进行以下操作：");
        System.out.println("1、查询余额");
        System.out.println("2、发起众筹");
        System.out.println("3、查看其他众筹信息");
        System.out.println("4、修改个人信息");
        System.out.println("请选择：");
        int command=sc.nextInt();
        switch (command){
            case 1:
                System.out.println("您的余额为："+loginAcc.getMoney());
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                update();
                break;
            default:

        }
    }

//修改界面
    private void update(){
        System.out.println("请输入您要修改的信息：");
        System.out.println("1、姓名");
        System.out.println("2、邮箱");
        System.out.println("3、个人介绍");
        int command=sc.nextInt();
        switch(command){
            case 1:
                updateUserName();
                break;
            case 2:
                updateEmail();
                break;
            case 3:
                updateIntroduction();
                break;
            default:
        }
    }

//修改姓名
    private void updateUserName(){
        System.out.println("请输入您的新姓名：");
        String name=sc.next();
        loginAcc.setUserName(name);
        System.out.println("修改成功~");
    }

//修改邮箱
    private void updateEmail(){
        System.out.println("请输入您的新邮箱：");
        String email=sc.next();
        loginAcc.setEmail(email);
        System.out.println("修改成功~");
    }

//修改个人介绍
    private void updateIntroduction(){
        System.out.println("请输入您的新个人介绍：");
        String introduction=sc.next();
        loginAcc.setIntroduction(introduction);
        System.out.println("修改成功~");
    }
}
