//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.ItemDao;
//import com.chaonengquan.model.Customer;
//import com.chaonengquan.model.Item;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemDaoJdbcImpl  implements ItemDao {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //Step1: database configurations
//    private static final String DB_URL = "jdbc:postgresql://localhost:5431/myDatabase";
//    private static final String USER = "chaonengquan";
//    private static final String PASS = "Quanchaoneng.523";
//
//
//    @Override
//    public Item save(Item item) {
//        Item savedItem = null;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;     //good for preventing SQL injection
//
//        try {
//            //STEP 2: Open a connection
//            logger.info("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            //STEP 3: Execute a query
//            logger.debug("Insert statement...");
//            String SQL_INSERT = "INSERT INTO item (item_name, price, order_id) VALUES (?,?,?)";
//            preparedStatement = conn.prepareStatement(SQL_INSERT);
//            preparedStatement.setString(1, item.getItemName());
//            preparedStatement.setDouble(2, item.getPrice());
//            preparedStatement.setLong(3, item.getOrderId());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                savedItem = item;
//
//        } catch (SQLException e) {
//            logger.error("Call save(...) for Item throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call save(...) for Item when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return savedItem;
//    }
//
//    @Override
//    public boolean delete(Item item) {
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
//            String SQL_DELETE = "DELETE FROM item WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_DELETE);
//            preparedStatement.setLong(1, item.getId());
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                isSuccess = true;
//
//        } catch (SQLException e) {
//            logger.error("Call delete(...) for Item throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call delete(...) for Item when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//
//        return isSuccess;
//    }
//
//    @Override
//    public Item update(Item item) {
//        Item updatedItem = null;
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
//            String SQL_UPDATE = "UPDATE item SET item_name=?, price=?, order_id=? WHERE id = ?;";
//            preparedStatement = conn.prepareStatement(SQL_UPDATE);
//            preparedStatement.setString(1, item.getItemName());
//            preparedStatement.setDouble(2, item.getPrice());
//            preparedStatement.setLong(3, item.getOrderId());
//
//
//            int row = preparedStatement.executeUpdate();
//            if (row > 0)
//                updatedItem = item;
//
//        } catch (SQLException e) {
//            logger.error("Call update(...) Item throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 4: finally block used to close resources
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call update(...) Item when closing connection throws SQLException, error=" + se.getMessage());
//            }
//        }
//        return updatedItem;
//    }
//
//    @Override
//    public List<Item> getAllItem() {
//        List<Item> itemList = new ArrayList<>();
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
//            String sql = "SELECT * FROM item";
//            rs = stmt.executeQuery(sql);
//
//            //STEP 4: Extract data from result set
//            while (rs.next()) {
//                //Retrieve by column name
//                long id = rs.getLong("id");
//                String item_name = rs.getString("item_name");
//                Double price = rs.getDouble("price");
//                Long order_id = rs.getLong("order_id");
//                //Create and fill the object
//                Item newItem = new Item();
//
//                newItem.setId(id);
//                newItem.setItemName(item_name);
//                newItem.setPrice(price);
//                newItem.setOrderId(order_id);
//
//                itemList.add(newItem);
//            }
//
//        } catch (SQLException e) {
//            logger.error("Call getAllItem(...) throws SQLException, error=" + e.getMessage());
//        } finally {
//            //STEP 5: finally block used to close resources
//            try {
//                if (rs != null) rs.close();
//                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                logger.error("Call getAllItem(...) when closing connections throws SQLException, error=" + se.getMessage());
//            }
//        }
//        return itemList;
//    }
//}
