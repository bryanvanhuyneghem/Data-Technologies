using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Winkel
{
    class Customer
    {
        private int customerNumber;

        public int CustomerNumber
        {
            get { return customerNumber; }
            set { customerNumber = value; }
        }
        private string customerName;

        public string CustomerName
        {
            get { return customerName; }
            set { customerName = value; }
        }
        private string contactLastName;

        public string ContactLastName
        {
            get { return contactLastName; }
            set { contactLastName = value; }
        }
        private string contactFirstName;

        public string ContactFirstName
        {
            get { return contactFirstName; }
            set { contactFirstName = value; }
        }
        private string phone;

        public string Phone
        {
            get { return phone; }
            set { phone = value; }
        }
        private string addressLine1;

        public string AddressLine1
        {
            get { return addressLine1; }
            set { addressLine1 = value; }
        }
        private string addressLine2;

        public string AddressLine2
        {
            get { return addressLine2; }
            set { addressLine2 = value; }
        }
        private string city;

        public string City
        {
            get { return city; }
            set { city = value; }
        }
        private string state;

        public string State
        {
            get { return state; }
            set { state = value; }
        }
        private string postalCode;

        public string PostalCode
        {
            get { return postalCode; }
            set { postalCode = value; }
        }
        private string country;

        public string Country
        {
            get { return country; }
            set { country = value; }
        }
        private int salesRepEmployeeNumber;

        public int SalesRepEmployeeNumber
        {
            get { return salesRepEmployeeNumber; }
            set { salesRepEmployeeNumber = value; }
        }
        private double creditLimit;

        public double CreditLimit
        {
            get { return creditLimit; }
            set { creditLimit = value; }
        }
    }
}
