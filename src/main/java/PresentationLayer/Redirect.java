package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.databaseExceptions.DBexception;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.User;
import FunctionLayer.user.UserExists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class Redirect extends Command {
    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException, InvalidPassword, UserExists {
        String destination = request.getParameter("destination");

        //Error message and destination page
        String errorMessage = "";
        String destinationPage = "";

        //Admin content
        String adminMenu = "";


        HttpSession session = request.getSession();

        switch (destination){

            case "index":
//                String role;
//                if (session.getAttribute("role") != null) {
//                    role = (String) session.getAttribute("role");
//                    request.setAttribute("user_status", "Du er logget på: Rolle: " + role);
//                } else
//                {
//                    request.setAttribute("user_status","Du er ikke logget på makker" );
//                }
                return destination;

            case "addUser":
                adminMenu = "addUser";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            case "allUsers":
                try{
                    ArrayList<User> allUsersFromDB = LogicFacade.getAllUsersFromDB();
                    request.setAttribute("allUsersFromDB", allUsersFromDB);
                    adminMenu = "allUsers";
                    request.setAttribute("adminMenu", adminMenu);
                    destination = "adminPage";
                    return destination;

                } catch (DBexception dBexception) {
                    errorMessage = "Error at the DB";
                    request.setAttribute("errorMessage", errorMessage);
                    destinationPage = "adminPage";
                    return destinationPage;
                }

            case "editUser":
                int user_id = Integer.parseInt(request.getParameter("user_id"));

                try {
                    User userToEdit = LogicFacade.getUserById(user_id);
                    request.setAttribute("userToEdit", userToEdit);
                    adminMenu = "editUser";
                    request.setAttribute("adminMenu", adminMenu);
                    destination = "adminPage";
                    return destination;

                } catch (DBexception dBexception) {
                    errorMessage = "Error at the DB";
                    request.setAttribute("errorMessage", errorMessage);
                    destinationPage = "adminPage";
                    return destinationPage;
                }

            case "addCategory":
                adminMenu = "addCategory";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            case "allCategories":
                adminMenu = "allCategories";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            case "addCar":
                adminMenu = "addCar";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            case "allCars":
                adminMenu = "allCars";
                request.setAttribute("adminMenu", adminMenu);
                destination = "adminPage";
                return destination;

            default: return "index";
        }
    }
}
