/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.iii.personen;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tiwi
 */
public class PersoonData implements Serializable {

    private Collection<Persoon> personen;
    private PersoonDAO dao;

    public void setDao(PersoonDAO dao) {
        this.dao = dao;
        haalPersonen();
    }

    public Collection<Persoon> getPersonen() {
        return personen;
    }

    public void voegPersoonToe(String naam, Date geboortedatum, char geslacht) {
        dao.voegPersoonToe(naam, geboortedatum, geslacht);
    }

    public void wijzigPersoon(Persoon persoon) {
        dao.wijzigPersoon(persoon);
        haalPersonen();
    }

    public void bewaarAlles() {
        dao.bewaarAlles();
        haalPersonen();
    }

    public void haalPersonen() {
        personen = dao.getPersonen();
        for (Persoon p : personen) {
            p.setData(this);
        }
    }
}
