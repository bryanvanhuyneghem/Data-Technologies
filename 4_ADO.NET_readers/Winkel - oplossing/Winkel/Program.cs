using System;
using System.Data;
using System.Collections.Generic;

namespace Winkel
{
    class Program
    {
        static void Main(string[] args)
        {

            DataStorage ds = new DataStorage();

            //Lijst van customers tonen

            /*List<Customer> customers = ds.SelectCustomers();
            foreach (Customer c in customers) 
                Console.WriteLine(c.CustomerName);*/

            //Customer toevoegen
            
            /*Customer c = new Customer();
            c.CustomerNumber = 876;
            c.CustomerName = "Shen Comics";
            c.ContactLastName = "De Tal";
            c.ContactFirstName = "Fulanita";
            c.Phone = "917654321";
            c.AddressLine1 = "Marques de Santa Ana 23";
            c.AddressLine2 = "Entresuelo A";
            c.City = "Madrid";
            c.State = "Madrid";
            c.Country = "Spain";
            c.SalesRepEmployeeNumber = 988723;
            c.CreditLimit = 15000;
            ds.InsertCustomer(c);*/

            //Order toevoegen;

            /*List<OrderDetail> lijst = new List<OrderDetail>();

            OrderDetail o = new OrderDetail();
            o.OrderLineNumber = 1;
            o.Price = 5.5;
            o.ProductCode = "k";
            o.Quantity = 50;

            lijst.Add(o);

            o = new OrderDetail();
            o.OrderLineNumber = 2;
            o.ProductCode = "f";
            o.Price = 5.5;
            o.Quantity = 7;

            lijst.Add(o);

            Order ord = new Order();

            ord.Number = 58780;
            ord.Ordered = new DateTime(1998, 5, 5);
            ord.Required = new DateTime(2000, 11, 12);
            ord.Shipped = new DateTime(2010, 5, 5);
            ord.Status = "onderweg";
            ord.Comments = "Bloh bloh";
            ord.CustomerNumber = "5";
            ord.Details = lijst;

            ds.insertOrder(ord);*/


            string stop = Console.ReadLine();

        }
    }
}
