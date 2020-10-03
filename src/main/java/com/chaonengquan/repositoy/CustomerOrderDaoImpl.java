package com.chaonengquan.repositoy;

import com.chaonengquan.dao.CustomerOrderDao;
import com.chaonengquan.model.Customer;
import com.chaonengquan.model.CustomerOrder;
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
public class CustomerOrderDaoImpl implements CustomerOrderDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public CustomerOrder save(CustomerOrder customerOrder) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            //session.save(cus);
            //if incoming record does not contains primary key, then save(), if it contains primary key, then call update()
            session.saveOrUpdate(customerOrder);
            transaction.commit();
            session.close();
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return customerOrder;
    }

    @Override
    public boolean delete(CustomerOrder customerOrder) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            session.delete(customerOrder);
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
    public CustomerOrder update(CustomerOrder customerOrder) {
        CustomerOrder updatedCustomerOrder = null;
        String hql = "update CustomerOrder custOrd set custOrd.address=?1, custOrd.payment=?2, custOrd.amount=?3, custOrd.customer=?4 where custOrd.id=?5";

        try(Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery(hql);
            query.setParameter(1, customerOrder.getAddress());
            query.setParameter(2, customerOrder.getPayment());
            query.setParameter(3, customerOrder.getAmount());
            query.setParameter(4, customerOrder.getCustomer());
            query.setParameter(5, customerOrder.getId());

            int rowCount = query.executeUpdate();
            if(rowCount > 0){
                updatedCustomerOrder = customerOrder;
            }
        }
        return updatedCustomerOrder;
    }

    @Override
    public List<CustomerOrder> getAllCustomerOrder() {
        //String hql = "FROM Customers";
        //String hql = "select distinct customer from Customers as customer left join fetch customer.orders";
        String hql = "select distinct custOrd from CustomerOrder as custOrd left join fetch custOrd.items";

        try (Session session = HibernateUtil.getSession()) { //try with resources
            Query<CustomerOrder> query = session.createQuery(hql);
            return query.list();
        }

    }
}
