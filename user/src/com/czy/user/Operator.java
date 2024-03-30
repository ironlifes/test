package com.czy.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//操作类
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();//储存账号信息
    //private ArrayList<Crowdfunding> crowdfundings=new ArrayList<>();//储存众筹者信息
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//定义变量作为登录者
    private Crowdfunding cro2;
    public void start() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("==欢迎进入众筹系统==");
            System.out.println("1、登录");
            System.out.println("2、注册");
            System.out.println("3、退出系统");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    login();
                    break;
                case 2:
                    registerAccount();
                    break;
                case 3:
                    System.out.println("退出成功~");
                    return;
                default:
                    System.out.println("输入有误，请重新输入~");
                    break;
            }
        }
    }

//注册界面
    private void registerAccount() throws SQLException, ClassNotFoundException {
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
                acc.setCardId(createCardId());
                acc.setMoney(1000);
                //accounts.add(acc);
                System.out.println("恭喜您注册成功，您的账号是："+acc.getCardId());
                System.out.println("您的初始余额是1000.0元~");

                DBUtill ll=new DBUtill();
                ll.addAccount(acc);
                break;
            }else{
                System.out.println("两次输入的密码不同，请重新输入~");
            }
        }

    }

//获取账号
    private String createCardId() throws SQLException, ClassNotFoundException {
        while (true) {
            String cardId = "";
            Random r = new Random();//获取随机数
            for (int i = 0; i < 8; i++) {
                int n = r.nextInt(10);
                cardId += n;//将随机数连接起来
            }
            Account acc = getAccountCardId(cardId);
            if (acc == null) {
                return cardId;
            }
        }
    }

//查找账号是否存在
    private Account getAccountCardId(String cardId) throws SQLException, ClassNotFoundException {
        DBUtill ll=new DBUtill();
        Account acc=ll.getData(cardId);
        if(acc!=null){
            return acc;
        }else{
            return null;
        }
    }

//登录界面
    private void login() throws SQLException, ClassNotFoundException {
        System.out.println("==用户登录==");
        //验证系统中是否有账号
        DBUtill ll=new DBUtill();
        if(ll.searchAccount()==false){
            System.out.println("系统中不存在账号，请先注册~");
            return;
        }

        while (true) {
            System.out.println("请输入您的账号：");
            String cardId=sc.next();
            Account acc= ll.getData(cardId);

            if(acc==null){
                System.out.println("您输入的账号不存在，请重新输入~");
                break;
            }
            else{
                while (true) {
                    System.out.println("请输入您的密码：");
                    String passWord=sc.next();
                    if(passWord.equals(acc.getPassWord())){
                        System.out.println("登录成功~");
                        loginAcc=acc;
                        showUserCommand();
                        return;
                    }else{
                        System.out.println("密码错误，请重新输入~");
                    }
                }
            }
        }
    }

//登录成功界面
    private void showUserCommand() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("您可以进行以下操作：");
            System.out.println("1、查询余额");
            System.out.println("2、发起众筹");
            System.out.println("3、查看所有众筹信息");
            System.out.println("4、修改个人信息");
            System.out.println("5、退出登录");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    System.out.println("您的余额为："+loginAcc.getMoney());
                    break;
                case 2:
                    launchCrowdfunding();
                    break;
                case 3:
                    searchCrowdfunding();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    System.out.println("退出成功~");
                    return;
                default:
                    System.out.println("输入有误，请重新输入~");
                    break;
            }
        }
    }

//修改界面
    private void update() throws SQLException, ClassNotFoundException {
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
                System.out.println("输入有误，请重新输入~");
                break;
        }
    }

//修改姓名
    private void updateUserName() throws SQLException, ClassNotFoundException {
        System.out.println("请输入您的新姓名：");
        String name=sc.next();
        loginAcc.setUserName(name);
        System.out.println("修改成功~");
        DBUtill ll=new DBUtill();
        ll.updateUsername(loginAcc);
    }

//修改邮箱
    private void updateEmail() throws SQLException, ClassNotFoundException {
        System.out.println("请输入您的新邮箱：");
        String email=sc.next();
        loginAcc.setEmail(email);
        System.out.println("修改成功~");
        DBUtill ll=new DBUtill();
        ll.updateEamil(loginAcc);
    }

