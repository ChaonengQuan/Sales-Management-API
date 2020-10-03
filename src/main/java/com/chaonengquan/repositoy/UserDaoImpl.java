package com.chaonengquan.repositoy;

import com.chaonengquan.dao.UserDao;
import com.chaonengquan.model.User;
import com.chaonengquan.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public User save(User user) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            long id = (long) session.save(user);
            //session.saveOrUpdate(user);                  //if incoming record does not contains primary key, then save()
            //if it contains primary key, then call update()
            transaction.commit();
            session.close();
            user.setId(id);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return user;
    }


    @Override
    public boolean delete(User user) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();

            deleteCount = 1;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to delete record, error={}", e.getMessage());
            session.close();
        }
        return deleteCount > 0;
    }


    //could add more methods here
    @Override
    public List<User> getAllUser() {
        String hql = "select distinct user from User as user left join fetch user.roles";

        try (Session session = HibernateUtil.getSession()) {                //try with resources
            Query<User> query = session.createQuery(hql);
            return query.list();
        }
    }



    @Override
    public User getUserByCredential(String email, String password) throws Exception{
        String hql = "FROM User as u join fetch u.roles where lower(u.email) = :email and u.password = :password";
        logger.debug(String.format("User email: %s, password: %s", email, password));

        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            return query.uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("can't find user records or session");
        }
    }


    @Override
    public User getUserByName(String name){
        String hql = "FROM User as u join fetch u.roles where lower(u.name)=?1";
        logger.debug(String.format("User name is %s", name));

        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter(1, name);
            return query.uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
