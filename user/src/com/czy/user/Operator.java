package com.czy.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//������
public class Operator {
    private ArrayList<Account> accounts=new ArrayList<>();//�����˺���Ϣ
    //private ArrayList<Crowdfunding> crowdfundings=new ArrayList<>();//�����ڳ�����Ϣ
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//���������Ϊ��¼��
    private Crowdfunding cro2;
    public void start() throws SQLException, ClassNotFoundException {
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
    private void registerAccount() throws SQLException, ClassNotFoundException {
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
                //accounts.add(acc);
                System.out.println("��ϲ��ע��ɹ��������˺��ǣ�"+acc.getCardId());
                System.out.println("���ĳ�ʼ�����1000.0Ԫ~");

                DBUtill ll=new DBUtill();
                ll.addAccount(acc);
                break;
            }else{
                System.out.println("������������벻ͬ������������~");
            }
        }

    }

//��ȡ�˺�
    private String createCardId() throws SQLException, ClassNotFoundException {
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
    private Account getAccountCardId(String cardId) throws SQLException, ClassNotFoundException {
        DBUtill ll=new DBUtill();
        Account acc=ll.getData(cardId);
        if(acc!=null){
            return acc;
        }else{
            return null;
        }
    }

//��¼����
    private void login() throws SQLException, ClassNotFoundException {
        System.out.println("==�û���¼==");
        //��֤ϵͳ���Ƿ����˺�
        DBUtill ll=new DBUtill();
        if(ll.searchAccount()==false){
            System.out.println("ϵͳ�в������˺ţ�����ע��~");
            return;
        }

        while (true) {
            System.out.println("�����������˺ţ�");
            String cardId=sc.next();
            Account acc= ll.getData(cardId);

            if(acc==null){
                System.out.println("��������˺Ų����ڣ�����������~");
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
    private void showUserCommand() throws SQLException, ClassNotFoundException {
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
    private void update() throws SQLException, ClassNotFoundException {
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
    private void updateUserName() throws SQLException, ClassNotFoundException {
        System.out.println("������������������");
        String name=sc.next();
        loginAcc.setUserName(name);
        System.out.println("�޸ĳɹ�~");
        DBUtill ll=new DBUtill();
        ll.updateUsername(loginAcc);
    }

//�޸�����
    private void updateEmail() throws SQLException, ClassNotFoundException {
        System.out.println("���������������䣺");
        String email=sc.next();
        loginAcc.setEmail(email);
        System.out.println("�޸ĳɹ�~");
        DBUtill ll=new DBUtill();
        ll.updateEamil(loginAcc);
    }

//�޸ĸ��˽���
    private void updateIntroduction() throws SQLException, ClassNotFoundException {
        System.out.println("�����������¸��˽��ܣ�");
        String introduction=sc.next();
        loginAcc.setIntroduction(introduction);
        System.out.println("�޸ĳɹ�~");
        DBUtill ll=new DBUtill();
        ll.updateInrtoduction(loginAcc);
    }

//�����ڳ�
    private void launchCrowdfunding() throws SQLException, ClassNotFoundException {
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

        DBUtill ll=new DBUtill();
        ll.addCrowdfunding(cro);
        //crowdfundings.add(cro);
    }

//�鿴�����ڳ���Ϣ
    private void searchCrowdfunding() throws SQLException, ClassNotFoundException {
        DBUtill ll = new DBUtill();
        ArrayList<Crowdfunding> cros = ll.showCrowdfunding();
        if (cros.isEmpty()) {
            System.out.println("Ŀǰ��û���κ��ڳ���Ϣ~");
            return;
        }
        while (true) {
            System.out.println("���ڳ��߲�����Ϣ���£�");
            for (int i = 0; i < cros.size(); i++) {
                Crowdfunding cro = cros.get(i);
                System.out.println("===��" + (i + 1) + "λ===");
                System.out.println("������" + cro.getName());
                System.out.println("���䣺" + cro.getAge());
                System.out.println("�Ա�" + cro.getSex());
            }
            while (true) {
                System.out.println("1��ѡ���ڳ��ߣ��Բ鿴��ϸ��Ϣ");
                System.out.println("2��������һҳ");
                String rs = sc.next();
                switch (rs){
                    case "1":
                        showDetails(cros);
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("����������ȷ��~");
                        break;
                }
            }
        }
    }

    public void showDetails(ArrayList<Crowdfunding> cros) throws SQLException, ClassNotFoundException {
        System.out.println("�������ڳ���������");
        String rs=sc.next();
        for (int i = 0; i < cros.size(); i++) {
            Crowdfunding cro = cros.get(i);
            if (rs.equals(cro.getName())) {
                System.out.println("������" + cro.getName());
                System.out.println("���䣺" + cro.getAge());
                System.out.println("�Ա�" + cro.getSex());
                System.out.println("�˺ţ�" + cro.getCardId());
                System.out.println("ԭ��" + cro.getReason());
                System.out.println("�ڳ��" + cro.getMoney());
                cro2 = cro;
                break;
            }
        }
        choice(cros);
        return;
    }



//�����˾��
    private void donate(ArrayList<Crowdfunding> cros,Crowdfunding cro) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("���������");
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
                System.out.println("���ɹ�~");
                return;
            }else{
                System.out.println("�������㣬��ȷ��~");
            }
        }

    }


    public void choice(ArrayList<Crowdfunding> cros) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("��ѡ��");
            System.out.println("1����������");
            System.out.println("2�����о��");
            System.out.println("3��������һҳ");
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
                    System.out.println("����������ȷ��~");
                    break;
            }
        }
    }
}
