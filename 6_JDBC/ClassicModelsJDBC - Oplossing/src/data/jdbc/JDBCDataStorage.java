/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.jdbc;

import data.DataExceptie;
import data.IDataStorage;
import data.IOrder;
import data.IOrderDetail;
import data.ICustomer;
import data.IProduct;
//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCDataStorage implements IDataStorage {

    private final ResourceBundle constanten;

    
    public JDBCDataStorage() throws ClassNotFoundException {
        constanten = ResourceBundle.getBundle("data.jdbc.databankconstanten");
        //Class.forName(constanten.getString("driver")); //Niet meer nodig
    }
    

    private Connection openConnectie() throws SQLException {
        return DriverManager.getConnection(constanten.getString("connectiestring"), constanten.getString("username"), constanten.getString("password"));
    }

    
    //Maak product
    private IProduct createProduct(ResultSet rs) throws SQLException, DataExceptie {

        return new Product(rs.getString(constanten.getString("prod_code")),
                rs.getString(constanten.getString("prod_name")),
                rs.getString(constanten.getString("prod_line")),
                rs.getString(constanten.getString("prod_scale")),
                rs.getString(constanten.getString("prod_vendor")),
                rs.getString(constanten.getString("prod_description")),
                rs.getInt(constanten.getString("prod_stock")),
                rs.getDouble(constanten.getString("prod_price")),
                rs.getDouble(constanten.getString("prod_msrp")));
    }
    
    
//    //Maak customer
//    private ICustomer createCustomer(ResultSet rs) throws SQLException, DataExceptie {
//
//        return new Customer(rs.getInt(constanten.getString("klant_number")),
//                rs.getString(constanten.getString("klant_name")),
//                rs.getString(constanten.getString("klant_lname")),
//                rs.getString(constanten.getString("klant_fname")),
//                rs.getString(constanten.getString("klant_phone")),
//                rs.getString(constanten.getString("klant_address1")),
//                rs.getString(constanten.getString("klant_address2")),
//                rs.getString(constanten.getString("klant_city")),
//                rs.getString(constanten.getString("klant_state")),
//                rs.getString(constanten.getString("klant_pcode")),
//                rs.getString(constanten.getString("klant_country")),
//                rs.getInt(constanten.getString("klant_repnumber")),
//                rs.getDouble(constanten.getString("klant_climit")));
//    }

    
    //Maak product
    private IOrder createOrder(ResultSet rs) throws SQLException, DataExceptie {

        return new Order(rs.getInt(constanten.getString("order_number")),
                rs.getDate(constanten.getString("order_date")),
                rs.getDate(constanten.getString("order_rdate")),
                rs.getDate(constanten.getString("order_sdate")),
                rs.getString(constanten.getString("order_status")),
                rs.getString(constanten.getString("order_comments")),
                rs.getInt(constanten.getString("order_cnumber")),
                null);
    }
    
    private void addOrder(Connection conn, IOrder order) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(constanten.getString("insert_order"))) {
            stmt.setInt(1,order.getNumber());
            stmt.setDate(2,new java.sql.Date(order.getOrdered().getTime()));
            stmt.setDate(3,new java.sql.Date(order.getRequired().getTime()));
            stmt.setNull(4,java.sql.Types.DATE); //Nog niet verzonden, dus geen ShippingDate
            stmt.setString(5,order.getStatus());
            if(order.getComments() != null && !order.getComments().equals("")){
                stmt.setString(6,order.getComments());
            }else{
                stmt.setNull(6,java.sql.Types.VARCHAR);
            }
            stmt.setInt(7,order.getCustomerNumber());
            stmt.executeUpdate();
        }
    }
    
    
    private void addOrderDetail(Connection conn, IOrderDetail detail) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(constanten.getString("insert_orderdetail"))) {
            stmt.setInt(1,detail.getOrderNumber());
            stmt.setString(2,detail.getProductCode());
            stmt.setInt(3,detail.getQuantity());
            stmt.setDouble(4,detail.getPrice());
            stmt.setInt(5,detail.getOrderLineNumber());
            stmt.executeUpdate();
        }
    }
    
    
    @Override
    public List<IProduct> getProducts() throws DataExceptie {
        List<IProduct> products = null;

        try {
            try (Connection conn = openConnectie(); Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(constanten.getString("select_products"));
                products = new ArrayList<>();
                while (rs.next()) {
                    products.add(createProduct(rs));
                }
            }

        } catch (SQLException ex) {
            throw new DataExceptie(constanten.getString("fout_products"));
        }

        return products;
    }

    
