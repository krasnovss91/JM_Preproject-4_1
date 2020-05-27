package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {


    List<User> getAllUsersDao();


    User getUserByIdDao(long id) throws SQLException;


    boolean checkUserByNameDao(String name) throws SQLException;


    boolean checkUserByLoginDao(String login) throws SQLException;


    void addUserDao(User user);


    void updateUserDao(User user);


    void deleteUserByIdDao(Long id) throws SQLException;

    public User isExist(String login, String password);

}
