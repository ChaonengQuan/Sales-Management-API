package com.chaonengquan.repositoy;

import com.chaonengquan.dao.ItemDao;
import com.chaonengquan.model.Customer;
import com.chaonengquan.model.Item;
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
public class ItemDaoImpl implements ItemDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Item save(Item item) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            //session.save(cus);
            //if incoming record does not contains primary key, then save(), if it contains primary key, then call update()
            session.saveOrUpdate(item);
            transaction.commit();
            session.close();
        } catch (TransactionException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return item;
    }

    @Override
    public boolean delete(Item item) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;

        try {
            transaction = session.beginTransaction();
            session.delete(item);
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
    public Item update(Item item) {
        Item updatedItem = null;
        String hql = "update Item item set item.itemName=?1, item.price=?2, item.customerOrder =?3 where item.id=?4";

        try (Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery(hql);
            query.setParameter(1, item.getItemName());
            query.setParameter(2, item.getPrice());
            query.setParameter(3, item.getCustomerOrder());
            query.setParameter(4, item.getId());
            int rowCount = query.executeUpdate();
            if (rowCount > 0) {
                updatedItem = item;
            }
        }

        return updatedItem;
    }

    @Override
    public List<Item> getAllItem() {
        //String hql = "FROM Customers";
        //String hql = "select distinct customer from Customers as customer left join fetch customer.orders";
        String hql = "select distinct item from Item as item left join fetch item.customerOrder";
        try (Session session = HibernateUtil.getSession()) { //try with resources
            Query<Item> query = session.createQuery(hql);
            return query.list();
        }
    }
}
