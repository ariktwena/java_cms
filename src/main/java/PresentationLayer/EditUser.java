package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.User;
import FunctionLayer.user.UserExists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser extends Command {
    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException, InvalidPassword, UserExists {

        //Error message and destination page
        String destination = "";
        String errorMessageEdit = "";
        String successMessage = "";
        String adminMenu = "";

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String username = request.getParameter( "username" );
        String user_email = request.getParameter( "user_email" );
        String user_role = request.getParameter( "user_role" );

        if(username.length() == 0 | user_email.length() == 0  | user_role.length() == 0 ){
            errorMessageEdit = "Fields can't be empty...";
            //We create a incomplete user to hold the variables to we can reset the edit page
            return getString(request, errorMessageEdit, user_id, username, user_email, user_role);

        }else {
            try{
                //We dont return a user, because we don't need the data
                LogicFacade.updateUserById(user_id, username, user_email, user_role);

                //So we can see the inputs we made when we return to the edit page
                User userToEdit = new User(user_id, username, user_email, user_role);
                request.setAttribute("userToEdit", userToEdit);

                successMessage = "User: " + username + " was successfully created!";
                request.setAttribute("successMessage", successMessage);
                adminMenu = "editUser";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            }  catch (LoginSampleException e) {
                errorMessageEdit = "DB error " + e;
                //We create a incomplete user to hold the variables to we can reset the edit page
                return getString(request, errorMessageEdit, user_id, username, user_email, user_role);
            }

        }
    }

    private String getString(HttpServletRequest request, String errorMessageEdit, int user_id, String username, String user_email, String user_role) {
        String adminMenu;
        String destination;
        User userToEdit = new User(user_id, username, user_email, user_role);
        request.setAttribute("userToEdit", userToEdit);
        request.setAttribute("errorMessageEdit", errorMessageEdit);
        adminMenu = "editUser";
        request.setAttribute("adminMenu", adminMenu);
        destination = "adminPage";
        return destination;
    }
}
