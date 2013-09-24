/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos.savable;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RoyZheng
 */
public class Entries {

    public static final String PATH = "./entries.dat";
    // List<EmailEntry> emails;
    Map<String, List<Integer>> entries = new HashMap<String, List<Integer>>();

    public Entries() {
    }

    public Map<String, List<Integer>> getData() {
        return this.entries;
    }

    public void add(String email, int id) {
        if (!entries.containsKey(email)) {
            entries.put(email, new ArrayList<Integer>());
        }
        if (entries.get(email).contains(id)) {
            return;
        }
        entries.get(email).add(id);
    }

    public static Entries load() {
        Path file = Paths.get(PATH);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            StringBuilder b = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                b.append(line);
            }
            return (new Gson()).fromJson(b.toString(), Entries.class);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return new Entries();
    }

    public void save() {
        Path p = Paths.get(PATH);
        Charset charset = Charset.forName("US-ASCII");
        String s = (new Gson()).toJson(this);
        try (BufferedWriter writer = Files.newBufferedWriter(p, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
