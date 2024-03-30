package com.czy.user;

public class Crowdfunding {
    private String cardId;
    private String name;
    private int age;
    private String sex;
    private String reason;
    private double money;

    public Crowdfunding() {
    }

    public Crowdfunding(String cardId, String name, int age, String sex, String reason, double money) {
        this.cardId = cardId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.reason = reason;
        this.money = money;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
