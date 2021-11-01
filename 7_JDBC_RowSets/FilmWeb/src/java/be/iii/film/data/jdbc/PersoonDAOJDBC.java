/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.iii.film.data.jdbc;

import be.iii.film.Persoon;
import be.iii.film.PersoonDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author tiwi
 */
public class PersoonDAOJDBC implements PersoonDAO, Serializable {

    CachedRowSet personenRS;
    CachedRowSet wijzigbarePersonenRS;

    public PersoonDAOJDBC() {
        try {
            RowSetFactory myRowSetFactory = RowSetProvider.newFactory();
            personenRS = myRowSetFactory.createCachedRowSet();

            personenRS.setDataSourceName("jdbc/film");

            personenRS.setCommand("select naam, geboortedatum, geslacht from personen");
            personenRS.execute();

            wijzigbarePersonenRS = myRowSetFactory.createCachedRowSet();

            wijzigbarePersonenRS.setDataSourceName("jdbc/film");

            wijzigbarePersonenRS.setCommand("select id, naam, geboortedatum, geslacht from personen");
            int[] id = {1};
            wijzigbarePersonenRS.setKeyColumns(id);
            wijzigbarePersonenRS.execute();
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void voegPersoonToe(String naam, Date geboortedatum, char geslacht) {
        try {
            personenRS.moveToInsertRow();
            personenRS.updateString("NAAM", naam);
            personenRS.updateDate("GEBOORTEDATUM", new java.sql.Date(geboortedatum.getTime()));
            personenRS.updateString("GESLACHT", String.valueOf(geslacht));
            personenRS.insertRow();
            personenRS.moveToCurrentRow();
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, e);
        }

    }

//    @Override
//    public Collection<Persoon> getPersonen() {
//        List<Persoon> personen = new ArrayList<>();
//        try {
//            personenRS.beforeFirst();
//            while (personenRS.next()) {
//                Persoon p = new Persoon();
//                p.setNaam(personenRS.getString("NAAM"));
//                p.setGeboortedatum(personenRS.getDate("GEBOORTEDATUM"));
//                p.setGeslacht(personenRS.getString("GESLACHT").charAt(0));
//                personen.add(p);
//            }
//
//        } catch (SQLException ex) {
//            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//        return personen;
//    }

    @Override
    public void bewaarAlles() {
        try {
            personenRS.acceptChanges();
        } catch (SyncProviderException ex) {
            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void wijzigPersoon(Persoon persoon) {
        try {
            wijzigbarePersonenRS.beforeFirst();
            while (wijzigbarePersonenRS.next()) {
                if (wijzigbarePersonenRS.getInt("ID") == persoon.getId()) {
                    wijzigbarePersonenRS.updateString("NAAM", persoon.getNaam());
                    wijzigbarePersonenRS.updateDate("GEBOORTEDATUM", new java.sql.Date(persoon.getGeboortedatum().getTime()));
                    wijzigbarePersonenRS.updateRow();
                    wijzigbarePersonenRS.acceptChanges();
                    return;
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<Persoon> getPersonen() {
        List<Persoon> personen = new ArrayList<>();
        try {
            wijzigbarePersonenRS.acceptChanges();
            wijzigbarePersonenRS.beforeFirst();
            while (wijzigbarePersonenRS.next()) {
                Persoon p = new Persoon();
                p.setId(wijzigbarePersonenRS.getInt("ID"));
                p.setNaam(wijzigbarePersonenRS.getString("NAAM"));
                p.setGeboortedatum(wijzigbarePersonenRS.getDate("GEBOORTEDATUM"));
                p.setGeslacht(wijzigbarePersonenRS.getString("GESLACHT").charAt(0));
                personen.add(p);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PersoonDAOJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return personen;
    }

    
}
