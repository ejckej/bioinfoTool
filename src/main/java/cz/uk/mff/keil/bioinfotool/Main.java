package cz.uk.mff.keil.bioinfotool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan Keil
 */
public class Main {

    public static String pathPrefix;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        pathPrefix = new File("").getAbsolutePath()
                + "\\src\\main\\java\\cz\\uk\\mff\\keil\\bioinfotool";
        String path = pathPrefix + "\\help.txt";
        writeFileToConsole(path); //writes instructions (included in help.txt)
        
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                if (!(option > 0 && option < 4)) {
                    System.out.println("Wrong number.");
                }
            } else {
                sc.next();
                System.out.println("Not a number! Try agin...");
            }
        } while (!(option > 0 && option < 4));
        System.out.println("Selected option: " + option);
        
        switch(option){
            case 1:
                ParseCaller();
                break;
            case 2:
                EditDistance.ed();
                break;
            case 3:
                System.out.println("Nothing to do here... Yet.");
                break;
            default:
                break;
        }
    }

    private static void writeFileToConsole(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ParseCaller() {
        
    }
}
