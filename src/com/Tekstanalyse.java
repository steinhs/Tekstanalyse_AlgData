package com;

import com.sun.source.tree.Tree;
import org.w3c.dom.Node;

import java.io.*;
import java.util.*;
/**
 * Stein Hanssen Sæstad
 *
 * Kode er basert på JH-AlgDat - binarySearchTree.java og tilpasset oppgaven.
 * Inkludert i prosjektet er lorem20, 300 og 3000 .txt filer.
 */

public class Tekstanalyse {
    //Counters for words
    int totalWordsRead = 0, uniqueWordsRead = 0;

    public static void main(String[] args) throws IOException {
        Tekstanalyse tekstanalyse = new Tekstanalyse();
        tekstanalyse.insertFromFile();

        //Write data
        tekstanalyse.inorder();
        tekstanalyse.wordData();

    }

    /** INNER CLASS FOR BINARY TREE NODES WITH STRING DATA */
    public static class TreeNode{
        String data;
        int timesOccurred;
        TreeNode left, right;

        public TreeNode(String value){
            this.timesOccurred = 1;
            data = value;
            left = right = null;
        }
        public void addTimesOccurred(){
            this.timesOccurred = this.timesOccurred+1;
        }
        void write(){
            System.out.println(data + " : " + timesOccurred);
        }
    }

    /** READS FILE INPUT AND RUNS insert() METHOD */
    public void insertFromFile() throws FileNotFoundException {

        // READS INPUT FROM USER
        System.out.println("Existing files in solution are:\n- lorem20.txt\n- lorem300.txt\n- lorem3000.txt\n\nEnter name of text-file:");
        Scanner sc = new Scanner(System.in);
        File file = new File(sc.next());

        // Pre-defined paths
        //File file = new File("lorem20.txt");
        //File file = new File("lorem300.txt");
        //File file = new File("lorem3000.txt");

        Scanner in = new Scanner(file);
        // Runs through words, removes excess characters and makes all uppercase. Then runs insert()
        while (in.hasNext()){
            String word = in.next();
            word = word.toUpperCase();
            word = word.replaceAll("[^a-zA-Z0-9]", "");
            // If empty string, skip, else insert
            if (word==""){}
            else {
                insert(word);
            }
        }
    }

    /** INSERT NEW WORD INTO BINARY SEARCH TREE */
    public void insert(String value){
        totalWordsRead++; //counter for total words
        TreeNode newNode = new TreeNode(value);

        if (isEmpty()){
            root = newNode;
            uniqueWordsRead++;
            return;
        }
        TreeNode current = root;
        boolean finished = false;
        while (!finished){
            //Checks if current word is the same as word getting inserted. If true > +1 to existing word
            if (value.equals(current.data)) {
                current.addTimesOccurred();
                finished = true;
            }
            else {
                // Compares strings. compareTo returns int with alphabetical difference. If true > go to left side
                if (value.compareTo(current.data) < 0) {
                    // If empty on left side, insert and exit while-loop
                    if (current.left == null) {
                        current.left = newNode;
                        uniqueWordsRead++;
                        finished = true;
                    }
                    // Else not empty, go into left side and repeat while-loop
                    else current = current.left;
                }
                // Else go to right side
                else {
                    // If empty on right side, insert and exit while-loop
                    if (current.right == null){
                        current.right = newNode;
                        uniqueWordsRead++;
                        finished = true;
                    }
                    // Else not empty, go into right side and repeat while-loop
                    else current = current.right;
                }
            }
        }
    }

    /** ROOT OF ENTIRE SEARCH TREE */
    public TreeNode root;

    /** CHECKS FOR EMPTY TREE */
    public boolean isEmpty(){
        return (root == null);
    }

    /** PRINT TREE WORDS AND OTHER DATA */
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

