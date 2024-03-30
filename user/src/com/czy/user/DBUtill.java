package com.czy.user;

import java.sql.*;
import java.util.ArrayList;

public class DBUtill {
    //添加用户

    public void addAccount(Account acc) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="insert into t_account(card_id,user_name,pass_word,email,introduction,money) values(?,?,?,?,?,?);";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,acc.getCardId());
        pstmt.setString(2,acc.getUserName());
        pstmt.setString(3,acc.getPassWord());
        pstmt.setString(4,acc.getEmail());
        pstmt.setString(5,acc.getIntroduction());
        pstmt.setDouble(6,acc.getMoney());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    //修改用户名
    public void updateUsername(Account acc) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="update t_account set user_name=? where card_id=?;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,acc.getUserName());
        pstmt.setString(2,acc.getCardId());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    //修改email
    public void updateEamil(Account acc) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="update t_account set email=? where card_id=?;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,acc.getEmail());
        pstmt.setString(2,acc.getCardId());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    //修改个人介绍
    public void updateInrtoduction(Account acc) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="update t_account set introduction=? where card_id=?;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,acc.getIntroduction());
        pstmt.setString(2,acc.getCardId());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    //查找用户
    public boolean searchAccount() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="select * from t_account;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        ResultSet rs= pstmt.executeQuery();
        if(rs.next()){
            rs.close();
            pstmt.close();
            conn.close();
            return true;
        }else{
            rs.close();
            pstmt.close();
            conn.close();
            return false;
        }
    }

    //
    public int getAccountCount() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="select * from t_account;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        ResultSet rs= pstmt.executeQuery();
        int i = 0;
        while(rs.next()){
            i=i+1;
        }
        rs.close();
        pstmt.close();
        conn.close();
        return i;
    }

    //根据cardId获取用户信息，即登录
    public Account getData(String cardId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="select * from t_account where card_id=?;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,cardId);
        ResultSet rs= pstmt.executeQuery();
        if(rs.next()){
            Account acc=new Account();
            acc.setUserName(rs.getString("user_name"));
            acc.setCardId(rs.getString("card_id"));
            acc.setPassWord(rs.getString("pass_word"));
            acc.setEmail(rs.getString("email"));
            acc.setIntroduction(rs.getString("introduction"));
            acc.setMoney(rs.getDouble("money"));
            rs.close();
            pstmt.close();
            conn.close();
            return acc;
        }else {
            rs.close();
            pstmt.close();
            conn.close();
            return null;
        }
    }

    //添加众筹信息
    public void addCrowdfunding(Crowdfunding cro) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="insert into t_crowdfunding(card_id,name,age,sex,reason,money) values(?,?,?,?,?,?);";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,cro.getCardId());
        pstmt.setString(2, cro.getName());
        pstmt.setInt(3,cro.getAge());
        pstmt.setString(4,cro.getSex());
        pstmt.setString(5, cro.getReason());
        pstmt.setDouble(6,cro.getMoney());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    //查询众筹信息
    public ArrayList<Crowdfunding> showCrowdfunding() throws ClassNotFoundException, SQLException {
        deleteCrowdfunding();
        ArrayList<Crowdfunding> cros=new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="select * from t_crowdfunding;";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();
        while(rs.next()){
            String cardId= rs.getNString("card_id");
            String name=rs.getString("name");
            int age=rs.getInt("age");
            String sex=rs.getString("sex");
            String reason=rs.getString("reason");
            double money=rs.getDouble("money");

            Crowdfunding cro=new Crowdfunding();
            cro.setCardId(cardId);
            cro.setName(name);
            cro.setAge(age);
            cro.setSex(sex);
            cro.setReason(reason);
            cro.setMoney(money);
            cros.add(cro);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return cros;
    }

    //
    public void updateDonate(Account acc,double money,Crowdfunding cro) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql1="update t_crowdfunding set money=money-? where card_id=?;";
        PreparedStatement pstmt1=conn.prepareStatement(sql1);
        pstmt1.setDouble(1,money);
        pstmt1.setString(2,cro.getCardId());
        pstmt1.executeUpdate();
        pstmt1.close();

        String sql2="update t_account set money=money-? where card_id=?;";
        PreparedStatement pstmt2=conn.prepareStatement(sql2);
        pstmt2.setDouble(1,money);
        pstmt2.setString(2,acc.getCardId());
        pstmt2.executeUpdate();
        pstmt2.close();

        String sql3="update t_account set money=money+? where card_id=?;";
        PreparedStatement pstmt3=conn.prepareStatement(sql3);
        pstmt3.setDouble(1,money);
        pstmt3.setString(2,cro.getCardId());
        pstmt3.executeUpdate();
        pstmt3.close();
        conn.close();

        acc.setMoney(acc.getMoney()-money);
    }

    public void deleteCrowdfunding() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL="jdbc:mysql://localhost:3306/kaohe";
        String NAME="root";
        String PASSWORD="123456";
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        String sql="delete from t_crowdfunding where money=0";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
