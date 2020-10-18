package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.User;
import FunctionLayer.user.UserExists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Register extends Command {

    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) {

        //Error message and destination page
        String messageSignUp = "";
        String destinationPage = "";

        String username = request.getParameter( "username" );
        String email = request.getParameter( "email" );
        String password1 = request.getParameter( "password1" );
        String password2 = request.getParameter( "password2" );

        try{
            User user = LogicFacade.createUser( username, email, password1, password2 );
            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute( "username", username );
            session.setAttribute("email",email);
            session.setAttribute( "username", username );
            session.setAttribute( "role", user.getUserRole() );

            destinationPage = "adminPage";

            return destinationPage;

        } catch (InvalidPassword e){
            messageSignUp = "Passwords do not match";
            request.setAttribute("messageSignUp", messageSignUp);
            destinationPage = "index";
            return destinationPage;

        } catch (UserExists e) {
            messageSignUp = "User already exist in our system";
            request.setAttribute("messageSignUp", messageSignUp);
            destinationPage = "index";
            return destinationPage;

        } catch (LoginSampleException e) {
            messageSignUp = "DB error";
            request.setAttribute("messageSignUp", messageSignUp);
            destinationPage = "index";
            return destinationPage;
        }


    }

}
