package com.example.fridge;
import java.sql.Connection;
import java.sql.DriverManager;


public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getDBConnection(){
        String databaseUser = "a21ib2a01";
        String databasePassword = "secret";
        String url = "jdbc:mysql://localhost:3306/a21ib2a01";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }

}
