/**
 * Created by Matt (Robert) Anger and Logan Gillespie.
 *
 * @version May 2014
 *
 */

import java.io.FileNotFoundException;
import java.util.Collection;

public class Driver {

    private static final String USAGE_MSG = "usage: java Driver <filename> " +
            "<start> <end>\n";
    /**
     * Helper method to print out the usage message
     */
    private static void usage() {
        System.err.println(USAGE_MSG);
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            usage();
        }

        //Check to make sure the word lengths are the same
        checkWordLength((String) args[1], (String) args[2]);

        DictionaryBuilder dirbuild = null;
        try {
            dirbuild = new DictionaryBuilder(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File location not found");
            System.exit(1);
        }

        Collection<String> words = dirbuild.getWordsOfLength(args[2].length());

        LadderBuilder ladbuild = new LadderBuilder(words);
        ladbuild.buildLadder(args[1], args[2]);
    }

    /**
     * Helper method to check the length of two words
     *
     * @param w1 starting word to check
     * @param w2 ending word to check
     */
    private static void checkWordLength(String w1, String w2) {
        if (w2.length() > w1.length()) {
            System.out.println("Error: " + w1 + " and " + w2 +
                    " must be the same length!");
            System.exit(1);
        }
    }
}

