using System;
using System.Data;
using System.Data.Common;

namespace CountryApplication
{
    class Program
    {
        static void Main(string[] args)
        {
            DataTable dt = new DataTable("countries");
            DbDataAdapter adapter = new DAOCountry().createCountryAdapter();
            adapter.Fill(dt);

            dt.WriteXml(Console.Out);

            DataRow row = dt.Rows.Find("US");
            row["code"] = "USA";
            row["name"] = "United States of America";

            dt.WriteXml(Console.Out);

            adapter.Update(dt);

            Console.ReadKey();
        }

    }
}
