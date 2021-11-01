/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package straten.dummy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import straten.bo.Gemeente;
import straten.bo.Sector;
import straten.bo.Stadsdeel;
import straten.bo.Straat;
import straten.bo.StraatDeel;
import straten.bo.Wijk;

/**
 *
 * @author tiwi
 */
public class DummyGemeente implements Gemeente {

    private Random random;
    private Set<Stadsdeel> stadsdelen;

    public DummyGemeente() {
        random = new Random();
        stadsdelen = new HashSet<>();
        Stadsdeel stadsdeel = new Stadsdeel();
        stadsdeel.setNaam("Oost");
        stadsdeel.setWijken(maakWijken(1, new String[]{"centrum", "rand"}));
        stadsdelen.add(stadsdeel);
        stadsdeel = new Stadsdeel();
        stadsdeel.setNaam("West");
        stadsdeel.setWijken(maakWijken(1, new String[]{"wijk", "parochie"}));
        stadsdelen.add(stadsdeel);

    }

    private Set<Wijk> maakWijken(int startId, String[] namen) {
        Set<Wijk> wijken = new HashSet<>();
        for (String naam : namen) {
            Wijk wijk = new Wijk();
            wijk.setId(startId++);
            wijk.setNaam(naam);
            wijken.add(wijk);
            wijk.setStraten(maakStraten(naam, random.nextInt(5) + 1));
        }
        return wijken;
    }

    private Set<Straat> maakStraten(String naam, int aantal) {
        Set<Straat> straten = new HashSet<>();
        for (int i = 0; i < aantal; i++) {
            Straat straat = new Straat();
            straat.setNaam(naam + i);
            straat.setPostcode(1000 + random.nextInt(9000));
            straten.add(straat);
            straat.setSectoren(maakSector(random.nextInt(2) + 1));
        }
        return straten;
    }

    private Set<Sector> maakSector(int aantal) {
        Set<Sector> sectoren = new HashSet<>();
        for (int i = 0; i < aantal; i++) {
            Sector sector = new Sector();
            sector.setNaam("" + ((char) 'A' + random.nextInt(26)) + random.nextInt(5));
            sector.setPaar(maakStraatDeel());
            sector.setOnpaar(maakStraatDeel());
            sectoren.add(sector);
        }
        return sectoren;
    }

    private StraatDeel maakStraatDeel() {
        StraatDeel straatdeel = new StraatDeel();
        straatdeel.setVan("" + random.nextInt(10));
        straatdeel.setTot("" + random.nextInt(200));
        return straatdeel;
    }

    @Override
    public Set<Stadsdeel> getStadsdelen() {
        return stadsdelen;
    }

}
