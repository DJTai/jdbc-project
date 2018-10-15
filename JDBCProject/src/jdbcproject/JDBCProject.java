/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;

import java.sql.*;
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

    /*
    This is the specification for the printout that is happening:
    - Each % denotes the start of a new field
    - The `-` denotes left justification
    - The number indicates how wide to make the field
    - The "s" denotes that it's a string. All of our output in this test are
      strings, but that won't always be the case.
     */
    // static final String DISPLAY_FORMAT = "%-30s%-30s%-12s%-20s\n";

    /* JDBC driver name and DB URL */
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

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

        in.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /* DB connection and statement being used */
        Connection connection = null;
        Statement statement = null;

        getDBCredentials();

        try {
            // STEP 2: Register JDBC Driver
            Class.forName(JDBC_DRIVER);

            // STEP 3: Open a connection
            System.out.println("Connecting to the database...");
            connection = DriverManager.getConnection(DB_URL);
            
            listAll(1, statement, connection);
            System.out.println();
            listAll(2, statement, connection);
            System.out.println();
            listAll(3, statement, connection);

//            // STEP 4: Execute a query
//            System.out.println("Creating a statement...");
//            statement = connection.createStatement();
//            String sql;
//            ResultSet resultSet;
////            sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
//            sql = "SELECT * FROM Authors";
//            resultSet = statement.executeQuery(sql);
//
//            // STEP 5: Extract data from the result set
//            System.out.printf(DISPLAY_FORMAT, "ID", "First Name", "Last Name",
//                    "Phone #");
//            while (resultSet.next()) {
//                // Retrieve by column name
//                String id = resultSet.getString("au_id");
//                String first = resultSet.getString("au_fname");
//                String last = resultSet.getString("au_lname");
//                String phone = resultSet.getString("phone");
//
//                // Display values
//                System.out.printf(DISPLAY_FORMAT,
//                        displayNull(id), displayNull(first), displayNull(last),
//                        displayNull(phone));
//            }
//
//            // STEP 6: Clean-up environment
//            resultSet.close();
//            statement.close();
//            connection.close();
//
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* Finally block used to close resources */

            // Try to close the statement
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException sqle2) {
                // Can't do anything
            }

            // Try to close the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle3) {
                sqle3.printStackTrace();
            }
    }
    }
    

    
    /**
     *
     * @param userChoice
     * @param statement
     * @param connection
     */
    public static void listAll(int userChoice, Statement statement, Connection connection) {
        // If user wants to list all writing groups.
        if (userChoice == 1) {
            try {
                // Execute a query.
                System.out.println("Creating a statement...");
                statement = connection.createStatement();
                String sql;
                ResultSet resultSet;
                // sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
                sql = "SELECT groupName, headWriter, yearFormed, Subject FROM writingGroups";
                resultSet = statement.executeQuery(sql);

                // STEP 5: Extract data from the result set
                System.out.printf("%-30s%-30s%-12s%-20s\n", "Group Name", "Head Writer", "Year Formed",
                        "Subject");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String groupName = resultSet.getString("groupName");
                    String headWriter = resultSet.getString("headWriter");
                    String yearFormed = resultSet.getString("yearFormed");
                    String subject = resultSet.getString("subject");

                    // Display values
                    System.out.printf("%-30s%-30s%-12s%-20s\n",
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
                System.out.printf("%-30s%-30s%-20s%-40s\n", "Publisher Name", "Publisher Address", "Publisher Phone",
                        "Publisher Email");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String publisherName = resultSet.getString("publisherName");
                    String publisherAddress = resultSet.getString("publisherAddress");
                    String publisherPhone = resultSet.getString("publisherPhone");
                    String publisherEmail = resultSet.getString("publisherEmail");

                    // Display values
                    System.out.printf("%-30s%-30s%-20s%-40s\n",
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
                System.out.printf("%-30s\n", "Book Title");
                while (resultSet.next()) {
                    // Retrieve by column name
                    String bookTitle = resultSet.getString("bookTitle");

                    // Display values
                    System.out.printf("%-30s\n",
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
}
