/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;

import java.sql.*;
import java.util.Scanner;

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
    static final String DISPLAY_FORMAT = "%-5s%-15s%-15s%-15s\n";

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
    public static void main(String[] args) {
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

            // STEP 4: Execute a query
            System.out.println("Creating a statement...");
            statement = connection.createStatement();
            String sql;
            ResultSet resultSet;

            sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
            resultSet = statement.executeQuery(sql);

            // STEP 5: Extract data from the result set
            System.out.printf(DISPLAY_FORMAT, "ID", "First Name", "Last Name",
                    "Phone #");
            while (resultSet.next()) {
                // Retrieve by column name
                String id = resultSet.getString("au_id");
                String first = resultSet.getString("au_fname");
                String last = resultSet.getString("au_lname");
                String phone = resultSet.getString("phone");

                // Display values
                System.out.printf(DISPLAY_FORMAT,
                        displayNull(id), displayNull(first), displayNull(last),
                        displayNull(phone));
            }

            // STEP 6: Clean-up environment
            resultSet.close();
            statement.close();
            connection.close();

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
}
