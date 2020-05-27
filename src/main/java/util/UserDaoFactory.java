package util;

import dao.UserDao;
import dao.UserDaoHibernateImpl;
import dao.UserDaoJDBCimpl;

import java.io.FileInputStream;
import java.io.*;
import java.util.Properties;

public class UserDaoFactory {

   public static UserDao userDao;
   public static Properties property;

    public static void getDriverProperty() {

        System.out.println("Ищу файл свойств" );


        try {

            property = new Properties();
            String propFileName = "config.properties";

            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            InputStream inputStream = loader.getResourceAsStream(propFileName);

            property.load(inputStream);

            String driverDB = property.getProperty("driverDB");

            System.out.println("DriverDB: " + driverDB);
            inputStream.close();
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public static UserDao getUserDao() {
        property = new Properties();
        System.out.println("Читаю  файл свойств" );
        getDriverProperty();
        String driverDB = property.getProperty("driverDB");

        if (driverDB.equals("hibernate")) {
            userDao = new UserDaoHibernateImpl(DBHelper.getSessionFactory());
        } else if (driverDB.equals("jdbc")) {
            userDao = new UserDaoJDBCimpl(DBHelper.getInstance().getConnection());
        }
            return userDao;

    }


}












