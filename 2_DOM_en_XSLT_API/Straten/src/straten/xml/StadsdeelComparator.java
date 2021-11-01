/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package straten.xml;

import java.util.Comparator;
import straten.bo.Stadsdeel;

/**
 *
 * @author tiwi
 */
public class StadsdeelComparator implements Comparator<Stadsdeel> {

    @Override
    public int compare(Stadsdeel t, Stadsdeel t1) {
        return t.getNaam().compareTo(t1.getNaam());
    }
    
}
