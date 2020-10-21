package DBAccess.Handlers;

import DBAccess.DBSetup.Connector_old;
import FunctionLayer.layer.database.DBexception;
import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserHandler_old {

    public static boolean userAldreadyExistsInDB(String email) {

        try{

            Connection con = Connector_old.connection();

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

    public static User login(String user_email ) throws LoginSampleException {

        try {
            Connection con = Connector_old.connection();
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
            Connection con = Connector_old.connection();

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
            Connection con = Connector_old.connection();

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
}
