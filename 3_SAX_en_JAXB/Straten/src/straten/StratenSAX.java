/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package straten;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import straten.stadsdeel.jaxb.Stadsdelen;

/**
 *
 * @author vongenae
 */
public class StratenSAX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            StratenHandler handler = new StratenHandler();
            parser.parse(new File("stratenInGent.xml"), handler);
            Stadsdelen stadsdelen = handler.getStadsdelen();
            JAXBContext jctx = JAXBContext.newInstance("straten.stadsdeel.jaxb");
            Marshaller m = jctx.createMarshaller();
            m.marshal(stadsdelen, new File("stratenPerStadsdeel.xml"));
        } catch (ParserConfigurationException | SAXException | IOException | JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}