//修改个人介绍
    private void updateIntroduction() throws SQLException, ClassNotFoundException {
        System.out.println("请输入您的新个人介绍：");
        String introduction=sc.next();
        loginAcc.setIntroduction(introduction);
        System.out.println("修改成功~");
        DBUtill ll=new DBUtill();
        ll.updateInrtoduction(loginAcc);
    }

//发起众筹
    private void launchCrowdfunding() throws SQLException, ClassNotFoundException {
        Crowdfunding cro=new Crowdfunding();

        cro.setCardId(loginAcc.getCardId());

        System.out.println("请输入众筹发起者的姓名：");
        cro.setName(sc.next());

        System.out.println("请输入众筹发起者的年龄：");
        cro.setAge(sc.nextInt());

        System.out.println("请输入众筹发起者的性别：");
        cro.setSex(sc.next());

        System.out.println("请输入众筹发起的原因：");
        cro.setReason(sc.next());

        System.out.println("请输入众筹的金额：");
        cro.setMoney(sc.nextDouble());

        DBUtill ll=new DBUtill();
        ll.addCrowdfunding(cro);
        //crowdfundings.add(cro);
    }

//查看他人众筹信息
    private void searchCrowdfunding() throws SQLException, ClassNotFoundException {
        DBUtill ll = new DBUtill();
        ArrayList<Crowdfunding> cros = ll.showCrowdfunding();
        if (cros.isEmpty()) {
            System.out.println("目前还没有任何众筹信息~");
            return;
        }
        while (true) {
            System.out.println("各众筹者部分信息如下：");
            for (int i = 0; i < cros.size(); i++) {
                Crowdfunding cro = cros.get(i);
                System.out.println("===第" + (i + 1) + "位===");
                System.out.println("姓名：" + cro.getName());
                System.out.println("年龄：" + cro.getAge());
                System.out.println("性别：" + cro.getSex());
            }
            while (true) {
                System.out.println("1、选择众筹者，以查看详细信息");
                System.out.println("2、返回上一页");
                String rs = sc.next();
                switch (rs){
                    case "1":
                        showDetails(cros);
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("输入有误，请确认~");
                        break;
                }
            }
        }
    }

    public void showDetails(ArrayList<Crowdfunding> cros) throws SQLException, ClassNotFoundException {
        System.out.println("请输入众筹者姓名：");
        String rs=sc.next();
        for (int i = 0; i < cros.size(); i++) {
            Crowdfunding cro = cros.get(i);
            if (rs.equals(cro.getName())) {
                System.out.println("姓名：" + cro.getName());
                System.out.println("年龄：" + cro.getAge());
                System.out.println("性别：" + cro.getSex());
                System.out.println("账号：" + cro.getCardId());
                System.out.println("原因：" + cro.getReason());
                System.out.println("众筹金额：" + cro.getMoney());
                cro2 = cro;
                break;
            }
        }
        choice(cros);
        return;
    }



//向他人捐款
    private void donate(ArrayList<Crowdfunding> cros,Crowdfunding cro) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("请输入捐款金额：");
            double rs=sc.nextDouble();
            if(loginAcc.getMoney()>=rs){
                DBUtill ll=new DBUtill();
                ll.updateDonate(loginAcc,rs,cro);
                for (int i = 0; i <cros.size(); i++) {
                    Crowdfunding cro3=cros.get(i);
                    if(cro3.getCardId().equals(cro.getCardId())){
                        cro3.setMoney(cro3.getMoney()-rs);
                        break;
                    }
                }
                System.out.println("捐款成功~");
                return;
            }else{
                System.out.println("您的余额不足，请确认~");
            }
        }

    }


    public void choice(ArrayList<Crowdfunding> cros) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("请选择：");
            System.out.println("1、参与评论");
            System.out.println("2、进行捐款");
            System.out.println("3、返回上一页");
            String rs2 = sc.next();
            switch (rs2) {
                case "1":
                    break;
                case "2":
                    donate(cros, cro2);
                    return;
                case "3":
                    return;
                default:
                    System.out.println("输入有误，请确认~");
                    break;
            }
        }
    }
}
