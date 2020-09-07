package states.main;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/*
    @author - Manzanedo Valle Francisco David.
    email - fmanzanedovalle@gmail.com
 */
public class States {

    //Scanner Object to read the user inputs
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int linesCounter;
        byte menuOption = 0;
        boolean exit, check;
        final String FILE_NAME = "states.csv";
        String stateToDelete = "", acronym = "";

       //HashMap to store the file data
        Map<String, String> statesCollection = new HashMap<String, String>();

        /*
        We call the readFile method to read all the data stored into the
         file and returns the value of the lines counter
        */
        linesCounter = readFile(statesCollection, FILE_NAME);

        do {
            showMenu();
            do {
                try {
                    check = true;
                    menuOption = validateInteger();
                } catch (InputMismatchException ex) {
                    check = false;
                    System.err.print(ex.getMessage());
                    input.nextLine();
                }
            } while (!check);
            input.nextLine();

            switch (menuOption) {
                case 0: //Exit
                    exit = true;
                    System.out.println("End of the program!");
                    break;

                case 1: //Shows the collection of states
                    exit = false;
                    showCollection(statesCollection, linesCounter);
                    break;

                case 2://Search state by code
                    exit = false;
                    do {
                        try {
                            check = true;
                            acronym = validateString("Enter the " +
                                "acronym of the state " +
                                "to search (XX): ");
                        }catch (NullPointerException ex){
                            check = false;
                            System.err.println(ex.getMessage());
                            input.nextLine(); }
                    }while (!check);

                    searchStateByCode(statesCollection,acronym);
                    break;

                case 3://Delete State
                    exit = false;
                    do {
                        try {
                            check = true;
                            stateToDelete = validateString("Enter the acronym of the " +
                                    "state (XX): ");
                        }catch (NullPointerException e){
                           check = false;
                            System.err.println(e.getMessage());
                            input.nextLine(); }
                    }while (!check);
                    //We delete the state select by th user
                    deleteState(statesCollection,stateToDelete);
                    break;

                default://Invalid options
                    exit = false;
                    System.out.println("Invalid input: Please, type an " +
                            "Integer (1-3) 0 to EXIT.");
                    break;
            }
        } while (!exit);
    }
    //Method that's show the contest menu
    private static void showMenu() {
        System.out.println("------- Menu -------");
        System.out.println("1. List collection");
        System.out.println("2. Search by code");
        System.out.println("3. Delete state");
        System.out.println("--------------------");
        System.out.println( "0. Exit.\n");
        System.out.print( "Choose a menu option: ");
    }

    //Method to read the file and returns the amount of lines counter
    private static int readFile(Map<String, String> states, final String FILE_NAME) {

        int linesCounter = 0;
        //check if the file exists
        if (!(new File(FILE_NAME)).exists())
            System.out.println("File: " + FILE_NAME + " do not exits.");
            //If the file exits, we can read it
        else {
            try {
                BufferedReader inputFile = new BufferedReader(
                        new FileReader(
                            new File(FILE_NAME)));

                String line = inputFile.readLine();
                if (line == null) System.out.println("The File is empty.");
                else {
                    //If the line is not null, we call the method to add the
                    // states
                    while (line != null) {
                        String[] separatedData = line.split(";");
                        states.put(separatedData[1], separatedData[0]);
                        line = inputFile.readLine();
                        linesCounter++;
                    }
                }//If we already have read the data, we close the file
                inputFile.close();
            } catch (IOException e) {
                System.out.println("An error has occurred.." + e.getMessage()); }
        }
        return linesCounter;
    }

    //Method to print the data stored into the HashMap
    private static void showCollection(Map<String, String> states, int counterLines) {
        for (Map.Entry<String, String> st : states.entrySet()) {
            System.out.println(st.getKey().toUpperCase() +
                    " - " + st.getValue());
        }
        System.out.println("\nRead lines: " + counterLines);
        System.out.println("Lines stored in the collection: " + states.size()+"\n");
    }

    //Method to search a state by code enter by the user
    private static void searchStateByCode(Map<String,String> states, String acronym ){
        if(!states.containsKey(acronym))
            System.out.println("No results found.\n");
        else
            System.out.println("The state is: "+states.get(acronym)+"\n");
    }

    //Method to delete a state if it's stored on the HashMap
    private static void deleteState(Map<String,String> states, String stateToDelete){

        //If the state it's not stored into the HashMap
        if (!states.containsKey(stateToDelete))
            System.out.println("No results found.\n");
        //If the state it's stored into the HashMap, we delete it
        else {
            System.out.println("Delete: "+states.get(stateToDelete));
            states.remove(stateToDelete);
        }
    }

    //Method to validate the input option of the Menu
    private static byte validateInteger() throws InputMismatchException {
        byte data;
        boolean check = false;
        while (!check){
            if (!input.hasNextByte())
                throw new InputMismatchException("Invalid input. Please, type an " +
                        "Integer: ");
            else check = true;
        }
        data = input.nextByte();
        return data;
    }
    //Method to validate a non empty string
    private static String validateString(String msg) throws NullPointerException{
        boolean result;
        String data;
        System.out.print(msg);
        do {
            data = input.nextLine().toUpperCase();
            if(data.isEmpty())
                throw new NullPointerException("Invalid input. The field " +
                        "can not be empty.\nPress Enter to continue...");
            else result = true;
        }while (!result);
        return data;
    }
}
