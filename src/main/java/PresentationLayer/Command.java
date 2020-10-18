package PresentationLayer;

import FunctionLayer.user.LoginSampleException;
import FunctionLayer.user.InvalidPassword;
import FunctionLayer.user.UserExists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

abstract class Command {

    private static HashMap<String, Command> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put( "login", new Login() );
        commands.put( "register", new Register() );
        commands.put( "redirect", new Redirect() );
        commands.put( "addUser", new AddUser());
        commands.put( "editUser", new EditUser());
        commands.put( "deleteUser", new DeleteUser());
        commands.put( "bulkHandler", new BulkHandler());
    }

    static Command from( HttpServletRequest request ) {
        String targetName = request.getParameter( "target" );
        if ( commands == null ) {
            initCommands();
        }
        return commands.getOrDefault(targetName, new UnknownCommand() );   // unknowncommand er default.
    }

    protected abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws LoginSampleException, InvalidPassword, UserExists;

}
