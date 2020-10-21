package PresentationLayer;

import FunctionLayer.layer.user.LogicFacadeUser;
import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.layer.user.InvalidPassword;
import FunctionLayer.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 The purpose of Login is to...

 @author kasper
 */
public class Login extends Command {

    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {

        //Error message and destination page
        String messageSignIn = "";
        String destinationPage = "";
        String errorMessage = "";

        String email = request.getParameter( "email" );
        String password = request.getParameter( "password" );

        //Check the username and password that is provided, and the user from DB
        try {

            User user = LogicFacadeUser.login(email, password);

            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute( "username", user.getUsername() );
            session.setAttribute("email",email);
            session.setAttribute( "role", user.getUserRole() );

            //Redirect by user_role and get session data
            if(user.getUserRole().equals("admin")){
                destinationPage = "adminPage";

            } else if (user.getUserRole().equals("customer")){
                destinationPage = "customerPage";

            } else {
                destinationPage = "index";
            }

            return destinationPage;

        } catch(NullPointerException | InvalidPassword e){
            //Unknown username or password
            messageSignIn = "Unknown username or password";
            request.setAttribute("messageSignIn", messageSignIn);
            destinationPage = "index";
            return destinationPage;

        }

    }

}
