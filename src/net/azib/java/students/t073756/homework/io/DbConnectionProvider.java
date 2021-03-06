package net.azib.java.students.t073756.homework.io;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import net.azib.java.students.t073756.homework.DecathlonException;
import net.azib.java.students.t073756.homework.Main;

import java.sql.Connection;
import java.util.Properties;

public class DbConnectionProvider {

    public static Connection provideConnection() {
        Properties p = new Properties();
        try {
            p.load(Main.class.getResourceAsStream("dbconnection.properties"));
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUrl(p.getProperty("url"));
            return dataSource.getConnection(p.getProperty("user"), p.getProperty("password"));
        } catch (Exception e) {
            throw new DecathlonException("unable to provide DB connection");
        }
    }
}