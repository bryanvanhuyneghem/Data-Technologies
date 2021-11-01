using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Common;

namespace CountryApplication
{
    public class DAOCountry
    {
        const string DB_CONN_STRING = "countryDB";
        const string INSERT_COMM = "insertCountry";
        const string UPDATE_COMM = "updateCountry";
        const string SELECT_COMM = "selectCountry";
        const string DELETE_COMM = "deleteCountry";

        ConnectionStringSettings settings;
        DbProviderFactory factory;

        public DAOCountry()
        {
            settings = ConfigurationManager.ConnectionStrings[DB_CONN_STRING];
            factory = DbProviderFactories.GetFactory(settings.ProviderName);
        }
        public DbDataAdapter createCountryAdapter()
        {
            
            DbDataAdapter adapter = factory.CreateDataAdapter();
            adapter.MissingSchemaAction = MissingSchemaAction.AddWithKey;
            DbConnection conn = factory.CreateConnection();
            conn.ConnectionString = settings.ConnectionString;
            DbCommand command = conn.CreateCommand();
            command.CommandText = ConfigurationManager.AppSettings[SELECT_COMM];
            adapter.SelectCommand = command;
            command = conn.CreateCommand();
            command.CommandText = ConfigurationManager.AppSettings[UPDATE_COMM];
            DbParameter param = factory.CreateParameter();
            param.ParameterName = "@code";
            param.SourceVersion = DataRowVersion.Original;
            param.SourceColumn = "code";
            command.Parameters.Add(param);
            param = factory.CreateParameter();
            param.ParameterName = "@name";
            //param.SourceVersion = DataRowVersion.Current;
            param.SourceColumn = "name";
            command.Parameters.Add(param);
            adapter.UpdateCommand = command;
            return adapter;
        }

        public List<Country> Countries
        {
            get
            {
                List<Country> countries = new List<Country>();
                using (DbConnection conn = factory.CreateConnection())
                {
                    conn.ConnectionString = settings.ConnectionString;
                    DbCommand command = conn.CreateCommand();
                    command.CommandText = ConfigurationManager.AppSettings[SELECT_COMM];
                    conn.Open();
                    DbDataReader reader = command.ExecuteReader();
                    while (reader.Read())
                    {
                        Country country = new Country();
                        country.Code = reader.GetString(0);
                        country.Name = reader.GetString(1);
                        countries.Add(country);
                    }
                }
                return countries;
            }
        }

        public void Add(Country country)
        {
            using (DbConnection conn = factory.CreateConnection())
            {
                conn.ConnectionString = settings.ConnectionString;
                DbCommand command = conn.CreateCommand();
                command.CommandText = ConfigurationManager.AppSettings[INSERT_COMM];
                DbParameter param = factory.CreateParameter();
                param.ParameterName = "@code";
                param.Value = country.Code;
                command.Parameters.Add(param);
                param = factory.CreateParameter();
                param.ParameterName = "@name";
                param.Value = country.Name;
                command.Parameters.Add(param);
                conn.Open();
                command.ExecuteNonQuery();
            }
        }

        public void RemoveAllCountries()
        {
            using (DbConnection conn = factory.CreateConnection())
            {
                conn.ConnectionString = settings.ConnectionString;
                DbCommand command = conn.CreateCommand();
                command.CommandText = ConfigurationManager.AppSettings[DELETE_COMM];
                conn.Open();
                command.ExecuteNonQuery();
            }
        }
    }
}
