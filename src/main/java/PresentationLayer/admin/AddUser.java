package PresentationLayer.admin;

import FunctionLayer.layer.user.LogicFacadeUser;
import FunctionLayer.layer.user.InvalidPassword;
import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.entities.User;
import FunctionLayer.layer.user.UserExists;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser extends Command {

    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException, InvalidPassword, UserExists {

        //Error message and destination page
        String destination = "";
        String errorMessageCreate = "";
        String successMessage = "";
        String adminMenu = "";

        String username = request.getParameter( "username" );
        String user_email = request.getParameter( "user_email" );
        String password = request.getParameter( "password" );
        String user_role = request.getParameter( "user_role" );

        if(username.length() == 0 | user_email.length() == 0  | password.length() == 0  | user_role.length() == 0 ){
            errorMessageCreate = "Fields can't be empty...";
            request.setAttribute("username", username);
            request.setAttribute("user_email", user_email);
            request.setAttribute("password", password);
            request.setAttribute("user_role", user_role);
            request.setAttribute("errorMessageCreate", errorMessageCreate);
            adminMenu = "addUser";
            request.setAttribute("adminMenu", adminMenu);
            destination = "adminPage";
            return destination;

        } else {
            try{
                User createdUser = LogicFacadeUser.createUserFromAdminPage(username, user_email, password, user_role);

                successMessage = "User: " + createdUser.getUsername() + " was successfully created!";
                request.setAttribute("successMessage", successMessage);
                adminMenu = "addUser";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            } catch (UserExists e) {
                errorMessageCreate = "User already exist in our system";
                request.setAttribute("username", username);
                request.setAttribute("user_email", user_email);
                request.setAttribute("password", password);
                request.setAttribute("user_role", user_role);
                request.setAttribute("errorMessageCreate", errorMessageCreate);
                adminMenu = "addUser";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            } catch (LoginSampleException e) {
                errorMessageCreate = "DB error";
                request.setAttribute("username", username);
                request.setAttribute("user_email", user_email);
                request.setAttribute("password", password);
                request.setAttribute("user_role", user_role);
                request.setAttribute("errorMessageCreate", errorMessageCreate);
                adminMenu = "addUser";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;
            }

        }

    }
}
