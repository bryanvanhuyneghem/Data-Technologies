using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Common;

namespace Countries
{
    public class DAOCountry
    {
        const string DB_CONN_STRING = "countryDB";
        const string INSERT_COMM = "insertCountry";
        const string UPDATE_COMM = "updateCountry";
        const string SELECT_COMM = "selectCountry";
        const string DELETE_COMM = "deleteCountry";

        public DAOCountry()
        {
        }
        
        public List<Country> Countries
        {
            get
            {
                return null;
            }
        }
        
        public void RemoveAllCountries()
        {
        }
        
        public void Add(Country country)
        {
        }

        public DbDataAdapter createCountryAdapter()
        {
            return null;
        }

    }
}
