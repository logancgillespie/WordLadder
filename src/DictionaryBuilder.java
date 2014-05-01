/**
 * Created by Matt (Robert) Anger and Logan Gillespie.
 *
 * @version May 2014
 *
 * This class enables clients (users of the class) to read a file containing
 * a list of whitespace delimited words and return a subset of those word of
 * a given length. Each word will be converted to lower case,
 * and each word will appear only once in the dictionary.
 */

import java.io.FileNotFoundException;
import java.util.Collection;
import java.io.File;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Initializes private variables for the class
 */
public class DictionaryBuilder {

    private BufferedReader br;
    private File file;
    private Collection<String> words;

    /**
     * Constructs a new DictionaryBuilder that will read words from the
     * specified file. The constructor creates a new Scanner object based on
     * a FileInputStream object that is based the specified filename.
     *
     * @param filename The name of the file from which the words should be read.
     */
    public DictionaryBuilder(String filename) throws FileNotFoundException {
        this.file = new File(filename);

        //make sure the file is valid        
        if (!file.isFile() || file.isDirectory())
            throw new FileNotFoundException("Invalid file name: " + this.file
                    .toString());

        //initialize the word Collection
        this.words = new HashSet<String>();

    }

    /**
     * Gets a Collection of words from the file with the specified length.
     * This method creates a HashSet to hold the dictionary. It then uses the
     * fileInput field to read each word from the file. If the word's length
     * is correct, the method converts the word to lower case. If the
     * converted word is not found in the dictionary, it adds the word to the
     * dictionary. Once the method examines each word in the file,
     * the method returns the dictionary of words.
     *
     * @param length The length of the words that should be returned.
     * @return a <code>Collection</code> of words from the associated file that
     * are the specified length.
     * @postcondition Every word in the dictionary of the specified length
     * will be included in the returned collection in lower case,
     * and will appear only once.
     */
    public Collection<String> getWordsOfLength(int length) {
        String word;
        this.openBufferedReader();

        try {
            while ((word = br.readLine()) != null) {

                if (!words.contains(word.toLowerCase())) {
                    if (word.length() == length)
                        this.words.add(word.toLowerCase());
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            this.closeBufferedReader();
        }
        return this.words;
    }

    /**
     * Helper method for closing the BufferedReader object
     */
    private void closeBufferedReader() {
        try {
            if (this.br != null)
                this.br.close();
            this.br = null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Helper method for initializing the BufferedReader object
     */
    private void openBufferedReader() {
        try {
            if (this.br == null)
                this.br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException fne) {
            System.out.println("File not found!");
            fne.printStackTrace();
        }
    }
}
