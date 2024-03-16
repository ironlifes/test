package com.czy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//������
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;
    public void start(){
        while (true) {
            System.out.println("==��ӭ�����ڳ�ϵͳ==");
            System.out.println("1����¼");
            System.out.println("2��ע��");
            System.out.println("��ѡ��");
            int command=sc.nextInt();
            switch (command){
                case 1:

                    break;
                case 2:
                    registerAccount();
                    break;
                default:
                    System.out.println("û�иò���~");
            }
        }
    }

//ע�����
    private void registerAccount(){
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
                break;
            }else{
                System.out.println("������������벻ͬ������������~");
            }
        }

        acc.setCardId(createCardId());

        acc.setMoney(1000);

        accounts.add(acc);
        System.out.println("��ϲ��ע��ɹ��������˺��ǣ�"+acc.getCardId());
    }

//��ȡ�˺�
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
        while (true) {
            System.out.println("�����������˺ţ�");
            String cardId=sc.next();
            Account acc=getAccountCardId(cardId);
            if(acc==null){
                System.out.println("��������˺Ų����ڣ����������룺");
            }else{
                while (true) {
                    System.out.println("�������������룺");
                    String passWord=sc.next();
                    if(passWord.equals(acc.getPassWord())){
                        System.out.println("��¼�ɹ�~");
                        loginAcc=acc;
                        showUserCommand();
                    }else{
                        System.out.println("�����������������~");
                    }
                }
            }
        }
    }

//��¼�ɹ�����
    private void showUserCommand(){
        System.out.println("�����Խ������²�����");
        System.out.println("1����ѯ���");
        System.out.println("2�������ڳ�");
        System.out.println("3���鿴�����ڳ���Ϣ");
        System.out.println("4���޸ĸ�����Ϣ");
        System.out.println("��ѡ��");
        int command=sc.nextInt();
        switch (command){
            case 1:
                System.out.println("�������Ϊ��"+loginAcc.getMoney());
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
}
