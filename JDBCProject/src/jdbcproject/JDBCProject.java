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
    static final String DISPLAY_WRITING_GROUPS = "%-30s%-30s%-15s%-20s\n";
    static final String DISPLAY_PUBLISHERS = "%-30s%-30s%-20s%-40s\n";
    static final String DISPLAY_BOOKTITLES = "%-30s\n";

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
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /* DB connection and statement being used */
        Connection connection = null;
        Statement statement = null;
        
        // STEP 2: Register JDBC Driver
        Class.forName(JDBC_DRIVER);
        getDBCredentials();
      
        // STEP 3: Open a connection
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection(DB_URL);
      
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
                                        listAll(1, mStatement, mConnection);
                                        break;

                                    case 2:
                                            // TODO: List by user input
                                        listByGroup(mConnection);
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
                                        listAll(2, mStatement, mConnection);
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
                                        listAll(3, mStatement, mConnection);
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
    }

    
    /** Lists all writing groups, publishers, or book titles.
     *
     * @param userChoice - User's selection from the menu.
     * @param statement - SQL statement to be executed.
     * @param connection - The connection session with the database.
     */
    private static void listAll(int userChoice, Statement statement, Connection connection) {
        // If user wants to list all writing groups.
        if (userChoice == 1) {
            try {
                // Execute a query.
                System.out.println("Creating a statement...");
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                
                sql = "SELECT groupName, headWriter, yearFormed, Subject FROM writingGroups";
                resultSet = statement.executeQuery(sql);

                // STEP 5: Extract data from the result set
                System.out.printf(DISPLAY_WRITING_GROUPS, "Group Name", "Head Writer", "Year Formed",
                        "Subject");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String groupName = resultSet.getString("groupName");
                    String headWriter = resultSet.getString("headWriter");
                    String yearFormed = resultSet.getString("yearFormed");
                    String subject = resultSet.getString("subject");

                    // Display values
                    System.out.printf(DISPLAY_WRITING_GROUPS,
                            displayNull(groupName), displayNull(headWriter), displayNull(yearFormed),
                            displayNull(subject));
                }

                // STEP 6: Clean-up environment
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // If user wants to list all publishers.
        else if (userChoice == 2) {
            try {
                // Execute a query.
                System.out.println("Creating a statement...");
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                // sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
                sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers";
                resultSet = statement.executeQuery(sql);

                // STEP 5: Extract data from the result set
                System.out.printf(DISPLAY_PUBLISHERS, "Publisher Name", "Publisher Address", "Publisher Phone",
                        "Publisher Email");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String publisherName = resultSet.getString("publisherName");
                    String publisherAddress = resultSet.getString("publisherAddress");
                    String publisherPhone = resultSet.getString("publisherPhone");
                    String publisherEmail = resultSet.getString("publisherEmail");

                    // Display values
                    System.out.printf(DISPLAY_PUBLISHERS,
                            displayNull(publisherName), displayNull(publisherAddress), displayNull(publisherPhone),
                            displayNull(publisherEmail));
                }

                // STEP 6: Clean-up environment
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // If user wants to list all book titles.
        else if (userChoice == 3) {
            try {
                // Execute a query.
                System.out.println("Creating a statement...");
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                // sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
                sql = "SELECT bookTitle FROM books";
                resultSet = statement.executeQuery(sql);

                // STEP 5: Extract data from the result set
                System.out.printf(DISPLAY_BOOKTITLES, "Book Title");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String bookTitle = resultSet.getString("bookTitle");

                    // Display values
                    System.out.printf(DISPLAY_BOOKTITLES,
                            displayNull(bookTitle));
                }

                // STEP 6: Clean-up environment
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
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

    /** Lists all the data for a group specified by the user.
     * 
     * @param connection - The connection session with the database.
     */
    private static void listByGroup(Connection connection) {
        try {
            boolean askAgain = true;
            while (askAgain) {
                // Prompt the user to enter the group name of the writing group they want to view data for.
                Scanner in = new Scanner(System.in);
                System.out.print("Group name: ");
                String userInput = in.nextLine();

                // stmt has a bind variable (?).
                // The user may have entered a group name with casing that is different than what we have in the database.
                // Use the sql lower function to make the groupName attribute all lower-case, which will come in handy for comparison with the user's input.
                String stmt = "SELECT groupName, headWriter, yearFormed, Subject FROM writingGroups where lower(groupName) = ?";
                // Create PreparedStatement object.
                PreparedStatement pstmt = connection.prepareStatement(stmt);

                // Bind the variable.
                // Convert userInput to lower-case so that comparison with the attribute in the database is somewhat "case-insensitive".
                pstmt.setString(1, userInput.toLowerCase());
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println();
                if (resultSet.next()) {
                    System.out.printf(DISPLAY_WRITING_GROUPS, "Group Name", "Head Writer", "Year Formed",
                        "Subject");

                    // Retrieve by column name
                    String groupName = resultSet.getString("groupName");
                    String headWriter = resultSet.getString("headWriter");
                    String yearFormed = resultSet.getString("yearFormed");
                    String subject = resultSet.getString("subject");

                    // Display values
                    System.out.printf(DISPLAY_WRITING_GROUPS,
                            displayNull(groupName), displayNull(headWriter), displayNull(yearFormed),
                            displayNull(subject));
                    askAgain = false;
                } else {
                    System.out.println("The group name " + userInput + " does not exist.\nEnter a different group name.");
                }
                // STEP 6: Clean-up environment
                resultSet.close();
                pstmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
