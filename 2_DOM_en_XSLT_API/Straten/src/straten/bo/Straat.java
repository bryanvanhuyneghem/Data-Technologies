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
public class Straat {

    private int postcode;

    /**
     * Get the value of postcode
     *
     * @return the value of postcode
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * Set the value of postcode
     *
     * @param postcode new value of postcode
     */
    public void setPostcode(int postcode) {
        this.postcode = postcode;
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

    private String code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    private Set<Sector> sectoren;

    /**
     * Get the value of sectoren
     *
     * @return the value of sectoren
     */
    public Set<Sector> getSectoren() {
        return sectoren;
    }

    /**
     * Set the value of sectoren
     *
     * @param sectoren new value of sectoren
     */
    public void setSectoren(Set<Sector> sectoren) {
        this.sectoren = sectoren;
    }

}
