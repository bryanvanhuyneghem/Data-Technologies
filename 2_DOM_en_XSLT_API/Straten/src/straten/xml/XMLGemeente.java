/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package straten.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
public class XMLGemeente implements Gemeente {

    // hulpmap om stadsdelen op naam te zoeken
    private final Map<String, Stadsdeel> mapStadsdelen;
    // hulpmap om wijken op nr te zoeken
    private final Map<Integer, Wijk> mapWijken;
    // hulpmap om straten op naam te zoeken
    private final Map<String, Straat> mapStraten;

    public XMLGemeente(String bestand) {
        mapStraten = new HashMap<>();
        mapWijken = new HashMap<>();
        mapStadsdelen = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(bestand));
            NodeList straatKnopen = document.getDocumentElement().getElementsByTagName("element");
            for (int i = 0; i < straatKnopen.getLength(); i++) {
                Node straatKnoop = straatKnopen.item(i);
                NodeList kinderen = straatKnoop.getChildNodes();

                // kinderen opvragen               
                String postcodeString = null;
                String straatCode = null;
                String straatNaam = null;
                String naamStadsdeel = null;
                String onpaarVan = null;
                String wijkNaam = null;
                String wijkNr = null;
                String sectorNaam = null;
                String onpaarTot = null;
                String paarVan = null;
                String paarTot = null;

                for (int j = 0; j < kinderen.getLength(); j++) {

                    switch (kinderen.item(j).getNodeName()) {
                        case "postcode":
                            postcodeString = kinderen.item(j).getFirstChild().getNodeValue();
                            break;
                        case "straatcode":
                            straatCode = kinderen.item(j).getFirstChild().getNodeValue();
                            break;
                        case "straatnaam":
                            straatNaam = kinderen.item(j).getFirstChild().getNodeValue();
                            break;
                        case "onpaar_van":
                            if (kinderen.item(j).getFirstChild() == null) {
                                onpaarVan = "";
                            } else {
                                onpaarVan = kinderen.item(j).getFirstChild().getNodeValue();
                            }
                            break;
                        case "onpaar_tot":
                            if (kinderen.item(j).getFirstChild() == null) {
                                onpaarTot = "";
                            } else {
                                onpaarTot = kinderen.item(j).getFirstChild().getNodeValue();
                            }

                            break;
                        case "paar_van":
                            if (kinderen.item(j).getFirstChild() == null) {
                                paarVan = "";
                            } else {
                                paarVan = kinderen.item(j).getFirstChild().getNodeValue();
                            }

                            break;
                        case "paar_tot":
                            if (kinderen.item(j).getFirstChild() == null) {
                                paarTot = "";
                            } else {
                                paarTot = kinderen.item(j).getFirstChild().getNodeValue();
                            }

                            break;
                        case "sector":
                            sectorNaam = kinderen.item(j).getFirstChild().getNodeValue();
                            break;
                        case "stadsdeel":
                            if (kinderen.item(j).getFirstChild() == null) {
                                naamStadsdeel = "";
                            } else {
                                naamStadsdeel = kinderen.item(j).getFirstChild().getNodeValue();
                            }

                            break;

                        case "wijkNr":
                            wijkNr = kinderen.item(j).getFirstChild().getNodeValue();
                            break;

                        case "wijknaam":
                            wijkNaam = kinderen.item(j).getFirstChild().getNodeValue();
                            break;

                    }
                }

                // info bewaren 
                Stadsdeel stadsdeel = bepaalStadsdeel(naamStadsdeel);
                // wijk maken
                Wijk wijk = bepaalWijk(wijkNr, wijkNaam);
                stadsdeel.getWijken().add(wijk);
                // straat maken
                Straat straat = bepaalStraat(straatNaam, postcodeString, straatCode);
                wijk.getStraten().add(straat);
                // sector maken
                Sector sector = new Sector();
                sector.setNaam(sectorNaam);
                sector.setPaar(maakStraatDeel(paarVan, paarTot));
                sector.setOnpaar(maakStraatDeel(onpaarVan, onpaarTot));
                straat.getSectoren().add(sector);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLGemeente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Geeft alle stadsdelen terug. De stadsdelen bevatten informatie over
     * wijken, straten, sectoren en straatdelen.
     *
     * Deze methode wordt gebruikt door de GUI.
     *
     * @return een verzameling van stadsdelen
     */
    @Override
    public Set<Stadsdeel> getStadsdelen() {
        Set<Stadsdeel> stadsdelen = new HashSet<>();
        //Set<Stadsdeel> stadsdelen = new TreeSet<>(new StadsdeelComparator());
        stadsdelen.addAll(mapStadsdelen.values());
        return stadsdelen;
    }

    /**
     * Zoekt een stadsdeel in de map of maakt een nieuw aan. Een hulpmethode die
     * in de hashmap mapStadsdelen een stadsdeel met de opgegeven naam zoekt,
     * als er geen stadsdeel gevonden wordt, dan wordt er een nieuw aangemaakt
     * en toegevoegd aan de hashmap.
     *
     * @param naamStadsdeel de naam van het te zoeken stadsdeel
     * @return het gevonden of nieuw aangemaakte stadsdeel
     */
    private Stadsdeel bepaalStadsdeel(String naamStadsdeel) {
        Stadsdeel stadsdeel;
        if (mapStadsdelen.containsKey(naamStadsdeel)) {
            stadsdeel = mapStadsdelen.get(naamStadsdeel);
        } else {
            stadsdeel = new Stadsdeel();
            stadsdeel.setNaam(naamStadsdeel);
            stadsdeel.setWijken(new HashSet<>());
            //stadsdeel.setWijken(new TreeSet<>(new WijkComparator()));
            mapStadsdelen.put(naamStadsdeel, stadsdeel);
        }
        return stadsdeel;
    }

    /**
     * Zoekt een wijk in de map of maakt een nieuwe aan. Een hulpmethode die in
     * de hashmap mapWijken een wijk met het opgegeven nummer zoekt, als er geen
     * wijk gevonden wordt, dan wordt er een nieuwe aangemaakt en toegevoegd aan
     * de hashmap.
     *
     * @param wijkNr de nummer van de te zoeken wijk
     * @param naam de naam van de te zoeken wijk
     * @return de gevonden of nieuw aangemaakte wijk
     */
    private Wijk bepaalWijk(String wijkNr, String naam) {
        Wijk wijk;
        Integer nr = new Integer(wijkNr);
        if (mapWijken.containsKey(nr)) {
            wijk = mapWijken.get(nr);
        } else {
            wijk = new Wijk();
            wijk.setNaam(naam);
            wijk.setId(nr);
            wijk.setStraten(new HashSet<>());
            //wijk.setStraten(new TreeSet<>(new StraatComparator()));
            mapWijken.put(nr, wijk);
        }
        return wijk;
    }

    /**
     * Zoekt een straat in de map of maakt een nieuwe aan. Een hulpmethode die
     * in de hashmap mapStraten een straat met de opgegeven naam zoekt, als er
     * geen straat gevonden wordt, dan wordt er een nieuwe aangemaakt en
     * toegevoegd aan de hashmap.
     *
     * @param straatNaam de naam van de te zoeken straat
     * @param postcode de postcode van de deelgemeente waarin de straat ligt
     * @param straatCode de code van de straat
     * @return de gevonden of nieuw aangemaakte straat
     */
    private Straat bepaalStraat(String straatNaam, String postcode, String straatCode) {
        Straat straat;
        if (mapStraten.containsKey(straatNaam)) {
            straat = mapStraten.get(straatNaam);
        } else {
            straat = new Straat();
            straat.setNaam(straatNaam);
            straat.setCode(straatCode);
            straat.setPostcode(Integer.parseInt(postcode));
            straat.setSectoren(new HashSet<>());
            mapStraten.put(straatNaam, straat);
        }
        return straat;
    }

    /**
     * Maakt een nieuw straatdeel aan. Een hulpmethode die een nieuw straatdeel
     * aan.
     *
     * @param van het nummer of de nummercode waarmee het straatdeel begint
     * @param tot het nummer of de nummercode waarmee het straatdeel eindigt
     * @return het aangemaakte straatdeel
     */
    private StraatDeel maakStraatDeel(String van, String tot) {
        StraatDeel straatdeel = new StraatDeel();
        straatdeel.setVan(van);
        straatdeel.setTot(tot);
        return straatdeel;
    }

}