//    @Override
//    public List<ICustomer> getCustomers() throws DataExceptie {
//        List<ICustomer> customers = null;
//
//        try {
//            try (Connection conn = openConnectie(); Statement stmt = conn.createStatement()) {
//                ResultSet rs = stmt.executeQuery(constanten.getString("select_customers"));
//                customers = new ArrayList<>();
//                while (rs.next()) {
//                    customers.add(createCustomer(rs));
//                }
//            }
//
//        } catch (SQLException ex) {
//            throw new DataExceptie(constanten.getString("fout_customers"));
//        }
//
//        return customers;
//    }
//    
//    //Lijst van bestellingen zonder details
//    @Override
//    public List<IOrder> getOrders() throws DataExceptie {
//        List<IOrder> orders = null;
//
//        try {
//            try (Connection conn = openConnectie(); Statement stmt = conn.createStatement()) {
//                ResultSet rs = stmt.executeQuery(constanten.getString("select_orders"));
//                orders = new ArrayList<>();
//                while (rs.next()) {
//                    orders.add(createOrder(rs));
//                }
//            }
//
//        } catch (SQLException ex) {
//            throw new DataExceptie(constanten.getString("fout_orders"));
//        }
//
//        return orders;
//    }
    
    
    //Lijst van bestellingen van een gegeven klant (zonder details)
    @Override
    public List<IOrder> getOrders(int customerNumber) throws DataExceptie {
        List<IOrder> orders = null;

        try {
            try (Connection conn = openConnectie(); 
            PreparedStatement stmt = conn.prepareStatement(constanten.getString("select_orders_klant"))) {
                stmt.setInt(1,customerNumber);
                ResultSet rs = stmt.executeQuery();
                orders = new ArrayList<>();
                while (rs.next()) {
                    orders.add(createOrder(rs));
                }
            }

        } catch (SQLException ex) {
            throw new DataExceptie(constanten.getString("fout_orders"));
        }

        return orders;
    }
    
        
