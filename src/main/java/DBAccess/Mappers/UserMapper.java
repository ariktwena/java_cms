package DBAccess.Mappers;

import DBAccess.DBSetup.Connector;
import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.entities.User;

import java.sql.*;

/**
 The purpose of UserMapper is to...

 @author kasper
 */
public class UserMapper {

    public static User createUser( User user ) throws LoginSampleException {

        try (Connection conn = Connector.getConnection()){

            //Prepare a SQL statement from the DB connection
            String SQL = "INSERT INTO users (username, user_email, user_role, user_reg, salt, secret) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );

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

    public static void updateUserById(int user_id, String username, String user_email, String user_role) throws LoginSampleException {

        try (Connection conn = Connector.getConnection()){

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET username = ?, user_email = ?, user_role = ? "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = conn.prepareStatement( SQL );

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
        try (Connection conn = Connector.getConnection()){

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_active = 0 "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = conn.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }

    public static void changeUserRoleToCustomer(int user_id) throws LoginSampleException {
        try (Connection conn = Connector.getConnection()){

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_role = 'customer' "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = conn.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }

    public static void changeUserRoleToAdmin(int user_id) throws LoginSampleException {
        try (Connection conn = Connector.getConnection()){

            //Prepare a SQL statement from the DB connection
            String SQL = "UPDATE users SET user_role = 'admin' "
                    + " WHERE user_id = ? ";
            PreparedStatement ps = conn.prepareStatement( SQL );

            //Link variables to the SQL statement
            ps.setInt(1, user_id);

            //Execute the SQL statement to update the DB
            ps.executeUpdate();

        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new LoginSampleException( ex.getMessage() );
        }
    }
}
