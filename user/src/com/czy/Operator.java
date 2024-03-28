package com.czy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//������
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();//�����˺���Ϣ
    private ArrayList<Crowdfunding> crowdfundings=new ArrayList<>();//�����ڳ�����Ϣ
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//���������Ϊ��¼��
    public void start() throws SQLException {
        while (true) {
            System.out.println("==��ӭ�����ڳ�ϵͳ==");
            System.out.println("1����¼");
            System.out.println("2��ע��");
            System.out.println("3���˳�ϵͳ");
            System.out.println("��ѡ��");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    login();
                    break;
                case 2:
                    registerAccount();
                    break;
                case 3:
                    System.out.println("�˳��ɹ�~");
                    return;
                default:
                    System.out.println("������������������~");
                    break;
            }
        }
    }

//ע�����
    private void registerAccount() throws SQLException {
        System.out.println("==�û�ע��==");

        Account acc=new Account();
        System.out.println("����������������");
        acc.setUserName(sc.next());

        System.out.println("���������󶨵����䣺");
        acc.setEmail(sc.next());

        System.out.println("���������ĸ��˽��ܣ�");
        acc.setIntroduction(sc.next());

        while (true) {
            System.out.println("�����������˻����룺");
            String passWord=sc.next();
            System.out.println("��ȷ�������˻����룺");
            String okPassWord=sc.next();
            if(passWord.equals(okPassWord)){
                acc.setPassWord(passWord);
                acc.setCardId(createCardId());
                acc.setMoney(1000);
                accounts.add(acc);
                System.out.println("��ϲ��ע��ɹ��������˺��ǣ�"+acc.getCardId());
                System.out.println("���ĳ�ʼ�����1000.0Ԫ~");

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
                System.out.println("������������벻ͬ������������~");
            }
        }

    }

//��ȡ�˺�
    private String createCardId() {
        while (true) {
            String cardId = "";
            Random r = new Random();//��ȡ�����
            for (int i = 0; i < 8; i++) {
                int n = r.nextInt(10);
                cardId += n;//���������������
            }
            Account acc = getAccountCardId(cardId);
            if (acc == null) {
                return cardId;
            }
        }
    }

//�����˺��Ƿ����
    private Account getAccountCardId(String cardId){
        for(int i=0;i<accounts.size();i++){
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }

//��¼����
    private void login(){
        System.out.println("==�û���¼==");
        //��֤ϵͳ���Ƿ����˺�
        if(accounts.size()==0){
            System.out.println("ϵͳ�в������˺ţ�����ע��~");
            return;
        }

        while (true) {
            System.out.println("�����������˺ţ�");
            String cardId=sc.next();
            Account acc=getAccountCardId(cardId);

            if(acc==null){
                System.out.println("��������˺Ų����ڣ����������룺");
                break;
            }
            else{
                while (true) {
                    System.out.println("�������������룺");
                    String passWord=sc.next();
                    if(passWord.equals(acc.getPassWord())){
                        System.out.println("��¼�ɹ�~");
                        loginAcc=acc;
                        showUserCommand();
                        return;
                    }else{
                        System.out.println("�����������������~");
                    }
                }
            }
        }
    }

//��¼�ɹ�����
    private void showUserCommand(){
        while (true) {
            System.out.println("�����Խ������²�����");
            System.out.println("1����ѯ���");
            System.out.println("2�������ڳ�");
            System.out.println("3���鿴�����ڳ���Ϣ");
            System.out.println("4���޸ĸ�����Ϣ");
            System.out.println("5���˳���¼");
            System.out.println("��ѡ��");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    System.out.println("�������Ϊ��"+loginAcc.getMoney());
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
                    System.out.println("�˳��ɹ�~");
                    return;
                default:
                    System.out.println("������������������~");
                    break;
            }
        }
    }

//�޸Ľ���
    private void update(){
        System.out.println("��������Ҫ�޸ĵ���Ϣ��");
        System.out.println("1������");
        System.out.println("2������");
        System.out.println("3�����˽���");
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
                System.out.println("������������������~");
                break;
        }
    }

//�޸�����
    private void updateUserName(){
        System.out.println("������������������");
        String name=sc.next();
        loginAcc.setUserName(name);
        System.out.println("�޸ĳɹ�~");
    }

//�޸�����
    private void updateEmail(){
        System.out.println("���������������䣺");
        String email=sc.next();
        loginAcc.setEmail(email);
        System.out.println("�޸ĳɹ�~");
    }

//�޸ĸ��˽���
    private void updateIntroduction(){
        System.out.println("�����������¸��˽��ܣ�");
        String introduction=sc.next();
        loginAcc.setIntroduction(introduction);
        System.out.println("�޸ĳɹ�~");
    }

//�����ڳ�
    private void launchCrowdfunding(){
        Crowdfunding cro=new Crowdfunding();

        cro.setCardId(loginAcc.getCardId());

        System.out.println("�������ڳ﷢���ߵ�������");
        cro.setName(sc.next());

        System.out.println("�������ڳ﷢���ߵ����䣺");
        cro.setAge(sc.nextInt());

        System.out.println("�������ڳ﷢���ߵ��Ա�");
        cro.setSex(sc.next());

        System.out.println("�������ڳ﷢���ԭ��");
        cro.setReason(sc.next());

        System.out.println("�������ڳ�Ľ�");
        cro.setMoney(sc.nextDouble());

        crowdfundings.add(cro);
    }

//�鿴�����ڳ���Ϣ
    private void searchCrowdfunding(){
        System.out.println("���ڳ���Ϣ���£�");
        for (int i = 0; i < crowdfundings.size(); i++) {
            Crowdfunding cro=crowdfundings.get(i);
            System.out.println("�˺ţ�"+cro.getCardId());
            System.out.println("������"+cro.getName());
            System.out.println("���䣺"+cro.getAge());
            System.out.println("�Ա�"+cro.getSex());
            System.out.println("�ڳ�ԭ��"+cro.getReason());
            System.out.println("�ڳ��"+cro.getMoney());
            System.out.println();
        }
        while (true) {
            System.out.println("�������Ƿ�Ҫ���о�");
            System.out.println("��ش��ǻ��");
            String command=sc.next();
            switch (command){
                case "��":
                    donate();
                    return;
                case "��":
                    return;
                default:
                    System.out.println("������������������~");
                    break;
            }
        }
    }
//�����˾��
    private void donate(){
        System.out.println("��ѡ���������˺ţ�");
        String cardId=sc.next();

        for (int i = 0; i < crowdfundings.size(); i++) {
            Crowdfunding cro=crowdfundings.get(i);
            //����crowdfundings����Ѱ�Ҿ�����
            if(cro.getCardId().equals(cardId)){
                while (true) {
                    System.out.println("���������");
                    double money=sc.nextDouble();
                    //ȷ��������
                    if(loginAcc.getMoney()-money>=0){
                        //���µ����˺����
                        loginAcc.setMoney(loginAcc.getMoney()-money);
                        for (int i1 = 0; i1 < accounts.size(); i1++) {
                            Account acc=accounts.get(i);
                            //����accounts����Ѱ�Ҿ�����
                            if(acc.getCardId().equals(cardId)){
                                //���¾���������
                                acc.setMoney(acc.getMoney()+money);
                                break;
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("�������㣬����������~");
                    }
                }
            }
            break;
        }
        System.out.println("���ɹ�~");
    }
}
