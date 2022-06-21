package com.bignerdranch.android.testonline.DAO;

/**
 * Created by ASUS on 2022/5/4.
 */

import com.bignerdranch.android.testonline.util.JDBCUtils;
import com.bignerdranch.android.testonline.Entity.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    public boolean login(String name,String password){

        String sql = "select * from users where name = ? and password = ?";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,name);
            pst.setString(2,password);

            if(pst.executeQuery().next()){

                return true;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return false;
    }

    public boolean register(User user){

        String sql = "insert into users(name,username,password,age,phone) values (?,?,?,?,?)";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,user.getName());
            pst.setString(2,user.getUsername());
            pst.setString(3,user.getPassword());
            pst.setInt(4,user.getAge());


            int value = pst.executeUpdate();

            if(value>0){
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }

    public User findUser(String name){

        String sql = "select * from users where name = ?";

        Connection  con = JDBCUtils.getConn();
        User user = null;
        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,name);

            ResultSet rs = pst.executeQuery();

            while (rs.next()){

                int id = rs.getInt(0);
                String namedb = rs.getString(1);
                String username = rs.getString(2);
                String passworddb  = rs.getString(3);
                int age = rs.getInt(4);
                String phone = rs.getString(5);
                user = new User(id,namedb,username,passworddb,age,phone);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return user;
    }


}