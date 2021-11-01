using System.Data.Common;
using System.Configuration;
using System.Data;
using System;
using System.Collections.Generic;

namespace Winkel
{
    class DataStorage
    {

        private ConnectionStringSettings connStringSet;
        private DbProviderFactory factory;

        public DataStorage()
        {
            this.connStringSet = ConfigurationManager.ConnectionStrings["classicmodels"]; ;
            factory = DbProviderFactories.GetFactory(connStringSet.ProviderName);

        }

        protected DbConnection GetConnection()
        {
            DbConnection connection =
                factory.CreateConnection();
            connection.ConnectionString =
                connStringSet.ConnectionString;

            return connection;
        }

        private DbCommand CreateCommand(DbConnection conn, string text)
        {
            DbCommand command = conn.CreateCommand();
            command.CommandText = text;
            return command;
        }
        private DbCommand CreateCommand(DbConnection conn, DbTransaction trans, string text)
        {
            DbCommand command = conn.CreateCommand();
            command.Transaction = trans;
            command.CommandText = text;
            return command;
        }

        private DbParameter CreateParam(DbCommand command, string parameter, string value)
        {
            DbParameter param = command.CreateParameter();
            param.ParameterName = parameter;
            param.DbType = DbType.String;
            param.Value = value;
            return param;

        }

        private DbParameter CreateParam(DbCommand command, string parameter, int value)
        {
            DbParameter param = command.CreateParameter();
            param.ParameterName = parameter;
            param.DbType = DbType.Int32;
            param.Value = value;
            return param;

        }

        private DbParameter CreateParam(DbCommand command, string parameter, DateTime value)
        {
            DbParameter param = command.CreateParameter();
            param.ParameterName = parameter;
            param.DbType = DbType.DateTime;
            param.Value = value;
            return param;

        }

        private DbParameter CreateParam(DbCommand command, string parameter, double value)
        {
            DbParameter param = command.CreateParameter();
            param.ParameterName = parameter;
            param.DbType = DbType.Double;
            param.Value = value;
            return param;

        }

        public void InsertCustomer(Customer c)
        {

            // verbinding met de gegevensbank
            DbConnection conn = GetConnection();
            // commando-object aanmaken
            DbCommand command = CreateCommand(conn, ConfigurationManager.AppSettings["insert_customer"]);
            // zoekopdracht uitvoeren

            try
            {
                command.Parameters.Add(CreateParam(command, "@customerNumber", c.CustomerNumber));
                command.Parameters.Add(CreateParam(command, "@customerName", c.CustomerName));
                command.Parameters.Add(CreateParam(command, "@contactLastName", c.ContactLastName));
                command.Parameters.Add(CreateParam(command, "@contactFirstName", c.ContactFirstName));
                command.Parameters.Add(CreateParam(command, "@phone", c.Phone));
                command.Parameters.Add(CreateParam(command, "@addressLine1", c.AddressLine1));
                command.Parameters.Add(CreateParam(command, "@addressLine2", c.AddressLine2));
                command.Parameters.Add(CreateParam(command, "@city", c.City));
                command.Parameters.Add(CreateParam(command, "@state", c.State));
                command.Parameters.Add(CreateParam(command, "@postalCode", c.PostalCode));
                command.Parameters.Add(CreateParam(command, "@country", c.Country));
                command.Parameters.Add(CreateParam(command, "@salesRepEmployeeNumber", c.SalesRepEmployeeNumber));
                command.Parameters.Add(CreateParam(command, "@creditLimit", c.CreditLimit));
                conn.Open();
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                conn.Close();
            }

        }


        public List<Customer> SelectCustomers()
        {
            List<Customer> customers = new List<Customer>();

            // verbinding met de gegevensbank
            DbConnection conn = GetConnection();
            // commando-object aanmaken
            DbCommand command = CreateCommand(conn, ConfigurationManager.AppSettings["select_customers"]);
            // zoekopdracht uitvoeren
            conn.Open();
            try
            {
                DbDataReader reader =
                    command.ExecuteReader();
                while (reader.Read())
                {
                    Customer c = new Customer();
                    c.CustomerNumber = reader.GetInt32(0);
                    c.CustomerName = reader["customerName"].ToString();
                    c.ContactLastName = reader["contactLastName"].ToString();
                    c.ContactFirstName = reader["contactFirstName"].ToString();
                    c.Phone = reader["phone"].ToString();
                    c.AddressLine1 = reader["addressLine1"].ToString();
                    if (!(reader["addressLine2"] is System.DBNull)) c.AddressLine2 = reader["addressLine2"].ToString();
                    c.City = reader["city"].ToString();
                    if (!(reader["state"] is System.DBNull)) c.State = reader["state"].ToString();
                    if (!(reader["postalCode"] is System.DBNull)) c.PostalCode = reader["postalCode"].ToString();
                    c.Country = reader["country"].ToString();
                    if (!(reader["salesRepEmployeeNumber"] is System.DBNull)) c.SalesRepEmployeeNumber = reader.GetInt32(11);
                    if (!(reader["creditLimit"] is System.DBNull)) c.CreditLimit = reader.GetDouble(12);

                    customers.Add(c);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                conn.Close();
            }

            return customers;
        }


        public void insertOrder(Order ord)
        {
            // verbinding met de gegevensbank
            DbConnection conn = GetConnection();
            conn.Open();
            DbTransaction trans = conn.BeginTransaction();
            try
            {
            
                // commando-object aanmaken
                DbCommand comOrders = CreateCommand(conn,trans, ConfigurationManager.AppSettings["insert_orders"]);
                

                comOrders.Parameters.Add(CreateParam(comOrders, "@orderNumber", ord.Number));
                comOrders.Parameters.Add(CreateParam(comOrders, "@orderDate", ord.Ordered));
                comOrders.Parameters.Add(CreateParam(comOrders, "@requiredDate", ord.Required));
                comOrders.Parameters.Add(CreateParam(comOrders, "@shippedDate", ord.Shipped));
                comOrders.Parameters.Add(CreateParam(comOrders, "@status", ord.Status));
                comOrders.Parameters.Add(CreateParam(comOrders, "@comments", ord.Comments));
                comOrders.Parameters.Add(CreateParam(comOrders, "@customerNumber", ord.CustomerNumber));
                comOrders.ExecuteNonQuery();

                foreach (OrderDetail detail in ord.Details)
                {
                    DbCommand comOrderDetails = CreateCommand(conn, trans, ConfigurationManager.AppSettings["insert_orderdetails"]);
                    comOrderDetails.Parameters.Add(CreateParam(comOrderDetails,"@orderNumber",ord.Number));
                    comOrderDetails.Parameters.Add(CreateParam(comOrderDetails,"@productCode",detail.ProductCode));
                    comOrderDetails.Parameters.Add(CreateParam(comOrderDetails,"@quantityOrdered",detail.Quantity));
                    comOrderDetails.Parameters.Add(CreateParam(comOrderDetails,"@priceEach",detail.Price));
                    comOrderDetails.Parameters.Add(CreateParam(comOrderDetails,"@orderLineNumber",detail.OrderLineNumber));
                    comOrderDetails.ExecuteNonQuery();
                }


                trans.Commit();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Hier is de fout!");
                Console.WriteLine(ex.StackTrace);
                trans.Rollback();
            }
            finally
            {
                conn.Close();
            }
        }
    }
}
