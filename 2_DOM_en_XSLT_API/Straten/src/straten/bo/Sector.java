/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package straten.bo;

/**
 *
 * @author tiwi
 */
public class Sector {

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

    private StraatDeel paar;

    /**
     * Get the value of paar
     *
     * @return the value of paar
     */
    public StraatDeel getPaar() {
        return paar;
    }

    /**
     * Set the value of paar
     *
     * @param paar new value of paar
     */
    public void setPaar(StraatDeel paar) {
        this.paar = paar;
    }

    private StraatDeel onpaar;

    /**
     * Get the value of onpaar
     *
     * @return the value of onpaar
     */
    public StraatDeel getOnpaar() {
        return onpaar;
    }

    /**
     * Set the value of onpaar
     *
     * @param onpaar new value of onpaar
     */
    public void setOnpaar(StraatDeel onpaar) {
        this.onpaar = onpaar;
    }

}
