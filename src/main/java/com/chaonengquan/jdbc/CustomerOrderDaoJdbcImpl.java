//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.CustomerOrderDao;
//import com.chaonengquan.model.CustomerOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerOrderDaoJdbcImpl implements CustomerOrderDao {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //Step1: database configurations
//    private static final String DB_URL = "jdbc:postgresql://localhost:5431/myDatabase";
//    private static final String USER = "chaonengquan";
//    private static final String PASS = "Quanchaoneng.523";
//
//
//    @Override
//    public CustomerOrder save(CustomerOrder customerOrder) {
//        CustomerOrder savedCustomerOrder = null;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;     //good for preventing SQL injection
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            //STEP 3: Execute a query
//            logger.debug("Insert statement...");
//            String SQL_INSERT = "insert into customer_order (address, payment, amount, customer_id) values (?,?,?,?)";
//            preparedStatement = conn.prepareStatement(SQL_INSERT);
//            preparedStatement.setString(1, customerOrder.getAddress());
//            preparedStatement.setString(2, customerOrder.getPayment());
//            preparedStatement.setDouble(3, customerOrder.getAmount());
//            preparedStatement.setLong(4, customerOrder.getCustomerID());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                savedCustomerOrder = customerOrder;
//
//
//        } catch (SQLException e) {
//            logger.error("Call save(...) for CustomerOrder throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call save(...) for CustomerOrder when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return savedCustomerOrder;
//    }
//
//    @Override
//    public boolean delete(CustomerOrder customerOrder) {
//        boolean status = false;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            //STEP 3: Execute a query
//            logger.debug("Insert statement...");
//            String SQL_DELETE = "DELETE FROM customer_order WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_DELETE);
//            preparedStatement.setLong(1, customerOrder.getId());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                status = true;
//
//        } catch (SQLException e) {
//            logger.error("Call delete(...) for CustomerOrder throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call delete(...) for CustomerOrder when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return status;
//    }
//
//    @Override
//    public CustomerOrder update(CustomerOrder customerOrder) {
//        CustomerOrder updatedCustomerOrder = null;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet rs = null;
//
//        try{
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            logger.debug("Insert statement...");
//            String SQL_UPDATE = "UPDATE customer_order SET address=?, payment=?, amount=?, customer_id=? WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_UPDATE);
//            preparedStatement.setString(1, customerOrder.getAddress());
//            preparedStatement.setString(2, customerOrder.getPayment());
//            preparedStatement.setDouble(3, customerOrder.getAmount());
//            preparedStatement.setLong(4, customerOrder.getCustomerID());
//            preparedStatement.setLong(5, customerOrder.getId());
//
//
//        } catch (SQLException e) {
//            logger.error("Call update(...) CustomerOrder throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call update(...) CustomerOrder when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return updatedCustomerOrder;
//    }
//
//    @Override
//    public List<CustomerOrder> getAllCustomerOrder() {
//        List<CustomerOrder> customerOrderList = new ArrayList<>();
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//
//            //STEP 3: Execute a query
//            logger.debug("Creating statement...");
//            stmt = conn.createStatement();
//            String sql = "SELECT * FROM customer_order";
//            rs = stmt.executeQuery(sql);
//
//            //STEP 4: Extract data from result set
//            while (rs.next()) {
//                //Retrieve by column name
//                long id = rs.getLong("id");
//                String address = rs.getString("address");
//                String payment = rs.getString("payment");
//                Double amount = rs.getDouble("amount");
//                long customerId = rs.getLong("customer_id");
//                //Create and fill the object
//                CustomerOrder newCustomerOrder = new CustomerOrder();
//
//                newCustomerOrder.setId(id);
//                newCustomerOrder.setAddress(address);
//                newCustomerOrder.setPayment(payment);
//                newCustomerOrder.setAmount(amount);
//                newCustomerOrder.setCustomerID(customerId);
//
//                customerOrderList.add(newCustomerOrder);
//            }
//
//        } catch (SQLException e) {
//            logger.error("Call getAllCustomers(...) throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 5: finally block used to close resources
//            try {
//                if (rs != null) rs.close();
//                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call getAllCustomer(...) when closing connections throws SQLException, error=" + se.getMessage());
//            }
//        }
//        return customerOrderList;
//    }
//}
