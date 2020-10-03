//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.CustomerDao;
//import com.chaonengquan.model.Customer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerDaoJdbcImpl implements CustomerDao {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //Step1: database configurations
//    private static final String DB_URL = "jdbc:postgresql://localhost:5431/myDatabase";
//    private static final String USER = "chaonengquan";
//    private static final String PASS = "Quanchaoneng.523";
//
//    @Override
//    public Customer save(Customer customer) {
//        Customer savedCustomer = null;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;     //good for preventing SQL injection
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            //STEP 3: Execute a query
//            logger.debug("Insert statement...");
//            String SQL_INSERT = "INSERT INTO customer (first_name, last_name, email, address) VALUES (?,?,?,?)";
//            preparedStatement = conn.prepareStatement(SQL_INSERT);
//            preparedStatement.setString(1, customer.getFirstName());
//            preparedStatement.setString(2, customer.getLastName());
//            preparedStatement.setString(3, customer.getEmail());
//            preparedStatement.setString(4, customer.getAddress());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                savedCustomer = customer;
//
//
//        } catch (SQLException e) {
//            logger.error("Call save(...) for Customer throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call save(...) for Customer when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return savedCustomer;
//    }
//
//    @Override
//    public boolean delete(Customer customer) {
//        boolean isSuccess = false;
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
//            String SQL_DELETE = "DELETE FROM customer WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_DELETE);
//            preparedStatement.setLong(1, customer.getId());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                isSuccess = true;
//
//        } catch (SQLException e) {
//            logger.error("Call delete(...) for Customer throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call delete(...) for Customer when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return isSuccess;
//    }
//
//    @Override
//    public Customer update(Customer customer) {     //update by id
//        Customer updatedCustomer = null;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet rs = null;
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            //STEP 3: Execute a query
//            logger.debug("Insert statement...");
//            String SQL_UPDATE = "UPDATE customers SET first_name=?, last_name=?, email=?, address=? WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_UPDATE);
//            preparedStatement.setString(1, customer.getFirstName());
//            preparedStatement.setString(2, customer.getLastName());
//            preparedStatement.setString(3, customer.getEmail());
//            preparedStatement.setString(4, customer.getAddress());
//            preparedStatement.setLong(5, customer.getId());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                updatedCustomer = customer;
//
//        } catch (SQLException e) {
//            logger.error("Call update(...) Customer throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call update(...) Customer when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//        return updatedCustomer;
//    }
//
//    @Override
//    public List<Customer> getAllCustomer() {
//        List<Customer> customerList = new ArrayList<>();
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
//            String sql = "SELECT * FROM customers";
//            rs = stmt.executeQuery(sql);
//
//            //STEP 4: Extract data from result set
//            while (rs.next()) {
//                //Retrieve by column name
//                long id = rs.getLong("id");
//                String first_name = rs.getString("first_name");
//                String last_name = rs.getString("last_name");
//                String email = rs.getString("email");
//                String address = rs.getString("address");
//                //Create and fill the object
//                Customer newCustomer = new Customer();
//
//                newCustomer.setId(id);
//                newCustomer.setFirstName(first_name);
//                newCustomer.setLastName(last_name);
//                newCustomer.setEmail(email);
//                newCustomer.setAddress(address);
//
//                customerList.add(newCustomer);
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
//        return customerList;
//    }
//
//}
