/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.iii.film;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tiwi
 */
public interface PersoonDAO {

    void voegPersoonToe(String naam, Date geboortedatum, char geslacht);
    
    void wijzigPersoon(Persoon persoon);

    Collection<Persoon> getPersonen();
    
    void bewaarAlles();
}
