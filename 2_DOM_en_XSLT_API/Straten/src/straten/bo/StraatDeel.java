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
public class StraatDeel {

    private String van;

    /**
     * Get the value of van
     *
     * @return the value of van
     */
    public String getVan() {
        return van;
    }

    /**
     * Set the value of van
     *
     * @param van new value of van
     */
    public void setVan(String van) {
        this.van = van;
    }

    private String tot;

    /**
     * Get the value of tot
     *
     * @return the value of tot
     */
    public String getTot() {
        return tot;
    }

    /**
     * Set the value of tot
     *
     * @param tot new value of tot
     */
    public void setTot(String tot) {
        this.tot = tot;
    }

    @Override
    public String toString() {
        return "van: " + van + " tot: " + tot;
    }

}
