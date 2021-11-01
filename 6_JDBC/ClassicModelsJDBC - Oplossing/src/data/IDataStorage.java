/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import java.util.List;

public interface IDataStorage {
    
    public List<IProduct> getProducts() throws DataExceptie;
//    public List<ICustomer> getCustomers() throws DataExceptie;
//    public List<IOrder> getOrders() throws DataExceptie;
    public List<IOrder> getOrders(int customerNumber) throws DataExceptie;
    
//    public boolean addProduct(IProduct product) throws DataExceptie;
    public boolean addOrder(IOrder order) throws DataExceptie;
    
//    public boolean addCustomer(ICustomer customer) throws DataExceptie;
    public boolean modifyCustomer(ICustomer customer) throws DataExceptie;
//    public boolean deleteCustomer(int customerNumber) throws DataExceptie;
//    
//    public double getTotal(int customerNumber) throws DataExceptie;
    
}
