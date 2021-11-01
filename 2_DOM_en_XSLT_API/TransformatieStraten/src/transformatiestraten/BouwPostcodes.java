/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformatiestraten;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import straten.bo.Sector;
import straten.bo.Stadsdeel;
import straten.bo.Straat;
import straten.bo.Wijk;
import straten.xml.XMLGemeente;

/**
 *
 * @author vongenae
 */
public class BouwPostcodes {

    XMLGemeente gemeente;
    Document stratenPerPostcode;
    // hulpmap om postcode op code te zoeken
    private final Map<Integer, Element> mapPostcodes;
    // hulpmap om stadsdelen op postcode en op naam te zoeken
    private final Map<Integer, Map<String, Element>> mapStadsdelen;
    // hulpmap om wijken per postcode per stadsdeel op nr te zoeken
    private final Map<Integer, Map<String, Map<Integer, Element>>> wijkenPerStadsdeel;

    protected BouwPostcodes(XMLGemeente gemeente) {
        mapPostcodes = new HashMap<>();
        mapStadsdelen = new HashMap<>();
        wijkenPerStadsdeel = new HashMap<>();
        this.gemeente = gemeente;
    }

    protected Document getPostcodes() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            stratenPerPostcode = builder.newDocument();
            Element postcodes = stratenPerPostcode.createElement("postcodes");
            stratenPerPostcode.appendChild(postcodes);

            for (Stadsdeel stadsdeel : gemeente.getStadsdelen()) {
                for (Wijk wijk : stadsdeel.getWijken()) {
                    for (Straat straat : wijk.getStraten()) {
                        for (Sector sector : straat.getSectoren()) {
                            // info bewaren in DOM-object
                            Integer postcode = straat.getPostcode();
                            bepaalPostcode(postcode);
                            bepaalStadsdeel(postcode, stadsdeel.getNaam());
                            Element wijkElement = bepaalWijk(postcode, stadsdeel.getNaam(), String.valueOf(wijk.getId()), wijk.getNaam());
                            Element straatElement = bepaalStraat(postcode, stadsdeel.getNaam(), String.valueOf(wijk.getId()),
                                    straat.getCode(), straat.getNaam(), sector.getOnpaar().getVan(),
                                    sector.getOnpaar().getTot(), sector.getPaar().getVan(), sector.getPaar().getTot(), sector.getNaam());
                            wijkElement.appendChild(straatElement);
                        }
                    }
                }
            }

            return stratenPerPostcode;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BouwPostcodes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Element bepaalPostcode(Integer postcode) {
        Element postcodeElement;
        if (mapPostcodes.containsKey(postcode)) {
            postcodeElement = mapPostcodes.get(postcode);
        } else {
            postcodeElement = stratenPerPostcode.createElement("postcode");
            postcodeElement.setAttribute("code", postcode.toString());
            stratenPerPostcode.getDocumentElement().appendChild(postcodeElement);
            mapPostcodes.put(postcode, postcodeElement);
            mapStadsdelen.put(postcode, new HashMap<>());
            wijkenPerStadsdeel.put(postcode, new HashMap<>());
        }
        return postcodeElement;

    }

    private Element bepaalStadsdeel(Integer postcode, String naamStadsdeel) {
        Element postcodeElement, stadsdeel;
        postcodeElement = mapPostcodes.get(postcode);
        Map<String, Element> stadsdelen = mapStadsdelen.get(postcode);

        if (stadsdelen.containsKey(naamStadsdeel)) {
            stadsdeel = stadsdelen.get(naamStadsdeel);
        } else {
            stadsdeel = stratenPerPostcode.createElement("stadsdeel");
            Element naam = stratenPerPostcode.createElement("naam");
            naam.appendChild(stratenPerPostcode.createTextNode(naamStadsdeel));
            stadsdeel.appendChild(naam);
            postcodeElement.appendChild(stadsdeel);
            stadsdelen.put(naamStadsdeel, stadsdeel);
            Map<String, Map<Integer, Element>> wijken = wijkenPerStadsdeel.get(postcode);
            wijken.put(naamStadsdeel, new HashMap());
        }
        return stadsdeel;
    }

    protected Element bepaalWijk(Integer postcode, String naamStadsdeel, String wijkNr, String naam) {
        Element wijk;
        Integer nr = new Integer(wijkNr);
        Map<Integer, Element> wijken = wijkenPerStadsdeel.get(postcode).get(naamStadsdeel);
        if (wijken.containsKey(nr)) {
            wijk = wijken.get(nr);
        } else {
            wijk = stratenPerPostcode.createElement("wijk");
            Element naamKnoop = stratenPerPostcode.createElement("naam");
            naamKnoop.appendChild(stratenPerPostcode.createTextNode(naam));
            wijk.appendChild(naamKnoop);
            wijk.setAttribute("nr", wijkNr);
            wijken.put(nr, wijk);
            Element stadsdeel = mapStadsdelen.get(postcode).get(naamStadsdeel);
            stadsdeel.appendChild(wijk);
        }
        return wijk;
    }

    protected Element bepaalStraat(Integer postcode, String naamStadsdeel, String wijkNr,
            String straatCode, String straatNaam,
            String onpaarVan, String onpaarTot, String paarVan, String paarTot,
            String sector) {
        Element straat = stratenPerPostcode.createElement("straat");
        straat.setAttribute("code", straatCode);

        Element naamKnoop = stratenPerPostcode.createElement("naam");
        naamKnoop.appendChild(stratenPerPostcode.createTextNode(straatNaam));
        straat.appendChild(naamKnoop);

        if (!onpaarVan.equals("") && !onpaarTot.equals("")) {
            Element onpaar = stratenPerPostcode.createElement("onpaar");
            onpaar.setAttribute("van", onpaarVan);
            onpaar.setAttribute("tot", onpaarTot);
            straat.appendChild(onpaar);
        }
        if (!paarVan.equals("") && !paarTot.equals("")) {
            Element paar = stratenPerPostcode.createElement("paar");
            paar.setAttribute("van", paarVan);
            paar.setAttribute("tot", paarTot);
            straat.appendChild(paar);
        }

        Element sectorKnoop = stratenPerPostcode.createElement("sector");
        sectorKnoop.appendChild(stratenPerPostcode.createTextNode(sector));
        straat.appendChild(sectorKnoop);
        return straat;
    }
}
