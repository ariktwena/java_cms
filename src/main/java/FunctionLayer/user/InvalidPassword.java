package FunctionLayer.user;

public class InvalidPassword extends Exception{
    public InvalidPassword() {
        super("Passwords do not match.");
    }
}
