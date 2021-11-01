using System.Data;
using System.Data.Common;
using Countries;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TestCountries
{
    [TestClass]
    public class TestDAOCountry
    {
        [TestMethod]
        public void TestDelete()
        {
            DAOCountry dao = new DAOCountry();
            dao.RemoveAllCountries();
            Assert.AreEqual(0, dao.Countries.Count);
        }

        [TestMethod]
        public void TestAddCountry()
        {
            DAOCountry dao = new DAOCountry();
            dao.RemoveAllCountries();
            dao.Add(new Country { Code = "BE", Name = "Belgium" });
            dao.Add(new Country { Code = "FR", Name = "France" });
            dao.Add(new Country { Code = "DE", Name = "Germany" });
            Assert.AreEqual(3, dao.Countries.Count);
        }
        [TestMethod]
        public void TestAdapter()
        {
            DAOCountry dao = new DAOCountry();
            dao.RemoveAllCountries();
            dao.Add(new Country { Code = "US", Name = "United States" });
            dao.Add(new Country { Code = "JP", Name = "Japan" });
            dao.Add(new Country { Code = "UK", Name = "United Kingdom" });

            DataTable dt = new DataTable("countries");
            DbDataAdapter adapter = new DAOCountry().createCountryAdapter();
            adapter.Fill(dt);
            Assert.AreEqual(3, dt.Rows.Count);

            DataRow row = dt.Rows.Find("US");
            row["code"] = "USA";
            row["name"] = "United States of America";

            adapter.Update(dt);

            Assert.AreEqual(3, dt.Rows.Count);
            DataTable dtLanden = new DataTable("landen");
            adapter.Fill(dtLanden);

            DataRow rowUSA = dtLanden.Rows.Find("USA");
            Assert.IsNull(rowUSA);

            DataRow rowUS = dtLanden.Rows.Find("US");
            Assert.AreEqual("United States of America",rowUS["name"]);

        }
    }
}
