/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package straten;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import straten.stadsdeel.jaxb.Stadsdeel;
import straten.stadsdeel.jaxb.Stadsdelen;
import straten.stadsdeel.jaxb.Straat;
import straten.stadsdeel.jaxb.Wijk;

/**
 *
 * @author vongenae
 */
public class StratenHandler extends DefaultHandler {

    private final BouwStadsdelen helper;
    private String laatsteElement;

    private String postcode;
    private String straatCode;
    private String straatNaam;
    private String onpaarVan;
    private String onpaarTot;
    private String paarVan;
    private String paarTot;
    private String sector;
    private String naamStadsdeel;
    private String wijkNr;
    private String wijkNaam;

    public StratenHandler() {
        helper = new BouwStadsdelen();
    }

    @Override

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        laatsteElement = qName;
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (laatsteElement.equalsIgnoreCase("postcode")) {
            postcode = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("straatcode")) {
            straatCode = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("straatnaam")) {
            straatNaam = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("onpaar_van")) {
            onpaarVan = new String(ch, start, length);
            onpaarVan = onpaarVan.trim();
        } else if (laatsteElement.equalsIgnoreCase("onpaar_tot")) {
            onpaarTot = new String(ch, start, length);
            onpaarTot = onpaarTot.trim();
        } else if (laatsteElement.equalsIgnoreCase("paar_van")) {
            paarVan = new String(ch, start, length);
            paarVan = paarVan.trim();
        } else if (laatsteElement.equalsIgnoreCase("paar_tot")) {
            paarTot = new String(ch, start, length);
            paarTot = paarTot.trim();
        } else if (laatsteElement.equalsIgnoreCase("sector")) {
            sector = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("stadsdeel")) {
            naamStadsdeel = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("wijkNr")) {
            wijkNr = new String(ch, start, length);
        } else if (laatsteElement.equalsIgnoreCase("wijknaam")) {
            wijkNaam = new String(ch, start, length);
        }
        laatsteElement = "";
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {

        if (qName.equalsIgnoreCase("element")) {
            Stadsdeel stadsdeel = helper.bepaalStadsdeel(naamStadsdeel);
            Wijk wijk = helper.bepaalWijk(stadsdeel, wijkNr, wijkNaam);
            Straat straat = helper.bepaalStraat(wijkNr, straatCode, straatNaam, onpaarVan,
                    onpaarTot, paarVan, paarTot, sector, postcode);
            wijk.getStraat().add(straat);

        }
    }

    public Stadsdelen getStadsdelen() {
        return helper.getStadsdelen();
    }
}
