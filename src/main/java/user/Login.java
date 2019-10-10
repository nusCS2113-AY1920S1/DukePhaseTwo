package user;

import java.io.*;
import java.util.Scanner;

public class Login{
    /**
     * verfiyLogin verifies if the email and password input by user is a registered account
     * @param email for login
     * @param password for login
     * @param filePath of members login and user info
     * @throws FileNotFoundException
     */
    public static boolean verifyLogin(String email, String password, String filePath) throws IOException {
        BufferedReader reader;
        boolean found = false;
        String tempEmail;
        String tempPassword;
        reader = new BufferedReader(new FileReader(filePath));

        String line = reader.readLine();

        while (line != null){
            String[] parts = line.split("[|]");
            tempEmail = parts[0];
            tempPassword = parts[1];
            if (email.trim().equals(tempEmail.trim()) && password.trim().equals(tempPassword.trim()))
                found = true;
            line = reader.readLine();
        }
        reader.close();
        return found;
    }

    public static boolean checkExistence(String email,String filePath) throws IOException {
        BufferedReader reader;
        boolean found = false;
        reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null){
            String[] parts = line.split("[|]");
            if(parts[0].trim().equals(email.trim()))
                found = true;
            line = reader.readLine();
        }
        reader.close();
        return found;
    }
}
