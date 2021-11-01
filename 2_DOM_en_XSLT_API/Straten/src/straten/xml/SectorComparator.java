/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package straten.xml;

import java.util.Comparator;
import straten.bo.Sector;

/**
 *
 * @author tiwi
 */
public class SectorComparator implements Comparator<Sector> {

    @Override
    public int compare(Sector t, Sector t1) {
        return t.getNaam().compareTo(t1.getNaam());
    }
    
}
