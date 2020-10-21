package FunctionLayer.layer.user;

public class UserExists extends Exception {

    public UserExists(String email) {
        super("User with that email already exists: " + email);
    }
}
