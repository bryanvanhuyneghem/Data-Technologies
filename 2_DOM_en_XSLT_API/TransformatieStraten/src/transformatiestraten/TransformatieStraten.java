/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformatiestraten;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import straten.xml.XMLGemeente;

/**
 *
 * @author vongenae
 */
public class TransformatieStraten {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            XMLGemeente gemeente = new XMLGemeente("stratenInGent.xml");
            Document stratenPerPostcode = new BouwPostcodes(gemeente).getPostcodes();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource bron = new DOMSource(stratenPerPostcode);
            StreamResult doel = new StreamResult(new File("stratenPerPostcode.xml"));
            transformer.transform(bron, doel);
        } catch (TransformerException ex) {
            Logger.getLogger(TransformatieStraten.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
