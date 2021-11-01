/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package straten.xml;

import java.util.Comparator;
import straten.bo.Wijk;

/**
 *
 * @author tiwi
 */
public class WijkComparator implements Comparator<Wijk> {

    @Override
    public int compare(Wijk t, Wijk t1) {
        return t.getNaam().compareTo(t1.getNaam());
    }

}
