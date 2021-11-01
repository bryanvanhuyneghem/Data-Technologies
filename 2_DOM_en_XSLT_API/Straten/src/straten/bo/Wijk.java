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
public class Wijk {

    private int id;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

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

    private Set<Straat> straten;

    /**
     * Get the value of straten
     *
     * @return the value of straten
     */
    public Set<Straat> getStraten() {
        return straten;
    }

    /**
     * Set the value of straten
     *
     * @param straten new value of straten
     */
    public void setStraten(Set<Straat> straten) {
        this.straten = straten;
    }

}
