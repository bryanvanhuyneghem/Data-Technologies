/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package straten.xml;

import java.util.Comparator;
import straten.bo.Straat;

/**
 *
 * @author tiwi
 */
public class StraatComparator implements Comparator<Straat> {

    @Override
    public int compare(Straat t, Straat t1) {
        return t.getNaam().compareTo(t1.getNaam());
    }

    
}
