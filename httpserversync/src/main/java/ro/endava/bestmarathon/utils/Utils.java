package ro.endava.bestmarathon.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by cosmin on 3/24/14.
 */
public class Utils {

    public static Set<String> split(String s) {
        Set<String> result = new HashSet<>();
        if (s == null) {
            return result;
        }
        StringTokenizer tokenizer = new StringTokenizer(s, " ,.;'\"\\/?!:"); // note: not splitting on "-"
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            if (word.length() > 2) {
                result.add(word.toLowerCase());
            }
        }
        return result;
    }

    public static Set<String> checkCollection(Collection<String> collection) {
        Set<String> result = new HashSet<>();
        for (String s : collection) {
            if (s.length() > 2) {
                result.add(s.toLowerCase());
            }
        }
        return result;
    }

}
