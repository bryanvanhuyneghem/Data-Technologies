/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package straten.bo;

import java.util.Set;

/**
 *
 * @author tiwi
 */
public class Stadsdeel {
    
    private String naam;

    /**
     * Get the value of naam
     *
     * @return the value of naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Set the value of naam
     *
     * @param naam new value of naam
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    private Set<Wijk> wijken;

    /**
     * Get the value of wijken
     *
     * @return the value of wijken
     */
    public Set<Wijk> getWijken() {
        return wijken;
    }

    /**
     * Set the value of wijken
     *
     * @param wijken new value of wijken
     */
    public void setWijken(Set<Wijk> wijken) {
        this.wijken = wijken;
    }

}
