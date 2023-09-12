package com.login.tally.login_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://10.0.2.2:3306/db1?useSSL=false";
    private static String user="root";
    private static String password="123456";

    public static Connection getConn(){
        Connection conn=null;
        try{
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
