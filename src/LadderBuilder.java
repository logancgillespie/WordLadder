/**
 * Created by Matt (Robert) Anger and Logan Gillespie.
 *
 * @version May 2014
 *
 * This algorithm uses a user provided dictionary <code> .txt</code> file to
 * determine the best way to get from word <code>a</code> to word
 * <code>b</code>. The algorithm accomplishes this without any repeated words.
 *
 * **Note** In certain circumstances there isn't a method to reach one word
 * from another because there isn't a sequence of words which is one letter
 * difference to the "start" word. In this case the sequence returns an error
 * to the user.
 */
 
import java.util.Collection;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class LadderBuilder {
    /**
     * The dictionary of words from which to build the word ladder.
     */
    private Collection<String> dictionary;

    /**
     * The words that have already been used in the word ladder.
     */
    private Collection<String> usedWords;

    /**
     * Initializes the two main collections dictionary and usedWords. Dictionary
     * is used to store the user input dictionary and the usedWords
     * collection is to store words used by the algorithm to prevent repeats.
     *
     * @param dictionary the collection of words representing the dictionary
     *                   from which word ladders will be created.
     */
    public LadderBuilder(Collection<String> dictionary) {
        this.dictionary = dictionary;
        this.usedWords = new HashSet<String>();
    }

    /**
     * Attempts to build a word ladder between start and end.
     * The method starts by finding all words in the dictionary that are one
     * character away from the ending word. For each word that
     * is one letter different, a stack is created and the starting word and
     * the word that is 1 letter different onto that stack. Each stack is
     * added to the <code> ladderQueue</code>
     * <p/>
     * Next the first item is removed from the queue and compares the top
     * word of the stack to the starting word, if they are the same,
     * then the stack contains a ladder from the end to start..
     * If the words are not the same, then find all the words that are 1
     * letter away from the top word. For each of these new words a
     * new copy of current is created in the stack and the new word is pushed
     * on it. Then all the newly created stacks are put into queue
     * The algorithm will stop this process when the queue is empty or when
     * you have found the starting word.
     * <p/>
     *
     * @param start the beginning word of the ladder
     * @param end   the ending word of the ladder
     * @return a stack representing the word ladder between start and end,
     * or null if no word ladder between start and end exists.
     */
    public Deque<String> buildLadder(String start, String end) {
        //Create an initial stack
        Stack<String> base = new Stack<String>();

        //Stacks are held in the queue ladderQueue to be processed
        Queue<Stack<String>> ladderQueue = new ArrayDeque<Stack<String>>();

        //holds words one away from the current word
        List<String> oneAway;

        boolean finished = false;
        if (start.length() == end.length()) {
            String curWord = start;
            base.push(start);
            usedWords.add(curWord);
            ladderQueue.add(base);

            while (!ladderQueue.isEmpty() && !finished) {
                //get a stack
                Stack<String> curStack = ladderQueue.remove();
                //get the top word in the current stack
                curWord = curStack.peek();

                //get the finished word ladder
                if (curWord.equals(end)) {
                    int t = 0;
                    for (String s : curStack) {
                        t++;
                        System.out.println(t + " " + s);
                    }
                    finished = true;

                } else {
                    //TODO: this needs to be switched to the "right"
                    oneAway = this.getWordsOneAway(curWord);

                    for (String w : oneAway) {
                        if (!usedWords.contains(w)) {
                            Stack<String> tStack = (Stack<String>) curStack.
                                    clone();
                            tStack.push(w);
                            ladderQueue.add(tStack);
                            usedWords.add(w);
                        }
                    }
                }
            }
        } else {
            throw new IllegalStateException("The start and end word aren't " +
                    "the same length");
        }
        if (ladderQueue.isEmpty()) {
            System.out.println("There is no word ladder between " + start + "" +
                    "and " + end);
        }
        return null;
    }

    /**
     * Gets a list of words that are one character aways from the specified
     * word.
     *
     * @param word the target word
     * @return a list of words that are different from word by only one
     * character.
     */
    public List<String> getWordsOneAway(String word) {
        List<String> results = new ArrayList<String>();
        HashSet<String> usedWords = new HashSet<String>();

        for (int i = 0; i < word.length(); i++) {
            char[] curWordArr = word.toCharArray();
            for (char j = 97; j <= 122; j++) {
                curWordArr[i] = j;
                String nWord = new String(curWordArr);

                if (dictionary.contains(nWord.toLowerCase()) && !usedWords
                        .contains(nWord.toLowerCase())) {
                    results.add(nWord.toLowerCase());
                    usedWords.add(nWord.toLowerCase());
                }
            }
        }
        return results;
    }
}
