package PresentationLayer;

import FunctionLayer.layer.user.LoginSampleException;
import FunctionLayer.layer.user.InvalidPassword;
import FunctionLayer.layer.user.UserExists;
import PresentationLayer.admin.AddUser;
import PresentationLayer.admin.BulkHandlerAdmin;
import PresentationLayer.admin.DeleteUser;
import PresentationLayer.admin.EditUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public abstract class Command {

    private static HashMap<String, Command> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put( "login", new Login() );
        commands.put( "register", new Register() );
        commands.put( "redirect", new Redirect() );
        commands.put( "addUser", new AddUser());
        commands.put( "editUser", new EditUser());
        commands.put( "deleteUser", new DeleteUser());
        commands.put( "bulkHandler", new BulkHandlerAdmin());
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
