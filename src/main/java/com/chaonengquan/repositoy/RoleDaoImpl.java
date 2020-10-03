package com.chaonengquan.repositoy;


import com.chaonengquan.dao.RoleDao;
import com.chaonengquan.model.Role;
import com.chaonengquan.model.User;
import com.chaonengquan.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Role save(Role role) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            //session.saveOrUpdate(cus);                //if incoming record does not contains primary key, then save()
            long id = (long) session.save(role);
            //if it contains primary key, then call update()
            transaction.commit();
            session.close();
            role.setId(id);
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }

        return role;
    }


    @Override
    public boolean delete(Role role) {
        String hql = "DELETE FROM Role as role where role.id=?1";

        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            //session.delete(role);         //TODO read doc about seesion.delete, it causes bug
            Query<User> query = session.createQuery(hql);
            query.setParameter(1, role.getId());
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();

            //deleteCount = 1;
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to delete record, error={}", e.getMessage());
            session.close();
        }
        return deleteCount > 0;
    }

}
