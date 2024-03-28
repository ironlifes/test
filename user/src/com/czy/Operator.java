package com.czy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//操作类
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();//储存账号信息
    private ArrayList<Crowdfunding> crowdfundings=new ArrayList<>();//储存众筹者信息
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//定义变量作为登录者
    public void start() throws SQLException {
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
    private void registerAccount() throws SQLException {
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
                accounts.add(acc);
                System.out.println("恭喜您注册成功，您的账号是："+acc.getCardId());
                System.out.println("您的初始余额是1000.0元~");

                String url="jdbc:mysql://127.0.0.1:3306/kaohe";
                String username="root";
                String password="123456";
                Connection conn= DriverManager.getConnection(url,username,password);
                String sql="insert into t_account(card_id,user_name,pass_word,email,introduction,money) values(?,?,?,?,?,?);";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.setString(1,acc.getCardId());
                pstmt.setString(2,acc.getUserName());
                pstmt.setString(3,acc.getPassWord());
                pstmt.setString(4,acc.getEmail());
                pstmt.setString(5,acc.getIntroduction());
                pstmt.setDouble(6,acc.getMoney());
                int count=pstmt.executeUpdate();
                System.out.println(count>0);
                pstmt.close();
                conn.close();
                break;
            }else{
                System.out.println("两次输入的密码不同，请重新输入~");
            }
        }

    }

//获取账号
    private String createCardId() {
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
        //验证系统中是否有账号
        if(accounts.size()==0){
            System.out.println("系统中不存在账号，请先注册~");
            return;
        }

        while (true) {
            System.out.println("请输入您的账号：");
            String cardId=sc.next();
            Account acc=getAccountCardId(cardId);

            if(acc==null){
                System.out.println("您输入的账号不存在，请重新输入：");
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
    private void showUserCommand(){
        while (true) {
            System.out.println("您可以进行以下操作：");
            System.out.println("1、查询余额");
            System.out.println("2、发起众筹");
            System.out.println("3、查看其他众筹信息");
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
                System.out.println("输入有误，请重新输入~");
                break;
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

//发起众筹
    private void launchCrowdfunding(){
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

        crowdfundings.add(cro);
    }

//查看他人众筹信息
    private void searchCrowdfunding(){
        System.out.println("各众筹信息如下：");
        for (int i = 0; i < crowdfundings.size(); i++) {
            Crowdfunding cro=crowdfundings.get(i);
            System.out.println("账号："+cro.getCardId());
            System.out.println("姓名："+cro.getName());
            System.out.println("年龄："+cro.getAge());
            System.out.println("性别："+cro.getSex());
            System.out.println("众筹原因："+cro.getReason());
            System.out.println("众筹金额："+cro.getMoney());
            System.out.println();
        }
        while (true) {
            System.out.println("请问您是否要进行捐款？");
            System.out.println("请回答是或否：");
            String command=sc.next();
            switch (command){
                case "是":
                    donate();
                    return;
                case "否":
                    return;
                default:
                    System.out.println("输入有误，请重新输入~");
                    break;
            }
        }
    }
//向他人捐款
    private void donate(){
        System.out.println("请选择捐款对象的账号：");
        String cardId=sc.next();

        for (int i = 0; i < crowdfundings.size(); i++) {
            Crowdfunding cro=crowdfundings.get(i);
            //遍历crowdfundings集合寻找捐款对象
            if(cro.getCardId().equals(cardId)){
                while (true) {
                    System.out.println("请输入捐款金额：");
                    double money=sc.nextDouble();
                    //确保余额充足
                    if(loginAcc.getMoney()-money>=0){
                        //更新当期账号余额
                        loginAcc.setMoney(loginAcc.getMoney()-money);
                        for (int i1 = 0; i1 < accounts.size(); i1++) {
                            Account acc=accounts.get(i);
                            //遍历accounts集合寻找捐款对象
                            if(acc.getCardId().equals(cardId)){
                                //更新捐款对象的余额
                                acc.setMoney(acc.getMoney()+money);
                                break;
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("您的余额不足，请重新输入~");
                    }
                }
            }
            break;
        }
        System.out.println("捐款成功~");
    }
}
