package ro.endava.bestmarathon.utils;

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

}
