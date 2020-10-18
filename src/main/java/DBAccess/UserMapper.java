package DBAccess;

import FunctionLayer.databaseExceptions.DBexception;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 The purpose of UserMapper is to...

 @author kasper
 */
public class UserMapper {

    public static User createUser( User user ) throws LoginSampleException {

        try {
            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "INSERT INTO users (username, user_email, user_role, user_reg, salt, secret) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );

            //Link variables to the SQL statement
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUserRole());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(user.getRegistrationDate()));
            ps.setBytes(5, user.getSalt());
            ps.setBytes(6, user.getSecret());

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

            //Optional: Get result from the SQL execution, that returns the executed keys (user_id, user_name etc..)
            ResultSet rs = ps.getGeneratedKeys();

            //Search if there is a result from the DB execution
            if (rs.next()) {
                //Create user from the user_id key that is returned form the DB execution
                return user.withId(rs.getInt(1));

            } else {
                //Return null, if no result is returned form the execution
                return null;
            }
        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }


    public static boolean userAldreadyExistsInDB(String email) {

        try{

            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "SELECT user_email FROM users WHERE user_email = (?)";
            PreparedStatement ps = con.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setString(1, email);

            //Execute the SQL query and save the result
            ResultSet rs = ps.executeQuery();

            //Search if there is a result from the DB execution
            if (rs.next()) {
                //Return true, if the is a DB execution result
                return true;

            } else {
                //Return false, if the isn't a DB execution result
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static User login( String user_email ) throws LoginSampleException {

        try {
            Connection con = Connector.connection();
            String SQL = "SELECT user_id, username, user_role, user_reg, salt, secret FROM users "
                    + "WHERE user_email = ?";
            PreparedStatement ps = con.prepareStatement( SQL );
            ps.setString( 1, user_email );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString( "username" );
                String user_role = rs.getString( "user_role" );
                LocalDateTime user_reg = rs.getTimestamp("user_reg").toLocalDateTime();
                byte[] salt = rs.getBytes("salt");
                byte[] secret = rs.getBytes("secret");

                User user = new User( user_id, username, user_email, user_role, user_reg, salt, secret );

                return user;

            } else {
                throw new LoginSampleException( "Could not validate user" );
            }
        } catch ( ClassNotFoundException | SQLException ex ) {
            throw new LoginSampleException(ex.getMessage());
        }
    }

    public static ArrayList<User> getAllUsersFromDB() throws DBexception {

        ArrayList<User> allUsersFromDB = new ArrayList<>();

        try {
            Connection con = Connector.connection();

            String SQL = "SELECT user_id, username, user_email, user_role, user_reg, salt, secret FROM users "
                    + "WHERE user_active = 1";

            PreparedStatement ps = con.prepareStatement( SQL );

            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString( "username" );
                String user_email = rs.getString( "user_email" );
                String user_role = rs.getString( "user_role" );
                LocalDateTime user_reg = rs.getTimestamp("user_reg").toLocalDateTime();
                byte[] salt = rs.getBytes("salt");
                byte[] secret = rs.getBytes("secret");

                User user = new User( user_id, username, user_email, user_role, user_reg, salt, secret );

                allUsersFromDB.add(user);
            }
        } catch ( ClassNotFoundException | SQLException ex ) {
            throw new DBexception();
        }

        return allUsersFromDB;
    }

    public static User getUserById(int user_id) throws DBexception {

        User user = null;

        try {
            Connection con = Connector.connection();

            String SQL = "SELECT username, user_email, user_role, user_reg, salt, secret FROM users "
                    + "WHERE user_id = ?";

            PreparedStatement ps = con.prepareStatement( SQL );
            ps.setInt( 1, user_id );
            ResultSet rs = ps.executeQuery();

            if ( rs.next() ) {
                String username = rs.getString( "username" );
                String user_email = rs.getString( "user_email" );
                String user_role = rs.getString( "user_role" );
                LocalDateTime user_reg = rs.getTimestamp("user_reg").toLocalDateTime();
                byte[] salt = rs.getBytes("salt");
                byte[] secret = rs.getBytes("secret");

                user = new User( user_id, username, user_email, user_role, user_reg, salt, secret );

                return user;
            }
        } catch ( ClassNotFoundException | SQLException ex ) {
            throw new DBexception();
        }

        return user;
    }

    public static void updateUserById(int user_id, String username, String user_email, String user_role) throws LoginSampleException {

        try {
            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET username = ?, user_email = ?, user_role = ? "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = con.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setString(1, username);
            ps.setString(2, user_email);
            ps.setString(3, user_role);
            ps.setInt(4, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }

    public static void deleteUserById(int user_id) throws LoginSampleException {
        try {
            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_active = 0 "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = con.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }

    public static void changeUserRoleToCustomer(int user_id) throws LoginSampleException {
        try {
            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_role = 'customer' "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = con.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }

    public static void changeUserRoleToAdmin(int user_id) throws LoginSampleException {
        try {
            Connection con = Connector.connection();

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_role = 'admin' "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = con.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }
}
