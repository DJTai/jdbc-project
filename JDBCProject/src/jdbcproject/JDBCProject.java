/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authors Chelsea Marfil & David Taitingfong
 */
public class JDBCProject {

    /* Databas Credentials */
    static String USER;
    static String PASS;
    static String DBNAME;

    /* JDBC driver name and DB URL */
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

    /* printf formatting */
    static final String DISPLAY_FORMAT = "%-5s%-15s%-15s%-15s\n";
    static final String DISPLAY_WRITING_GROUPS = "%-30s%-30s%-6s%-20s\n";

    /**
     * Takes the input String and outputs "N/A" if the String is empty or null.
     *
     * @param input - String to be mapped
     * @return - Input String or "N/A" as appropriate
     */
    public static String displayNull(String input) {
        if (null == input || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }

    private static void getDBCredentials() {
        Scanner in = new Scanner(System.in);

        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();

        System.out.print("Database Username: ");
        USER = in.nextLine();

        System.out.print("Database password: ");
        PASS = in.nextLine();

        DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* DB connection and statement being used */
        Connection connection = null;
        Statement statement = null;

        getDBCredentials();

        System.out.println("DB accessed\n");

        accessDatabase(connection, statement);

        System.exit(0);
    }

    /**
     * Prints to the screen the main menu prompts
     *
     * @param mConnection - Connection to DB
     * @param mStatement - Statement to be executed
     */
    public static void accessDatabase(Connection mConnection, Statement mStatement) {

        Scanner stdin = new Scanner(System.in);
        int response = -1;

        // Response of 4 means to exit
        while (response != 4) {

            printMainMenu();

            // try-catch for integer input only
            try {
                response = stdin.nextInt();

                switch (response) {

                    case 1:
                        /* Writing Groups Submenu */
                        stdin.nextLine();  // Clear any extra user input
                        int wgResponse;    // Response for Writing group submenu
                        boolean wgRepeat;

                        do {
                            try {
                                printWritingGroupMenu();
                                wgResponse = stdin.nextInt();
                                wgRepeat = false;

                                // Writing Group inner-menu
                                switch (wgResponse) {
                                    case 1:
                                        // TODO: Call Chelsea's method
                                        // ex: listAllWritingGroups()
                                        break;

                                    case 2:
                                        // TODO: List by user input       
                                        break;

                                    case 3:
                                        // TODO: Return to main menu
                                        System.out.println("Returning to main menu\n");
                                        break;

                                    default:
                                        System.out.println("Selection invalid\n");
                                        stdin.nextLine();
                                        wgRepeat = true;
                                        delayForEffect();
                                        break;
                                }

                            } catch (InputMismatchException ime) {
                                System.out.println("Integers only, please.");
                                stdin.nextLine();
                                wgRepeat = true;
                                delayForEffect();
                            }

                        } while (wgRepeat);

                        break;  // end case 1

                    case 2:
                        /* Publishers Submenu */
                        stdin.nextLine();  // Clear any extra input
                        int pubResponse;
                        boolean pubRepeat;

                        do {
                            try {
                                pubRepeat = false;
                                printPublishersMenu();
                                pubResponse = stdin.nextInt();

                                switch (pubResponse) {
                                    case 1:
                                        // List all
                                        break;

                                    case 2:
                                        // By pub
                                        break;

                                    case 3:
                                        // Insert new pub
                                        break;

                                    case 4:
                                        // Change pub
                                        break;

                                    case 5:
                                        // Return to main menu
                                        System.out.println("Returning to main menu");
                                        break;

                                    default:
                                        System.out.println("Invalid selection");
                                        pubRepeat = true;
                                        break;
                                }
                            } catch (InputMismatchException ime) {
                                System.out.println("Integers only, please.");
                                stdin.nextLine();
                                pubRepeat = true;
                                delayForEffect();
                            }
                        } while (pubRepeat);

                        break;
                    // end case 2

                    case 3:
                        /* Books Submenu */
                        stdin.nextLine();  // Clear any extra input
                        int bkResponse;
                        boolean bkRepeat;

                        do {
                            try {
                                bkRepeat = false;
                                printBooksMenu();
                                bkResponse = stdin.nextInt();

                                switch (bkResponse) {
                                    case 1:
                                        // List all
                                        break;

                                    case 2:
                                        // List by book title
                                        break;

                                    case 3:
                                        // Insert new book
                                        break;

                                    case 4:
                                        // Remove a book
                                        break;

                                    case 5:
                                        // Return to main menu
                                        System.out.println("Returning to main menu");
                                        break;

                                    default:
                                        System.out.println("Invalid selection");
                                        delayForEffect();
                                        pubRepeat = true;
                                        break;
                                }
                            } catch (InputMismatchException ime) {
                                System.out.println("Integers only, please.");
                                stdin.nextLine();
                                bkRepeat = true;
                                delayForEffect();
                            }
                        } while (bkRepeat);

                        break;

                    case 4:
                        /* Exit Program */
                        stdin.nextLine();  // Clear any extra input
                        System.out.println("Thank you for using our services. Farewell!\n");
                        delayForEffect();
                        break;

                    default:
                        stdin.nextLine();  // Clear any extra input
                        System.out.println("Invalid selection");
                        delayForEffect();
                        break;
                }
                // end switch(response)
                
            } catch (InputMismatchException ime) {
                System.out.println("Integers only, please.");
                stdin.nextLine();
                delayForEffect();
            }
        }
        // end while-loop
    }
    // end method

    /**
     * Prints main menu prompt to the screen
     */
    private static void printMainMenu() {

        System.out.println("\n-- MAIN MENU --");
        System.out.println("1. Writing Groups");
        System.out.println("2. Publishers");
        System.out.println("3. Books");
        System.out.println("4. Exit");
        System.out.print("Choice: ");
    }

    /**
     * Prints Writing Groups sub-menu prompt to the screen
     */
    private static void printWritingGroupMenu() {

        System.out.println("\n-- WRITING GROUP MENU -- ");
        System.out.println("1. List all");
        System.out.println("2. List by writing group name");
        System.out.println("3. Main Menu");
        System.out.print("Choice: ");
    }

    /**
     * Prints Publishers sub-menu prompt to the screen
     */
    private static void printPublishersMenu() {

        System.out.println("\n-- PUBLISHERS MENU -- ");
        System.out.println("1. List all");
        System.out.println("2. List by publisher name");
        System.out.println("3. Insert a new publisher");
        System.out.println("4. Change a publisher");
        System.out.println("5. Main Menu");
        System.out.print("Choice: ");
    }

    /**
     * Prints Book sub-menu prompt to the screen
     */
    private static void printBooksMenu() {

        System.out.println("\n-- BOOKS MENU -- ");
        System.out.println("1. List all");
        System.out.println("2. List by book title");
        System.out.println("3. Insert a new book");
        System.out.println("4. Remove a current book");
        System.out.println("5. Main Menu");
        System.out.print("Choice: ");
    }

    private static void delayForEffect() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
