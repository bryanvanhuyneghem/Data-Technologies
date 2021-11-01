using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Winkel
{
    class Order
    {
        private int number;

        public int Number
        {
            get { return number; }
            set { number = value; }
        }
        private DateTime ordered;

        public DateTime Ordered
        {
            get { return ordered; }
            set { ordered = value; }
        }
        private DateTime required;

        public DateTime Required
        {
            get { return required; }
            set { required = value; }
        }
        private DateTime shipped;

        public DateTime Shipped
        {
            get { return shipped; }
            set { shipped = value; }
        }
        private string status;

        public string Status
        {
            get { return status; }
            set { status = value; }
        }
        private string comments;

        public string Comments
        {
            get { return comments; }
            set { comments = value; }
        }
        private string customerNumber;

        public string CustomerNumber
        {
            get { return customerNumber; }
            set { customerNumber = value; }
        }
        private List<OrderDetail> details;

        internal List<OrderDetail> Details
        {
            get { return details; }
            set { details = value; }
        }

    }
}