//    @Override
//    public boolean addProduct(IProduct product) throws DataExceptie {
//        boolean resultaat = false;
//        try{
//            try(Connection conn = openConnectie(); PreparedStatement stmt = conn.prepareStatement(constanten.getString("insert_product"))) {
//                stmt.setString(1,product.getProductCode());
//                stmt.setString(2,product.getProductName());
//                stmt.setString(3,product.getProductLine());
//                stmt.setString(4,product.getProductScale());
//                stmt.setString(5,product.getProductVendor());
//                stmt.setString(6,product.getProductDescription());
//                stmt.setInt(7,product.getQuantityInStock());
//                stmt.setDouble(8,product.getPrice());
//                stmt.setDouble(9,product.getMsrp());
//                stmt.executeUpdate();
//                resultaat = true;
//            }
//        } catch (SQLException ex) {
//            throw new DataExceptie(constanten.getString("fout_add_product"));
//        }
//        
//        return resultaat;
//    }
//    
    
    @Override
    public boolean addOrder(IOrder order) throws DataExceptie {
        boolean resultaat = false;
        try{
            Connection conn = openConnectie();
            try{
                conn.setAutoCommit(false);
                addOrder(conn,order);
                
                for(IOrderDetail detail : order.getDetails()){
                    addOrderDetail(conn,detail);
                }
                
                conn.commit();
                resultaat = true;
            } catch (SQLException se) {
                conn.rollback();
                resultaat = false;
            } finally{
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch ( Exception ex ) {
            throw new DataExceptie(constanten.getString("fout_add_order"));
        }
        return resultaat;

    }
    
    
//    @Override
//    public boolean addCustomer(ICustomer customer) throws DataExceptie {
//        boolean resultaat = false;
//        
//        try{
//            try(Connection conn = openConnectie(); PreparedStatement stmt = conn.prepareStatement(constanten.getString("insert_customer"))) {
//                stmt.setInt(1,customer.getCustomerNumber());
//                stmt.setString(2,customer.getCustomerName());
//                stmt.setString(3,customer.getContactLastName());
//                stmt.setString(4,customer.getContactFirstName());
//                stmt.setString(5,customer.getPhone());
//                stmt.setString(6,customer.getAddressLine1());
//                if(customer.getAddressLine2() != null && !customer.getAddressLine2().equals("")){
//                    stmt.setString(7,customer.getAddressLine2());
//                }else{
//                    stmt.setNull(7,java.sql.Types.VARCHAR);
//                }
//                stmt.setString(8,customer.getCity());
//                if(customer.getState() != null && !customer.getState().equals("")){
//                    stmt.setString(9,customer.getState());
//                }else{
//                    stmt.setNull(9,java.sql.Types.VARCHAR);
//                }
//                if(customer.getPostalCode() != null && !customer.getPostalCode().equals("")){
//                    stmt.setString(10,customer.getPostalCode());
//                }else{
//                    stmt.setNull(10,java.sql.Types.VARCHAR);
//                }
//                stmt.setString(11,customer.getCountry());
//                if(customer.getSalesRepEmployeeNumber() != 0){
//                    stmt.setInt(12,customer.getSalesRepEmployeeNumber());
//                }else{
//                    stmt.setNull(12,java.sql.Types.INTEGER);
//                }
//                if(customer.getCreditLimit() != 0){
//                    stmt.setDouble(13,customer.getCreditLimit());
//                }else{
//                    stmt.setNull(13,java.sql.Types.DOUBLE);
//                }
//                stmt.executeUpdate();
//                resultaat = true;
//            }
//        } catch ( SQLException ex ) {
//            throw new DataExceptie(constanten.getString("fout_insert_customer"));
//        }
//        
//        return resultaat;
//    }
//    
    
    @Override
    public boolean modifyCustomer(ICustomer customer) throws DataExceptie {
        boolean resultaat = false;
        
        try{
            try(Connection conn = openConnectie(); PreparedStatement stmt = conn.prepareStatement(constanten.getString("update_customer"))) {
                stmt.setString(1,customer.getCustomerName());
                stmt.setString(2,customer.getContactLastName());
                stmt.setString(3,customer.getContactFirstName());
                stmt.setString(4,customer.getPhone());
                stmt.setString(5,customer.getAddressLine1());
                if(customer.getAddressLine2() != null && !customer.getAddressLine2().equals("")){
                    stmt.setString(6,customer.getAddressLine2());
                }else{
                    stmt.setNull(6,java.sql.Types.VARCHAR);
                }
                stmt.setString(7,customer.getCity());
                if(customer.getState() != null && !customer.getState().equals("")){
                    stmt.setString(8,customer.getState());
                }else{
                    stmt.setNull(8,java.sql.Types.VARCHAR);
                }
                if(customer.getPostalCode() != null && !customer.getPostalCode().equals("")){
                    stmt.setString(9,customer.getPostalCode());
                }else{
                    stmt.setNull(9,java.sql.Types.VARCHAR);
                }
                stmt.setString(10,customer.getCountry());
                if(customer.getSalesRepEmployeeNumber() != 0){
                    stmt.setInt(11,customer.getSalesRepEmployeeNumber());
                }else{
                    stmt.setNull(11,java.sql.Types.INTEGER);
                }
                if(customer.getCreditLimit() != 0){
                    stmt.setDouble(12,customer.getCreditLimit());
                }else{
                    stmt.setNull(12,java.sql.Types.DOUBLE);
                }
                stmt.setInt(13,customer.getCustomerNumber());
                stmt.executeUpdate();
                resultaat = true;
            }
        } catch ( SQLException ex ) {
            throw new DataExceptie(constanten.getString("fout_update_customer"));
        }
        
        return resultaat;
    }
    
    
//    @Override
//    public boolean deleteCustomer(int customerNumber) throws DataExceptie {
//        boolean resultaat = false;
//        
//        try{
//            try(Connection conn = openConnectie(); PreparedStatement stmt = conn.prepareStatement(constanten.getString("delete_customer"))) {
//                stmt.setInt(1,customerNumber);
//                stmt.executeUpdate();
//                resultaat = true;
//            }
//        } catch ( SQLException ex ) {
//            throw new DataExceptie(constanten.getString("fout_delete_customer"));
//        }
//        
//        return resultaat;
//    }
//    
//    
//    @Override
//    public double getTotal(int customerNumber) throws DataExceptie {
//        double resultaat = 0;
//        
//        try{
//            try(Connection conn = openConnectie(); CallableStatement stmt = conn.prepareCall(constanten.getString("procedure_get_total"))) {
//                stmt.setInt(1,customerNumber);
//                stmt.registerOutParameter(2,java.sql.Types.DOUBLE);
//                stmt.executeUpdate();
//                resultaat = stmt.getDouble(2);
//            }
//        } catch ( SQLException ex ) {
//            throw new DataExceptie(constanten.getString("fout_get_total"));
//        }
//        
//        return resultaat;
//    }
}
