/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.iii.film.data;

import be.iii.film.Persoon;
import be.iii.film.PersoonDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author tiwi
 */
public class PersoonDAODummy implements PersoonDAO, Serializable {

    private int idGenerator;
    private final Map<Integer, Persoon> personen;
    private final Map<Integer, Persoon> personenTemp;

    public PersoonDAODummy() {
        idGenerator = 0;
        personen = new HashMap<>();
        personenTemp = new HashMap<>();
    }

    @Override
    public void voegPersoonToe(String naam, Date geboortedatum, char geslacht) {
        idGenerator++;
        Persoon p = new Persoon();
        p.setId(idGenerator);
        p.setNaam(naam);
        p.setGeboortedatum(geboortedatum);
        p.setGeslacht(geslacht);
        personenTemp.put(idGenerator, p);
    }

    @Override
    public Collection<Persoon> getPersonen() {
        return personen.values();
    }

    @Override
    public void bewaarAlles() {
        personen.putAll(personenTemp);
        personenTemp.clear();
    }

    @Override
    public void wijzigPersoon(Persoon persoon) {
        Persoon p = personen.get(persoon.getId());
        p.setNaam(persoon.getNaam());
        p.setGeboortedatum(persoon.getGeboortedatum());

    }

}
