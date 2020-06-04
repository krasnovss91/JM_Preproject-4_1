package dao;

import model.User;
import org.hibernate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsersDao() throws HibernateException {
        //  Session session = sessionFactory.openSession();
        //    Transaction transaction = session.beginTransaction();
        //   List<User> allUsers = session.createQuery("FROM User").list();
        Session session = null;
        Transaction transaction = null;
        List<User> allUsers = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            allUsers = session.createQuery("FROM User").list();
            //     transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
            //    return allUsers;
        }
        return allUsers;
    }

    @Override
    public User getUserByIdDao(long id) throws HibernateException {
        // Session session = sessionFactory.openSession();
        // Transaction transaction = session.beginTransaction();
        //  Query query = session.createQuery("from User where id = :userId");
        //  List<User> userList = query.setParameter("userId", id).list();
        //  User user = userList.get(0);
        Session session = null;
        Transaction transaction = null;
        Query query;
        List<User> userList;
        User user = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from User where id = :userId");
            //   transaction.commit();
            userList = query.setParameter("userId", id).list();
            user = userList.get(0);
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
            // return user;
        }
        return user;
    }

    @Override
    public boolean checkUserByNameDao(String name) throws HibernateException {
        // Session session = sessionFactory.openSession();
        //  Transaction transaction = session.beginTransaction();
        // Query query = session.createQuery("from User where name = :userName");
        //  List<User> userList = query.setParameter("userName", name).list();
        Session session = null;
        Transaction transaction = null;
        Query query;
        List<User> userList = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from User where name = :userName");
            userList = query.setParameter("userName", name).list();
            // transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
            //  if (userList.size() > 0) {
            //      return false;
            //  } else return true;
        }
        if (userList.size() > 0) {
            return false;
        } else return true;
    }

    @Override
    public boolean checkUserByLoginDao(String login) throws HibernateException {
        //  Session session = sessionFactory.openSession();
        // Transaction transaction = session.beginTransaction();
        //  Query query = session.createQuery("from User where login = :userLogin");
        //  List<User> userList = query.setParameter("userLogin", login).list();
        Session session = null;
        Transaction transaction = null;
        Query query;
        List<User> userList = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from User where login = :userLogin");
            userList = query.setParameter("userLogin", login).list();
            //  transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        if (userList.size() > 0) {
            return false;
        } else return true;
    }

    @Override
    public void addUserDao(User user) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public void updateUserDao(User user) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("UPDATE User SET name=:name, login=:login, password=:password, role=:role WHERE id=:id")
                    .setParameter("name", user.getName())
                    .setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("role", user.getRole())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteUserByIdDao(Long id) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM User WHERE id = :userId");
            query.setParameter("userId", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public User isExist(String login, String password) throws HibernateException {
        //  Session session = sessionFactory.openSession();
        //  Transaction transaction = session.beginTransaction();
        //  Query query = session.createQuery("from User where login = :userLogin");
        //  List<User> userList = query.setParameter("userLogin", login).list();
        Session session = null;
        Transaction transaction = null;
        Query query;
        List<User> userList;
        User userExist = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from User where login = :userLogin");
            userList = query.setParameter("userLogin", login).list();

            for (User user : userList) {
                if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                    userExist = user;
                }
            }
            //  try {
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
            //   return userExist;
        }
        return userExist;
    }

}
