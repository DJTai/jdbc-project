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
    static final String DISPLAY_BOOKS = "%-30s%-30s%-30s%-20s%-20s\n";

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
                                        listByPublisher(mConnection);
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
                                        insertNewBook(mConnection);
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
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                
                sql = "SELECT groupName, headWriter, yearFormed, Subject FROM writingGroups order by groupName";
                resultSet = statement.executeQuery(sql);
                System.out.println();
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
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                // sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
                sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers order by publisherName";
                resultSet = statement.executeQuery(sql);
                System.out.println();
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
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                // sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
                sql = "SELECT bookTitle FROM books order by bookTitle";
                resultSet = statement.executeQuery(sql);
                System.out.println();
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
                System.out.print("\nEnter a group name or 'q' to go back to the main menu: ");
                String userInput = in.nextLine();
                
                // User may go back to the main menu if they enter q.
                if(userInput.equals("q")) {
                    break;
                }

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
                    // Give user the option to enter a different group name, or quit listByGroup altogether.
                    System.out.println("The group name '" + userInput + "' does not exist.");
                }
                // STEP 6: Clean-up environment
                resultSet.close();
                pstmt.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /** List all the data for a publisher specified by the user.
     * 
     * @param connection - The connection session to the database.
     */
    private static void listByPublisher(Connection connection) {
        try {
            boolean askAgain = true;
            while (askAgain) {
                // Prompt the user to enter the publisher name of the publisher hey want to view data for.
                Scanner in = new Scanner(System.in);
                System.out.print("\nEnter a publisher name or 'q' to go back to the main menu: ");
                String userInput = in.nextLine();

                // User may go back to the main menu if they enter q.
                if(userInput.equals("q")) {
                    break;
                }
                
                // stmt has a bind variable (?).
                // The user may have entered a publisher name with casing that is different than what we have in the database.
                // Use the sql lower function to make the groupName attribute all lower-case, which will come in handy for comparison with the user's input.
                String stmt = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM publishers where lower(publisherName) = ?";
                
                // Create PreparedStatement object.
                PreparedStatement pstmt = connection.prepareStatement(stmt);

                // Bind the variable.
                // Convert userInput to lower-case so that comparison with the attribute in the database is somewhat "case-insensitive".
                pstmt.setString(1, userInput.toLowerCase());
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println();
                
                if (resultSet.next()) {
                    System.out.printf(DISPLAY_PUBLISHERS, "Publisher Name", "Publisher Address", "Publisher Phone",
                        "Publisher Email");

                    // Retrieve by column name
                    String publisherName = resultSet.getString("publisherName");
                    String publisherAddress = resultSet.getString("publisherAddress");
                    String publisherPhone = resultSet.getString("publisherPhone");
                    String publisherEmail= resultSet.getString("publisherEmail");

                    // Display values
                    System.out.printf(DISPLAY_PUBLISHERS,
                            displayNull(publisherName), displayNull(publisherAddress), displayNull(publisherPhone),
                            displayNull(publisherEmail));
                    askAgain = false;
                } else {
                    // Give user the option to enter a different publisher name, or quit listByPublisher altogether.
                    System.out.println("The publisher name '" + userInput + "' does not exist.");
                }
                // STEP 6: Clean-up environment
                resultSet.close();
                pstmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** Insert a new book into the database.
     * 
     * @param connection - The connection session to the database.
     */
    private static void insertNewBook(Connection connection) {
        boolean askAgain = true;
        while (askAgain) {
            try {
                System.out.println("\nEnter '-1' at any time to go back to the main menu.");
                // stmt has bind variables (?).
                String stmt = "INSERT INTO books(groupName, bookTitle, publisherName, yearPublished, numberPages) "
                        + "VALUES (?, ?, ?, ?, ?)";
                // Create PreparedStatement object.
                PreparedStatement pstmt = connection.prepareStatement(stmt);
                
                // Prompt the user to enter the group name.
                Scanner in = new Scanner(System.in);
                System.out.print("Group name: ");
                String userGroupName = in.nextLine();
                if(userGroupName.equals("-1")) {
                    break;
                }
                // Bind the variable.
                pstmt.setString(1, userGroupName);

                // Prompt the user to enter the book title.
                System.out.print("Book title: ");
                String userBookTitle = in.nextLine();
                if(userBookTitle.equals("-1")) {
                    break;
                }
                pstmt.setString(2, userBookTitle); 

                // Prompt the user to enter the publisher name.
                System.out.print("Publisher name: ");
                String userPublisherName = in.nextLine();
                if(userPublisherName.equals("-1")) {
                    break;
                }
                pstmt.setString(3, userPublisherName);
                
                // Prompt the user to enter the year published.
                System.out.print("Year published: ");
                int userYearPublished = in.nextInt();
                if(userYearPublished == -1) {
                    break;
                }
                pstmt.setInt(4, userYearPublished);

                // Prompt the user to enter the number of pages.
                System.out.print("Number of pages: ");
                int userNumOfPages = in.nextInt();
                if(userNumOfPages == -1) {
                    break;
                }
                pstmt.setInt(5, userNumOfPages);
                
                int rowCount = pstmt.executeUpdate();
                
                System.out.println();
                
                if (rowCount >= 1) {
                    askAgain = false;
                    System.out.println("Book successfully inserted.");
                    
                    String stmt2 = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages from books where groupName = ? AND bookTitle = ?";
                    PreparedStatement pstmt2 = connection.prepareStatement(stmt2);
                    
                    // Bind the variable.
                    pstmt2.setString(1, userGroupName);
                    pstmt2.setString(2, userBookTitle);
                    ResultSet resultSet = pstmt2.executeQuery();
                    
                    if (resultSet.next()) {
                        System.out.printf(DISPLAY_BOOKS, "Group Name", "Book Title", "Publisher Name",
                                "Year Published", "Number Of Pages");
                        
                        // Retrieve by column name
                        String groupName = resultSet.getString("groupName");
                        String bookTitle = resultSet.getString("bookTitle");
                        String publisherName = resultSet.getString("publisherName");
                        String yearPublished= resultSet.getString("yearPublished");
                        String numberPages= resultSet.getString("numberPages");
 
                        // Display values
                        System.out.printf(DISPLAY_BOOKS,
                                displayNull(groupName), displayNull(bookTitle), displayNull(publisherName),
                                displayNull(yearPublished), displayNull(numberPages));
                    } 
                    // Clean-up environment
                    resultSet.close();
                    pstmt.close();
                    pstmt2.close();
                } else {
                    System.out.println("The insertion was unsuccessful.");
                }
            } catch (SQLException ex) {
                // Specify to the user the error that occurred.
                if (ex.getMessage().contains("caused a violation of foreign key constraint 'BOOKS_FK01'")) {
                    System.out.println("The group name you entered does not exist. Try a different group name. Keep in mind that group names are case sensitive.");
                } else if (ex.getMessage().contains("caused a violation of foreign key constraint 'BOOKS_FK02'")) {
                    System.out.println("The publisher name you entered does not exist. Try a different publisher name. Keep in mind that publisher names are case sensitive.");
                } else if (ex.getMessage().contains("The statement was aborted because it would have caused a duplicate key value in a unique or primary key constraint or unique index identified by 'BOOKS_PK' defined on 'BOOKS'.")) {
                    System.out.println("There is already a row in the table with those primary key column (group name, book title) values.");
                } else if (ex.getMessage().contains("The statement was aborted because it would have caused a duplicate key value in a unique or primary key constraint or unique index identified by 'BOOKS_CK' defined on 'BOOKS'.")) {
                    System.out.println("There is already a row in the table with those candidate key column (book title, publisher name) values.");
                } else {
                    System.out.println(ex.getMessage());
                }
            } catch (InputMismatchException ime) {
                System.out.println("Only integers are allowed for year published and number of pages. Please try again.");
            }
        }
    }
}
