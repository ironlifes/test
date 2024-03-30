package com.czy.user;

public class Account {
    private String cardId;
    private String userName;
    private String passWord;
    private String email;
    private String introduction;
    private double money;

    public Account() {
    }

    public Account(String cardId, String userName, String passWord, String email, String introduction, double money) {
        this.cardId = cardId;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.introduction = introduction;
        this.money = money;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
