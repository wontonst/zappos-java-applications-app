/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import zappos.requests.Search;
import zappos.savable.Entries;

/**
 *
 * @author RoyZheng
 */
public class DiscountChecker implements Runnable {

    public static final int REFRESH_RATE_MS = 3000;//60000;
    Entries entries;
    Zappos zappos;

    public DiscountChecker(Zappos z, Entries e) {
        this.zappos = z;
        this.entries = e;
    }

    public void run() {
        while (true) {
            this.scanOnce();
        }
    }

    public void scanOnce() {
        try {
            Thread.sleep(REFRESH_RATE_MS);
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscountChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, List<Integer>> m = this.entries.getData();
//TODO: this could easily be optimized in the future to minimize GETs
        for (Map.Entry<String, List<Integer>> entry : m.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();
            for (Integer i : value) {
                try {
                    Search s = this.zappos.getSearch(i + "");
                    if (s.getEntries().size() == 0) {
                        continue;
                    }
                    String poff = s.getEntries().get(0).getPercentOff();
                    if (20.0 <= Double.parseDouble(poff.substring(0, poff.length() - 1))) {
                        System.out.println(s.getEntries().get(0).getProductName() + " IS ON SALE!\n" + poff + "% off! Buy it at " + s.getEntries().get(0).getProductUrl());
                        this.zappos.sendEmail(key, "admin@royzheng.me", s.getEntries().get(0).getProductName() + " IS ON SALE!", poff + "% off! Buy it at " + s.getEntries().get(0).getProductUrl());
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(DiscountChecker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
