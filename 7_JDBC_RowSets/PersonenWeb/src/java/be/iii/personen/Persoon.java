/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.iii.personen;

import java.util.Date;
import javax.faces.event.ActionEvent;

/**
 *
 * @author tiwi
 */
public class Persoon {
    PersoonData data;
    
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

    private Date geboortedatum;

    /**
     * Get the value of geboortedatum
     *
     * @return the value of geboortedatum
     */
    public Date getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Set the value of geboortedatum
     *
     * @param geboortedatum new value of geboortedatum
     */
    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    private char geslacht;

    /**
     * Get the value of geslacht
     *
     * @return the value of geslacht
     */
    public char getGeslacht() {
        return geslacht;
    }

    /**
     * Set the value of geslacht
     *
     * @param geslacht new value of geslacht
     */
    public void setGeslacht(char geslacht) {
        this.geslacht = geslacht;
    }
    
        private boolean editeerbaar;

    /**
     * Get the value of editeerbaar
     *
     * @return the value of editeerbaar
     */
    public boolean isEditeerbaar() {
        return editeerbaar;
    }

    /**
     * Set the value of editeerbaar
     *
     * @param editeerbaar new value of editeerbaar
     */
    public void setEditeerbaar(boolean editeerbaar) {
        this.editeerbaar = editeerbaar;
    }


    public void setData(PersoonData data) {
        this.data = data;
    }
    
    public void voegToe(ActionEvent e) {
        data.voegPersoonToe(naam, geboortedatum, geslacht);
    }
    public void editeer(ActionEvent e) {
        setEditeerbaar(true);
    }
    
    public void wijzig(ActionEvent e) {
        data.wijzigPersoon(this);
        setEditeerbaar(false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persoon other = (Persoon) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    

}
