package com.chaonengquan.repositoy;

import com.chaonengquan.dao.CustomerDao;
import com.chaonengquan.model.Customer;
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

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Customer save(Customer customer) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            //session.save(cus);
            //if incoming record does not contains primary key, then save(), if it contains primary key, then call update()
            session.saveOrUpdate(customer);
            transaction.commit();
            session.close();
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        //String hql = "DELETE FROM Customer as cust where cust.id=?1";

        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            session.delete(customer);
//            Query<Customer> query = session.createQuery(hql);
//            query.setParameter(1, customer.getId());
//            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            deleteCount = 1;
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to delete record, error={}", e.getMessage());
            session.close();
        }

        return deleteCount > 0;
    }

    @Override
    public Customer update(Customer customer) {
        Customer updatedCustomer = null;
        String hql = "update Customer cust set cust.firstName=?1, cust.lastName=?2, cust.email=?3, cust.address=?4 where cust.id=?5";

        try(Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery(hql);
            query.setParameter(1, customer.getFirstName());
            query.setParameter(2, customer.getLastName());
            query.setParameter(3, customer.getEmail());
            query.setParameter(4, customer.getAddress());
            query.setParameter(5, customer.getId());
            int rowCount = query.executeUpdate();
            if(rowCount > 0){
                updatedCustomer = customer;
            }
        }

        return updatedCustomer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        //String hql = "FROM Customers";
        //String hql = "select distinct customer from Customers as customer left join fetch customer.orders";
        String hql = "select distinct cust from Customer as cust left join fetch cust.customerOrders as custOrd left join fetch custOrd.items";

        try (Session session = HibernateUtil.getSession()) { //try with resources
            Query<Customer> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public Customer getById(long id) {
        String hql = "FROM Customer AS cust WHERE cust.id=?1";
        try (Session session = HibernateUtil.getSession()) {
            Query<Customer> query = session.createQuery(hql);
            query.setParameter(1,id);
            return query.uniqueResult();
        }
    }
}
