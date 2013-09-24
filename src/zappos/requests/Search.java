/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos.requests;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RoyZheng
 */
public class Search {

    int statusCode;
    List<SearchEntry> results = new ArrayList<SearchEntry>();

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Statuscode:");
        b.append(this.statusCode);
        b.append(',');
        for (SearchEntry s : this.results) {
            b.append('[');
            b.append(s.toString());
            b.append(']');
        }
        return b.toString();
    }

    public List<SearchEntry> getEntries() {
        return this.results;
    }

    public void removeDuplicates() {
        List<SearchEntry> nr = new ArrayList<SearchEntry>();
        for (SearchEntry se : this.results) {
            boolean uniq = true;
            for (SearchEntry nre : nr) {
                if (nre.getProductId() == se.getProductId()) {
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                nr.add(se);;
            }
        }
    }
}

/*
{
"statusCode": "200",
"results":
[    
 { 
   "styleId": "556677", 
   "productId": "123456", 
   "brandName": "Ugg", 
   "productName": "Classic Tall", 
   "thumbnailImageUrl": "http://www.zappos.com/images/image.jpg", 
   "originalPrice": "$198.00", 
   "price": "$198.00", 
   "percentOff": "19%", 
   "productUrl": "http://www.zappos.com/product/101183/color/381" 
 },
 { 
   "styleId": "556678", 
   "productId": "123457", 
   "brandName": "Ugg", 
   "productName": "Classic Short", 
   "thumbnailImageUrl": "http://www.zappos.com/images/image.jpg",  
   "originalPrice": "$158.00", 
   "price": "$140.00", 
   "percentOff": "19%", 
   "productUrl": "http://www.zappos.com/product/101183/color/381" 
 },
 { 
   "styleId": "556679", 
   "productId": "234567", 
   "brandName": "Frye", 
   "productName": "Engineer 12R W", 
   "thumbnailImageUrl": "http://www.zappos.com/images/image.jpg", 
   "originalPrice": "$300.00", 
   "price": "$300.00", 
   "percentOff": "19%", 
   "productUrl": "http://www.zappos.com/product/101183/color/381" 
 }
]
}
* 
*/