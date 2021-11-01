/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package straten;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import straten.stadsdeel.jaxb.*;

/**
 *
 * @author vongenae
 */
public class BouwStadsdelen {
     // hulpmap om stadsdelen op naam te zoeken
    private final Map<String, Stadsdeel> mapStadsdelen;
    // hulpmap om wijken per stadsdeel op nr te zoeken
    private final Map<String, Map<BigInteger, Wijk>> wijkenPerStadsdeel;
    private final ObjectFactory factory;
    private final Stadsdelen stadsdelen;

    protected BouwStadsdelen() {
        mapStadsdelen = new HashMap<>();
        wijkenPerStadsdeel = new HashMap<>();
        factory = new ObjectFactory();
        stadsdelen = factory.createStadsdelen();
    }
    
    protected Stadsdeel bepaalStadsdeel(String naam) {
        Stadsdeel stadsdeel;
        if (mapStadsdelen.containsKey(naam)) {
            stadsdeel = mapStadsdelen.get(naam);
        } else {
            stadsdeel = factory.createStadsdeel();
            stadsdeel.setNaam(naam);
            mapStadsdelen.put(naam, stadsdeel);
            wijkenPerStadsdeel.put(naam, new HashMap<BigInteger, Wijk>());
            stadsdelen.getStadsdeel().add(stadsdeel);
        }
        return stadsdeel;
    }

     protected Wijk bepaalWijk(Stadsdeel stadsdeel, String wijkNr, String naam) {
        Wijk wijk;
        BigInteger nr = new BigInteger(wijkNr);
        Map<BigInteger, Wijk> wijken = wijkenPerStadsdeel.get(stadsdeel.getNaam());
        if (wijken.containsKey(nr)) {
            wijk = wijken.get(nr);
        } else {
            wijk = factory.createWijk();
            wijk.setNaam(naam);
            wijk.setNr(nr);
            wijken.put(nr, wijk);
            stadsdeel.getWijk().add(wijk);
        }
        return wijk;
    }

     protected Straat bepaalStraat(String wijkNr, String straatCode, String straatNaam,
            String onpaarVan, String onpaarTot, String paarVan, String paarTot,
            String sector, String postcodeString) {
        Straat straat = factory.createStraat();
        straat.setCode(new BigInteger(straatCode));
        straat.setNaam(straatNaam);
        straat.setSector(sector);
        straat.setPostcode(new BigInteger(postcodeString));
        if (!onpaarVan.equals("") && !onpaarTot.equals("")) {
            Nummers onpaar = factory.createNummers();
            onpaar.setVan(onpaarVan);
            onpaar.setTot(onpaarTot);
            straat.setOnpaar(onpaar);
        }
        if (!paarVan.equals("") && !paarTot.equals("")) {
            Nummers paar = factory.createNummers();
            paar.setVan(paarVan);
            paar.setTot(paarTot);
            straat.setPaar(paar);
        }
        return straat;
    }
     
      protected Stadsdelen getStadsdelen() {
        return stadsdelen;
    }

}
