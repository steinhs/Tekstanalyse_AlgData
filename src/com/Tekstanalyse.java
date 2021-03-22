package com;



import java.io.*;
import java.util.*;
/**
 * Stein Hanssen Sæstad -
 * Frist Fredag 26.Mars 23:59
 * https://hiof.instructure.com/courses/4861/assignments/17947

 * Forskjellige forfattere bruker ofte de samme ordene, men med varierende hyppighet.
 * Denne oppgaven går ut på å lese en tekst og registrere alle ordene som forekommer i teksten
 * i et binært søketre.

 * Skriv et program som leser tekst fra en fil (eller fra standard input) og deler teksten opp i
 * forskjellige ord (Lenker til en ekstern side.). Et ord regnes å bestå bare av bokstaver, og et
 * ord er slutt når det kommer et tegn som ikke er en bokstav. Gjør om alle bokstavene i hvert ord
 * til en stor bokstav, slik at det ikke blir forskjell på ord som kommer i begynnelsen av en
 * setning og ord som kommer inne i en setning.

 * Hvert ord som leses fra teksten skal legges inn i et binært søketre som initiellt er tomt.
 * Hvis ordet allerede finnes fra før, skal det ikke settes inn på nytt, men en teller i noden
 * som registrerer antall forekomster av ordet skal oppdateres.

 * Etter at hele teksten er lest skal alle ordene som forekom skrives ut i alfabetisk rekkefølge,
 * sammen med antallet ganger ordet forekom. Hvis treet er riktig bygd opp er det lett å skrive ut
 * ordene i alfabetisk rekkefølge ved å traversere treet på en bestemt måte.

 * Skriv hele programmet fra bunnen av, inkludert koden for innsetting av noder i søktreet.
 */
/**
 * Lese tekst fra fil (txt)
 * Dele teksten opp i forskjellige ord, et ord regnes å bestå bare av bokstaver, og et
 * ord er slutt når det kommer et tegn som ikke er en bokstav (komma, space, punktum).
 * Gjør om alle bokstaver i hvert ord til en stor bokstav, slik at det ikke blir forskjell på ord
 * med store og små bokstaver.
 *
 * Hvert ord som leses skal legges til i binært søketre som initiellt er tomt.
 * Finnes ordet fra før av skal det ikke legges til på nytt, men legges til teller i noden som registrerer
 * antall forekomster.
 *
 * Når alle ord er lest opp, skal alle ord skrives ut i alfabetisk rekkefølge og med antall ordet forekom.
 * Feks: ALBERT(1), BETOVEN(4), HEI(2), STEIN(2), . . . .
 *
 * Utskriving skal være lett å skrive ut i rekkefølgen ved å traversere treet på en bestemt måte.
 */

public class Tekstanalyse {
    public static void main(String[] args) throws IOException {
        Tekstanalyse tekstanalyse = new Tekstanalyse();
        tekstanalyse.insertFromFile();
    }

    public Tekstanalyse() throws IOException  {
    }

    /** INNER CLASS FOR BINARY TREE NODES WITH STRING DATA */
    public class TreeNode{
        String data;
        int timesOccurred;
        char[] dataChar;
        TreeNode left, right;

        public TreeNode(String value, int timesOccurred){
            this.timesOccurred = timesOccurred;
            data = value;
            dataChar = data.toCharArray();
            left = right = null;
        }

        void write(){
            System.out.println(data + " : " + timesOccurred);
        }
    }

    /** ROOT OF ENTIRE SEARCH TREE */
    public TreeNode root;

    /** CONSTRUCTOR, CREATES EMPTY TREE */
    public void binarySearchTree(){
        root = null;
    }

    /** CHECKS FOR EMPTY TREE */
    public boolean isEmpty(){
        return (root == null);
    }

    /** ITERATIVE SEARCH IN BINARY TREE WITH STRINGS FIRST CHARACTER
     * Edited code from JH-AlgDat binarySearchTree.java
     * Will search only based off first char of word */
    public boolean search(String word){
        TreeNode current = root;

        char[] wordChar = word.toCharArray();

        while (current != null){
            if (current.data == word)
                return true;
            if (wordChar[0] < current.dataChar[0])
                current = current.left;
            else
                current = current.right;
        }
        return false;
    }

    /** INSERT NEW WORD INTO BINARY SEARCH TREE */
    // INSERT WITHOUT SIGNATURE WILL USE FILEREADER THEN INSERT EACH ELEMENT FROM THE TREEMAP
    public void insertFromFile() throws FileNotFoundException {
        TreeMap<String, Integer> wordTreeMap = fileReader();
        for (Map.Entry<String, Integer> entry : wordTreeMap.entrySet()) {
            String treeKey = entry.getKey();
            Integer treeValue = entry.getValue();
            insert(treeKey, treeValue);
        }

    }
    public void insert(String value, int timesOccurred){
        TreeNode newNode = new TreeNode(value, timesOccurred);
        char[] wordChar = value.toCharArray();

        //CREATE NEW ROOT IF TREE IS EMPTY
        if (isEmpty()){
            root = newNode;
            return;
        }
        TreeNode current = root;
        boolean finished = false;

        // INSERT NEW NODE AS A LEAF IN THE TREE
        while (!finished){
            if (wordChar[0] < current.dataChar[0]){
                if (current.left == null){
                    current.left = newNode;
                    finished = true;
                }
                else current = current.left;
            }
            else{
                if (current.right == null){
                    current.right = newNode;
                    finished = true;
                }
                else current = current.right;
            }
        }
    }

    /** PRINT TREE */
    public void inorder(){
        System.out.println("Inorder: ");
        inorder(root);
        System.out.println("\n");
    }
    public void inorder(TreeNode t){
        if (t != null){
            inorder(t.left);
            t.write();
            inorder(t.right);
        }
    }


    /** READS FROM TEXT FILE AND CONVERTS TO UPPERCASE AND REMOVES EXCESS PUNCTUATION, COMMA, ETC. . . */
    public TreeMap<String, Integer> fileReader() throws FileNotFoundException {
        TreeMap<String, Integer> words = new TreeMap<>();

        /** READS INPUT FROM USER */
        //System.out.println("Enter name of text-file: "); Scanner sc = new Scanner(System.in); File file = new File(sc.next());

        /** READS LOREM FILE EXISTING IN SOLUTION PROJECT */
        File file = new File("lorem15.txt");

        Scanner in = new Scanner(file);
        int uniqueWordsCount = 0,totalWordsCount = 0, value = 0;
        String str;

        /** Adds words to arraylist of words and counts */
        while (in.hasNext()){
            String word = in.next();
            str = word.toUpperCase();
            str = str.replaceAll("[^a-zA-Z0-9]", "");
            //Skip non-words
            if (str.equals(""));
            //If word not in tree, add new and set value 1
            else if (!words.containsKey(str)) {
                words.put(str, 1);
                uniqueWordsCount++;
                totalWordsCount++;
            }
            // If already in tree, update existing with +1 value/times appeared
            else if (words.containsKey(str)){
                value = words.get(str);
                value = value+1;
                words.put(str, value);
                totalWordsCount++;
            }
        }

        /** PRINT STATS */
        System.out.println("Total words counted: " + totalWordsCount+"\nUnique words counted: " + uniqueWordsCount);

        return words;
    }

}

