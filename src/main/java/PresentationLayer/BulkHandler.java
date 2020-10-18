package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.databaseExceptions.DBexception;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.User;
import FunctionLayer.user.UserExists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class BulkHandler extends Command {
    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException, InvalidPassword, UserExists {

        int user_id;
        String errorMessage, successMessage, adminMenu, destination;

        String selector_option = request.getParameter("selector_option");
        String [] bulkArrayUserId = request.getParameterValues("bulkArrayUserId");

        if(bulkArrayUserId.length > 0){
            switch (selector_option){
                case "customer":
                    try{
                        //we loop through the array with the user id's we want to change
                        for(String currentElement : bulkArrayUserId){
                            user_id = Integer.parseInt(currentElement);
                            LogicFacade.changeUserRoleToCustomer(user_id);
                        }

                        successMessage = "Users where successfully converted to 'Customers'";

                        return getStringWhenSuccess(request, successMessage);

                    } catch (LoginSampleException | DBexception e) {
                        errorMessage = "DB error " + e;
                        //We create a incomplete user to hold the variables to we can reset the edit page
                        return getStringWhenError(request, errorMessage);
                    }

                case "admin":
                    try{
                        //we loop through the array with the user id's we want to change
                        for(String currentElement : bulkArrayUserId){
                            user_id = Integer.parseInt(currentElement);
                            LogicFacade.changeUserRoleToAdmin(user_id);
                        }

                        successMessage = "Users where successfully converted to 'Admin'";

                        return getStringWhenSuccess(request, successMessage);

                    } catch (LoginSampleException | DBexception e) {
                        errorMessage = "DB error " + e;

                        return getStringWhenError(request, errorMessage);
                    }

                case "delete":
                    try{
                        //we loop through the array with the user id's we want to change
                        for(String currentElement : bulkArrayUserId){
                            user_id = Integer.parseInt(currentElement);
                            LogicFacade.deleteUserById(user_id);
                        }

                        successMessage = "Users where successfully deleted.";

                        return getStringWhenSuccess(request, successMessage);

                    } catch (LoginSampleException | DBexception e) {
                        errorMessage = "DB error " + e;
                        //We create a incomplete user to hold the variables to we can reset the edit page
                        return getStringWhenError(request, errorMessage);
                    }

                default:
                    errorMessage = "Failed to execute bulk command";
                    request.setAttribute("errorMessage", errorMessage);
                    adminMenu = "allUsers";
                    request.setAttribute("adminMenu", adminMenu);
                    destination = "adminPage";
                    return destination;
            }
        } else {
            errorMessage = "Nothing was selected, please try again...";
            request.setAttribute("errorMessage", errorMessage);
            adminMenu = "allUsers";
            request.setAttribute("adminMenu", adminMenu);
            destination = "adminPage";
            return destination;
        }
    }

    private String getStringWhenError(HttpServletRequest request, String errorMessage) {
        String adminMenu;
        String destination;
        request.setAttribute("errorMessage", errorMessage);
        adminMenu = "allUsers";
        request.setAttribute("adminMenu", adminMenu);
        destination = "adminPage";
        return destination;
    }

    private String getStringWhenSuccess(HttpServletRequest request, String successMessage) throws DBexception {
        String adminMenu;
        String destination;
        request.setAttribute("successMessage", successMessage);

        //We reload the user list
        ArrayList<User> allUsersFromDB = LogicFacade.getAllUsersFromDB();
        request.setAttribute("allUsersFromDB", allUsersFromDB);

        adminMenu = "allUsers";
        request.setAttribute("adminMenu", adminMenu);
        destination = "adminPage";
        return destination;
    }
}
