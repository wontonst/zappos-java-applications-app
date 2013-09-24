/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos.savable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RoyZheng
 */
public class EmailEntry {

    String email;
    List<Integer> productIds;

    public EmailEntry(String email) {
        this.email = email;
        this.productIds = new ArrayList<Integer>();
    }

    public void addProductId(int i) {
        this.productIds.add(i);
    }
}
