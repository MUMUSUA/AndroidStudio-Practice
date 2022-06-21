package com.bignerdranch.android.testonline;

/**
 * Created by ASUS on 2022/5/4.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {



    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConn() {
        Connection  conn = null;
        try {
            conn= DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test","root","20011021");
            System.out.println("数据库连接成功！");
        }catch (Exception exception){
            exception.printStackTrace();
            System.out.println("数据库连接失败！");
        }
        return conn;
    }

    public static void close(Connection conn){
        try {
            conn.close();
            System.out.println("数据库已关闭!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库未关闭成功!");
        }
    }

    public static void main(String[] args){

        Connection  con = JDBCUtils.getConn();
        JDBCUtils.close(con);
    }
}