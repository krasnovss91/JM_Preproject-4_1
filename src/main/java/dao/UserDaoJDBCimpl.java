package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCimpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> getAllUsersDao() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {

            ResultSet result = preStmt.executeQuery();
            while (result.next()) {
                long idUser = result.getLong(1);
                String nameUser = result.getString(2);
                String loginUser = result.getString(3);
                String passwordUser = result.getString(4);
                String role = result.getString(5);

                allUsers.add(new User(idUser, nameUser, loginUser, passwordUser, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    @Override
    public User getUserByIdDao(long id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {
            preStmt.setLong(1, id);

            ResultSet result = preStmt.executeQuery();
            result.next();
            long idUser = result.getLong(1);
            String nameUser = result.getString(2);
            String loginUser = result.getString(3);
            String passwordUser = result.getString(4);
            String role = result.getString(5);

            user = new User(idUser, nameUser, loginUser, passwordUser, role);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public long getUserIdByName(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        long id = 0L;
        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {
            preStmt.setString(1, name);

            ResultSet result = preStmt.executeQuery();
            result.next();
            id = result.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return id;
    }

    public User getUserByName(String name) {

        User user = null;
        long id = 0L;
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {

            preStmt.setString(1, name);
            ResultSet result = preStmt.executeQuery();
            result.next();
            long idUser = result.getLong(1);
            String nameUser = result.getString(2);
            String loginUser = result.getString(3);
            String passwordUser = result.getString(4);
            String role = result.getString(5);


            user = new User(idUser, nameUser, loginUser, passwordUser, role);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean checkUserByNameDao(String name) throws SQLException {
        User user = null;
        long id = 0L;
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {

            preStmt.setString(1, name);
            ResultSet result = preStmt.executeQuery();
            result.next();
            long idUser = result.getLong(1);

            if (idUser > 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean checkUserByLoginDao(String login) throws SQLException {
        User user = null;
        long id = 0L;
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preStmt = connection.prepareStatement(sql)) {

            preStmt.setString(1, login);
            ResultSet result = preStmt.executeQuery();
            result.next();
            long idUser = result.getLong(1);

            if (idUser > 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void addUserDao(User user) throws SQLException {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public void updateUserDao(User user) throws SQLException {

        String sql = "UPDATE users SET name = ?, login = ?, password = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void deleteUserByIdDao(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public User isExist(String login, String password) {
        return null;
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE if NOT EXISTS users (id bigint auto_increment, name varchar(256), login varchar(256),role varchar (256), password varchar(256), primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE if EXISTS users");
        stmt.close();
    }


}