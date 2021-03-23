package com;

import com.sun.source.tree.Tree;
import org.w3c.dom.Node;

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
    int totalWordsRead = 0, uniqueWordsRead = 0;


    public static void main(String[] args) throws IOException {
        Tekstanalyse tekstanalyse = new Tekstanalyse();
        tekstanalyse.insertFromFile();

        //Write data
        tekstanalyse.inorder();
        tekstanalyse.wordData();
    }

    public Tekstanalyse() throws IOException  {
    }

    /** INNER CLASS FOR BINARY TREE NODES WITH STRING DATA */
    public static class TreeNode{
        String data;
        int timesOccurred;
        char[] dataChar;
        TreeNode left, right;

        public TreeNode(String value){
            this.timesOccurred = 1;
            data = value;
            dataChar = data.toCharArray();
            left = right = null;
        }

        public void addTimesOccurred(){
            this.timesOccurred = this.timesOccurred+1;
        }
        void write(){
            System.out.println(data + " : " + timesOccurred);
        }
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

    /** READS FILE INPUT AND RUNS insert() METHOD */
    public void insertFromFile() throws FileNotFoundException {

        /** READS INPUT FROM USER */
        System.out.println("Enter name of text-file: \n(Existing files in solution are:\n- lorem20.txt\n- lorem300.txt\n- lorem600.txt\n- lorem3000.txt ");
        //Scanner sc = new Scanner(System.in); File file = new File(sc.next());
        /** READS LOREM FILE EXISTING IN SOLUTION PROJECT */
        File file = new File("lorem20.txt");
        Scanner in = new Scanner(file);

        String str;
        // Runs through words, removes excess characters and makes all uppercase. Then runs insert()
        while (in.hasNext()){
            String word = in.next();
            str = word.toUpperCase();
            str = str.replaceAll("[^a-zA-Z0-9]", "");
            if (str==""){ }
            else {
                insert(str);
            }
        }
    }
    /** INSERT NEW WORD INTO BINARY SEARCH TREE */
    public void insert(String value){
        totalWordsRead++; //counter for total words
        TreeNode newNode = new TreeNode(value);
        char[] valueChar = value.toCharArray();

        if (isEmpty()){
            root = newNode;
            uniqueWordsRead++;
            return;
        }

        TreeNode current = root;
        boolean finished = false;
        while (!finished){
            if (value.equals(current.data)) {
                current.addTimesOccurred();
                finished = true;
            }
            else {
                    if (valueChar[0] < current.dataChar[0]) {
                        if (current.left == null) {
                            current.left = newNode;
                            uniqueWordsRead++;
                            finished = true;
                        } else current = current.left;
                    }

                    else {
                        if (current.right == null){
                            current.right = newNode;
                            uniqueWordsRead++;
                            finished = true;
                        }
                        else current = current.right;
                    }
            }
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

    public void wordData(){
        System.out.println("Total words read: " + totalWordsRead + "\nUnique words read: "+ uniqueWordsRead);
    }

}

