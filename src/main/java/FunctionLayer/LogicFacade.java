package FunctionLayer;

import DBAccess.UserMapper;
import FunctionLayer.databaseExceptions.DBexception;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.User;
import FunctionLayer.user.UserExists;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The purpose of LogicFacade is to...
 * @author kasper
 */
public class LogicFacade {

    public static User login(String email, String password ) throws LoginSampleException, InvalidPassword {

        //Get user from the DB with a specific name
        User user = UserMapper.login(email);

        //Username is null => no user was found in DB
        if(user.getUsername() == null){
            throw new NullPointerException();
        }

        //Validate the DB password with the provided one
        else if (!user.isPasswordCorrect(password)) {

            throw new InvalidPassword();

        } else  {

            //Return user if password is validated
            return user;

        }
    } 

    public static User createUser(String userName, String email, String password1, String password2) throws LoginSampleException, UserExists, InvalidPassword {

        //Create timeStamp
        LocalDateTime localTime = LocalDateTime.now();
        //Generate salt
        byte[] salt = User.generateSalt();
        //Generate secret
        byte[] secret = User.calculateSecret(salt, password1);
        //Create user

        //Validate user input
        if(UserMapper.userAldreadyExistsInDB(email)){
            throw new UserExists(email);

        } else if (!password1.equals(password2)){
            throw new InvalidPassword();

        } else {
            //Create user
            User user = new User(userName, email, "customer", localTime, salt, secret);

            //Save/create the user in the DB and return the users (No longer id -1)
            user = UserMapper.createUser(user);

            return user;
        }
    }

    public static ArrayList<User> getAllUsersFromDB() throws DBexception {
        ArrayList<User> allUsersFromDB = UserMapper.getAllUsersFromDB();
        return allUsersFromDB;
    }

    public static User createUserFromAdminPage(String userName, String email, String password, String user_role) throws LoginSampleException, UserExists, InvalidPassword {

        //Create timeStamp
        LocalDateTime localTime = LocalDateTime.now();
        //Generate salt
        byte[] salt = User.generateSalt();
        //Generate secret
        byte[] secret = User.calculateSecret(salt, password);
        //Create user

        //Validate user input
        if(UserMapper.userAldreadyExistsInDB(email)){
            throw new UserExists(email);

        } else {
            //Create user
            User user = new User(userName, email, user_role, localTime, salt, secret);

            //Save/create the user in the DB and return the users (No longer id -1)
            user = UserMapper.createUser(user);

            return user;
        }
    }

    public static User getUserById(int user_id) throws DBexception {

        User user = UserMapper.getUserById(user_id);
        return user;

    }

    public static void updateUserById(int user_id, String username, String user_email, String user_role) throws LoginSampleException {
        UserMapper.updateUserById(user_id, username, user_email, user_role);
    }

    public static void deleteUserById(int user_id) throws LoginSampleException {
        UserMapper.deleteUserById(user_id);
    }

    public static void changeUserRoleToCustomer(int user_id) throws LoginSampleException {
        UserMapper.changeUserRoleToCustomer(user_id);
    }

    public static void changeUserRoleToAdmin(int user_id) throws LoginSampleException {
        UserMapper.changeUserRoleToAdmin(user_id);
    }


}
