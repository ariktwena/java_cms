package FunctionLayer.layer.user;

public class UserAlreadyLoggedIn extends Exception {

    public UserAlreadyLoggedIn(String name) {
        super("User already logged in: " + name);
    }

}
