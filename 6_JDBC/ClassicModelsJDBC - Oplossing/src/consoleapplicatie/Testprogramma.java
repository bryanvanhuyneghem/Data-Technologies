/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleapplicatie;

import data.DataExceptie;
import data.IDataStorage;
import data.ICustomer;
import data.IOrderDetail;
import data.IProduct;
import data.IOrder;
import data.jdbc.Customer;
import data.jdbc.Order;
import data.jdbc.OrderDetail;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Testprogramma {

    /**
     * @param args the command line arguments
     */
    
     private static void showProduct(IProduct product) {
         System.out.println("Code :"+product.getProductCode());
         System.out.println("Name :"+product.getProductName());
         System.out.println("Line :"+product.getProductLine());
         System.out.println("Scale :"+product.getProductScale());
         System.out.println("Vendor :"+product.getProductVendor());
         System.out.println("Description :"+product.getProductDescription());
         System.out.println("Stock :"+product.getQuantityInStock());
         System.out.println("Price :"+product.getPrice());
         System.out.println("MSRP :"+product.getMsrp());
    }
     
//    private static void showCustomer(ICustomer customer) {
//         System.out.println("Number :"+customer.getCustomerNumber());
//         System.out.println("Name :"+customer.getCustomerName());
//         System.out.println("Contact :"+customer.getContactFirstName()+" "+customer.getContactLastName());
//         System.out.println("Phone :"+customer.getPhone());
//         System.out.println("Address :"+customer.getAddressLine1()+", "+customer.getAddressLine2());
//         System.out.println("City/State :"+customer.getCity()+" / "+customer.getState());
//         System.out.println("Postal Code / Country :"+customer.getPostalCode()+" / "+customer.getCountry());
//    }
//    
    private static void showOrder(IOrder order) {
         System.out.println("Number :"+order.getNumber());
         System.out.println("Ordered :"+order.getOrdered().toString());
         System.out.println("Required :"+order.getRequired().toString());
         if(order.getShipped() != null){
            System.out.println("Shipped :"+order.getShipped().toString());
         }
         System.out.println("Status :"+order.getStatus());
         System.out.println("Comments :"+order.getComments());
         System.out.println("Customer Nr. :"+order.getCustomerNumber());
    }

    private static ICustomer makeTestCustomer(){
        return new Customer(496,"Bruce Wayne","Wayne","Bruce","555-871364","Bat-Cave 1","2nd A",
                "Gotham","","","USA",0,0);
    }
    
    private static IOrder makeTestOrder(){
        IOrder order = null;
        try{
            DateFormat d = new SimpleDateFormat("dd/MM/yyyy");
            List<IOrderDetail> details = new ArrayList<>();
            details.add(new OrderDetail(20000,"BML9000",1,20.95,2));
            details.add(new OrderDetail(20000,"CAPE-32",3,15.95,4));
            order = new Order(20000,d.parse("15/12/2010"),d.parse("23/12/2010"),null,"In Process",null,141,details);
        }catch(Exception excp){
            System.out.println("Fout tijdens het maken van test_order:" + excp.getMessage());
        }
        return order;
    }
    
    public static void main(String[] args) throws DataExceptie {
        // TODO code application logic here
        try {
            IDataStorage database = (IDataStorage) Class.forName("data.jdbc.JDBCDataStorage").newInstance();
            
            //Test lijst producten
            System.out.println("Lijst van producten");
            for (IProduct product : database.getProducts()) {
                showProduct(product);
            }
            
//            //Test lijst klanten
//            System.out.println("Lijst van klanten");
//            for (ICustomer customer : database.getCustomers()) {
//                showCustomer(customer);
//            }
//
//            //Test lijst bestellingen
//            System.out.println("Lijst van bestellingen");
//            for (IOrder order : database.getOrders()) {
//                showOrder(order);
//            }

            //Test lijst bestellingen klant
            System.out.println("Lijst van bestellingen van klant 141");
            for (IOrder order : database.getOrders(141)) { //moeilijke klant blijkbaar :D
                showOrder(order);
            }
            
            //Test toevoegen bestelling
            boolean resultaat = database.addOrder(makeTestOrder());
            if (resultaat) {
                System.out.println("Het toevoegen van de order is gelukt");
            }
            else {
                System.out.println("Het toevoegen van de order is mislukt");
            }
            
//            //Test toevoegen klant
//            resultaat = database.addCustomer(makeTestCustomer());
//            if (resultaat) {
//                System.out.println("Het toevoegen van de klant is gelukt");
//            }
//            else {
//                System.out.println("Het toevoegen van de klant is mislukt");
//            }
            
            //Test wijzigen klant
            resultaat = database.modifyCustomer(makeTestCustomer());
            if (resultaat) {
                System.out.println("Het wijzigen van de klant is gelukt");
            }
            else {
                System.out.println("Het wijzigen van de klant is mislukt");
            }
            
//            //Test wissen klant
//            resultaat = database.deleteCustomer(makeTestCustomer().getCustomerNumber());
//            if (resultaat) {
//                System.out.println("Het wissen van de klant is gelukt");
//            }
//            else {
//                System.out.println("Het wissen van de klant is mislukt");
//            }
//            
//            //Test stored procedure
//            int customerNr = 141;
//            double totaal = database.getTotal(customerNr);
//            System.out.println("Totaal van klant nr. "+customerNr+": "+totaal);
            
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }


    }
    
}
