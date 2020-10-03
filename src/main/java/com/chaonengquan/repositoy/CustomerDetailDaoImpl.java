package com.chaonengquan.repositoy;

import com.chaonengquan.dao.CustomerDetailDao;
import com.chaonengquan.model.CustomerDetail;
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
public class CustomerDetailDaoImpl implements CustomerDetailDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CustomerDetail save(CustomerDetail customerDetail) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            //session.save(cus);
            //if incoming record does not contains primary key, then save(), if it contains primary key, then call update()
            session.saveOrUpdate(customerDetail);
            transaction.commit();
            session.close();
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return customerDetail;
    }

    @Override
    public boolean delete(CustomerDetail customerDetail) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            session.delete(customerDetail);
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
    public CustomerDetail update(CustomerDetail customerDetail) {
        CustomerDetail updatedCustomerDetail = null;
        String hql = "update CustomerDetail custDeat set custDeat.description=?1, custDeat.gender=?2, custDeat.membership=?3, custDeat.customer=?4 where custDeat.id=?5";

        try(Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery(hql);
            query.setParameter(1, customerDetail.getDescription());
            query.setParameter(2, customerDetail.getGender());
            query.setParameter(3, customerDetail.getMembership());
            query.setParameter(4, customerDetail.getCustomer());
            query.setParameter(5, customerDetail.getId());
            int rowCount = query.executeUpdate();
            if(rowCount > 0){
                updatedCustomerDetail = customerDetail;
            }
        }

        return updatedCustomerDetail;
    }

    @Override
    public List<CustomerDetail> getAllCustomer() {
        //String hql = "FROM Customers";
        //String hql = "select distinct customer from Customers as customer left join fetch customer.orders";
        String hql = "select distinct custDeat from CustomerDetail as custDeat left join fetch custDeat.customer";

        try (Session session = HibernateUtil.getSession()) { //try with resources
            Query<CustomerDetail> query = session.createQuery(hql);
            return query.list();
        }
    }
}
