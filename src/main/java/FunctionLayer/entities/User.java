package FunctionLayer.entities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * The purpose of User is to...
 * @author kasper
 */
public class User {

    //Password stuff
    private static final int PASSWORD_ITERATIONS = 65536;
    private static final int PASSWORD_LENGTH = 256; // 32 bytes
    private static final SecretKeyFactory PASSWORD_FACTORY;
    static {
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PASSWORD_FACTORY = factory;
    }
    private final byte[] salt;
    private final byte[] secret;

    private final int id;
    private final String username;
    private String email;
    private String userRole;
    private final LocalDateTime registrationDate;

    public User(int id, String username, String email, String userRole, LocalDateTime registrationDate, byte[] salt, byte[] secret) {
        this.id = id;
        this.username = username;
        this.email= email;
        this.userRole = userRole;
        this.registrationDate = registrationDate;
        this.salt = salt;
        this.secret = secret;
    }

    public User(String username, String email, String userRole, LocalDateTime registrationDate, byte[] salt, byte[] secret) {
        this.id = -1;
        this.username = username;
        this.email= email;
        this.userRole = userRole;
        this.registrationDate = registrationDate;
        this.salt = salt;
        this.secret = secret;
    }

    //Is used when we edit a user
    public User(int id, String username, String email, String userRole) {
        this.id = id;
        this.username = username;
        this.email= email;
        this.userRole = userRole;
        this.registrationDate = null;
        this.salt = null;
        this.secret = null;
    }

    public User withId (int id) {
        return new User(id, this.username, this.email, this.userRole, this.registrationDate, this.salt, this.secret);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserRole() {
        return userRole;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getSecret() {
        return secret;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Password stuff
     */

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] calculateSecret(byte[] salt, String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                PASSWORD_ITERATIONS,
                PASSWORD_LENGTH);
        try {
            return PASSWORD_FACTORY.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPasswordCorrect(String password) {
        return Arrays.equals(this.secret, calculateSecret(salt, password));
    }

    // Thanks: https://stackoverflow.com/a/13006907
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }



}
