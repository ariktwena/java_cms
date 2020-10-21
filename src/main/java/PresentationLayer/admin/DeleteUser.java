package PresentationLayer.admin;

import FunctionLayer.layer.user.LogicFacadeUser;
import FunctionLayer.layer.database.DBexception;
import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.entities.User;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DeleteUser extends Command {
    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) {

        String errorMessage = "";
        String successMessage = "";

        int user_id = Integer.parseInt(request.getParameter("user_id"));


        if(user_id == 0){
            errorMessage = "User was not deleted..";
            //We create a incomplete user to hold the variables to we can reset the edit page
            return getString(request, errorMessage);

        }else {
            try{
                String adminMenu;
                String destination;
                //We don't return a user, because we don't need the data
                LogicFacadeUser.deleteUserById(user_id);

                successMessage = "User with user_id: " + user_id + " was deleted.";
                request.setAttribute("successMessage", successMessage);

                //We reload the user list
                ArrayList<User> allUsersFromDB = LogicFacadeUser.getAllUsersFromDB();
                request.setAttribute("allUsersFromDB", allUsersFromDB);

                adminMenu = "allUsers";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            }  catch (LoginSampleException | DBexception e) {
                errorMessage = "DB error " + e;
                //We create a incomplete user to hold the variables to we can reset the edit page
                return getString(request, errorMessage);
            }

        }
    }

    private String getString(HttpServletRequest request, String errorMessage) {
        String adminMenu;
        String destination;
        request.setAttribute("errorMessage", errorMessage);
        adminMenu = "allUsers";
        request.setAttribute("adminMenu", adminMenu);
        destination = "adminPage";
        return destination;
    }
}
