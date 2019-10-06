package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.ReminderCommand;
import seedu.duke.task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

/**
 * Interacts with the file storing the task information.
 */
public class Storage {
    private static String getSaveFileDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "duke.txt";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "duke.txt";
        } else {
            dir = "." + File.separator + "duke.txt";
        }
        return dir;
    }

    /**
     * This function clears the content of the file and write all the information of the tasks in the task
     * list to that file. This file follows similar structure as the user input and can be used to
     * re-construct the task list later.
     *
     * @param taskList the list of task that is to be written to the file
     */
    public static void saveTasks(TaskList taskList) {
        FileOutputStream out;
        try {
            String dir = getSaveFileDir();
            File file = new File(dir);
            file.createNewFile();
            out = new FileOutputStream(file, false);
            String content = "";
            for (Task task : taskList) {
                content += task.toFileString() + "\n";
            }
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            Duke.getUI().showError("Write to output file IO exception!");
        }
    }

    /**
     * This function reads the file that is previously saved to re-construct and return the task list from the
     * file information. Note: if any error occurs during the reading or parsing of the file, an empty task
     * list will always be returned for the integrity of data.
     *
     * @return task list re-constructed from the save file
     */
    public static TaskList readTasks() {
        TaskList taskList = new TaskList();
        String dir = getSaveFileDir();
        FileInputStream in;
        File file = new File(dir);
        try {
            in = new FileInputStream(file);
            Scanner scanner = new Scanner(in);
            ArrayList<Boolean> doneList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.length() <= 2) {
                    throw new StorageException("Invalid Save File!");
                }
                if (input.startsWith("1")) {
                    doneList.add(true);
                } else if (input.startsWith("0")) {
                    doneList.add(false);
                } else {
                    throw new StorageException("Invalid Save File!");
                }
                input = input.split(" ", 2)[1];
                try {
                    Command adddCommand = Parser.parseAddTaskCommand(taskList, input);
                    adddCommand.setSilent();
                    adddCommand.execute();
                } catch (Parser.UserInputException e) {
                    throw new StorageException("Invalid Save File!");
                }
            }
            for (int i = 0; i < taskList.size(); i++) {
                if (doneList.get(i)) {
                    taskList.get(i).markDone();
                }
            }
            Duke.getUI().showMessage("Saved task file successfully loaded...");
            in.close();
            new ReminderCommand(taskList).execute();
        } catch (FileNotFoundException e) {
            return taskList; //it is acceptable if there is no save file
        } catch (IOException e) {
            Duke.getUI().showError("Read save file IO exception");
        } catch (StorageException e) {
            Duke.getUI().showError(e.toString());
            taskList = new TaskList();
        }
        return taskList;
    }

    /**
     * Exception that belongs to the process of storing and reading of file.
     */
    public static class StorageException extends Exception {
        private String msg;

        /**
         * Instantiates storage exception with a message, which can be later displayed by the UI.
         *
         * @param msg the message of the exception that can be displayed by UI
         */
        public StorageException(String msg) {
            super();
            this.msg = msg;
        }

        /**
         * Converts the exception to string by returning its message.
         *
         * @return message of the exception.
         */
        @Override
        public String toString() {
            return msg;
        }
    }
}
