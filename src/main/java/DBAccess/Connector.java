package DBAccess;

import java.sql.*;

/**
 The purpose of Connector is to...

 @author kasper
 */
public class Connector {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;


    private static Connection singleton;

    public static void setConnection( Connection con ) {
        singleton = con;
    }

    public static Connection connection() throws ClassNotFoundException, SQLException {
        if ((singleton == null) || singleton.isClosed()) {
            setDBCredentials();
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            singleton = DriverManager.getConnection( URL, USERNAME, PASSWORD );
        }
        return singleton;
    }

    public static void setDBCredentials() {
        String deployed = System.getenv("DEPLOYED");
        if (deployed != null){
            // Prod: hent variabler fra setenv.sh i Tomcats bin folder
            URL = System.getenv("JDBC_CONNECTION_STRING");
            USERNAME = System.getenv("JDBC_USER");
            PASSWORD = System.getenv("JDBC_PASSWORD");
        } else {
            // Localhost
//            URL = "jdbc:mysql://localhost:3306/jstl?serverTimezone=CET&useSSL=false";
            URL = "jdbc:mysql://localhost:3306/jstl?serverTimezone=Europe/Copenhagen&allowPublicKeyRetrieval=true";
            USERNAME = "testuser";
            PASSWORD = "";
        }
    }



}
