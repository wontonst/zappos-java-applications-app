/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import zappos.gui.Frame;
import zappos.requests.Search;
import zappos.requests.SearchEntry;
import zappos.savable.Entries;

/*
 * 
 */
/**
 *
 * @author RoyZheng
 */
public class Zappos {

    public static final String ZAPPOS_URL = "http://api.zappos.com/";
    public static final String API_KEY = "52ddafbe3ee659bad97fcce7c53592916a6bfd73";
    public static final String API_TOKEN = "key=" + API_KEY;
    public static final String product_url = ZAPPOS_URL + "Product";
    public static final String search_url = ZAPPOS_URL + "Search?includes=[\"productName,productId\"]&";
    Frame gui;
    Gson gson = new Gson();
    Entries entries;
    DiscountChecker dc;
    Thread dcthread;

    public Zappos() {
        this.gui = new Frame(this);
        this.entries = Entries.load();
        this.dc = new DiscountChecker(this, this.entries);
        this.dcthread = new Thread(this.dc);
        this.dcthread.start();
    }

    public void usrProductNameRequest(String email, String name) {
        final Zappos z = this;
        final String email2 = email;
        final String name2 = name;
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Search s = null;
                try {
                    s = z.getSearch(name2);
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(gui, "Could not establish connection to server.");
                    gui.showInputScreen();
                    return;
                }
                if (!z.containsProductName(s, name2)) {
                    JOptionPane.showMessageDialog(gui, "Product name " + name2 + " could not be found.");
                    gui.showInputScreen();
                    return;
                }
                for (SearchEntry se : s.getEntries()) {
                    z.entries.add(email2, se.getProductId());
                }
                z.entries.save();
                System.out.println(gson.toJson(z.entries));
                JOptionPane.showMessageDialog(gui, name2 + " successfully added to scan list.");
                gui.showInputScreen();
            }
        })).start();
    }

    public void usrProductIdRequest(String email, int id) {
        final Zappos z = this;
        final String email2 = email;
        final int id2 = id;
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Search s = null;
                try {
                    s = z.getSearch(id2 + "");
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(gui, "Could not establish connection to server.");
                    gui.showInputScreen();
                    return;
                }
                if (!z.containsProductId(s, id2)) {
                    JOptionPane.showMessageDialog(gui, "Product id " + id2 + " could not be found on Zappos.");
                    gui.showInputScreen();
                    return;
                }
                for (SearchEntry se : s.getEntries()) {
                    z.entries.add(email2, se.getProductId());
                }
                System.out.println(gson.toJson(z.entries));
                z.entries.save();
                JOptionPane.showMessageDialog(gui, id2 + " successfully added to scan list");
                gui.showInputScreen();
            }
        })).start();
    }

    public boolean containsProductId(Search s, int productid) {
        for (SearchEntry se : s.getEntries()) {
            if (se.getProductId() == productid) {
                return true;
            }
        }
        return false;
    }

    public boolean containsProductName(Search s, String pn) {
        for (SearchEntry se : s.getEntries()) {
            if (se.getProductName().toUpperCase().equals(pn.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public Search getSearch(String term) throws UnknownHostException {
        Search s = null;
        try {
            String reply = Get.sendRequest(new URL(Zappos.buildString(search_url, "term=", term, "&", API_TOKEN)));
            System.out.println("RAW JSON:" + reply);
            s = gson.fromJson(reply, Search.class);
            s.removeDuplicates();
            System.out.println(s.toString());
        } catch (UnknownHostException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(Zappos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public static String buildString(String... v) {
        StringBuilder sb = new StringBuilder();
        for (String s : v) {
            sb.append(s);
        }
        return sb.toString();
    }

    public void sendEmail(String to, String from, String subject, String body) {
        final String to2 = to, from2 = from, subject2 = subject, body2 = body;
        (new Thread(new Runnable() {
            public void run() {
                String host = "localhost";
                Properties props = new Properties();
                props.put("mail.smtp.host", host);
                props.put("mail.debug", "true");
                Session session = Session.getInstance(props);

                try {
                    Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(from2));
                    InternetAddress[] address = {new InternetAddress(to2)};
                    msg.setRecipients(Message.RecipientType.TO, address);
                    msg.setSubject(subject2);
                    msg.setSentDate(new Date());

                    msg.setText(body2);

                    Transport.send(msg);
                } catch (MessagingException mex) {
                    JOptionPane.showMessageDialog(gui, "Unable to send email! Please ensure a connection to the local smtp server can be established.");
                }
            }
        })).start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Zappos z = new Zappos();

    }
}
