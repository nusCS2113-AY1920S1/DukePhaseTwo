package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import user.Login;
import user.User;

import java.io.*;
import java.text.ParseException;

public class CreateAccountCommand extends Command {
    private String[] splitC;
    /**
     * Create new Account for user
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public  CreateAccountCommand(String input, String[] splitStr) throws DukeException, IOException {
        File membersFile = new File("data\\members.txt");
        membersFile.createNewFile(); //if file already exists, won't create
        if (splitStr.length == 1)
            throw new DukeException("\u2639 OOPS!!! Please create your account with the following format: email, password, username, usertype");
        this.splitC = input.split(" ");
        if(Login.checkExistence(splitC[1],"data\\members.txt"))
            throw new DukeException("\u2639 OOPS!!! There is already an account registered under that email!");
        if (!splitC[1].contains("@u.nus.edu"))
            throw new DukeException("\u2639 OOPS!!! Please create an account using your NUS email ending with @u.nus.edu");
        if (splitC[2].matches("[0-9]+") || splitC[2].matches("[a-z]+") || splitC[2].matches("[A-Z]+"))
            throw new DukeException("\u2639 OOPS!!! Your password must contain at least one alphabet, one number and no special characters!");
        if (!splitC[4].contains("A") && !splitC[4].contains("R") && !splitC[4].contains("C"))
            throw new DukeException("\u2639 OOPS!!! Your userType can only be one of the following: A (Admin), R (Resident) or C (Club Head)!");
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException, ParseException {
        User newUser = new User(splitC[1], splitC[2], splitC[3], splitC[4]);
        BufferedWriter bw = new BufferedWriter(new FileWriter("data\\members.txt", true));
        bw.write(newUser.toWriteFile());
        bw.newLine();
        bw.close();
        ui.addToOutput("Your account has been successfully created!");
    }
}


