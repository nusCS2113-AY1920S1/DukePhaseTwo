package duke.storage;

import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Repeat;
import duke.task.DoAfter;
import duke.task.FixedDuration;
import duke.ui.Ui;
import duke.dukeexception.DukeException;

//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a storage to store the task list into a text file.
 */
public class Storage {
    protected String filePath = "./";


    /**
     * Creates a storage with a specified filePath.
     *
     * @param filePath The location of the text file.
     */
    public Storage(String filePath) {
        this.filePath += filePath;
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the task list.
     * @throws IOException  If there is an error reading the text file.
     */
    public ArrayList<Task> read() throws IOException {
        ArrayList<Task> items = new ArrayList<>();
        Ui ui = new Ui();
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String taskDesc;
        String dateDesc;
        String afterDesc;
        String durDesc;
        while ((st = br.readLine()) != null) {
            String[] commandList = st.split("\\|");
            try {
                //clear previous dates/desc
                taskDesc = "";
                dateDesc = "";
                afterDesc = "";
                durDesc = "";
                for (int i = 0; i < commandList.length; i++) {
                    if (i == 2) {
                        taskDesc = commandList[i];
                    } else if (i == 3) {
                        if (commandList[0].equals("A")) {
                            afterDesc = commandList[i];
                        } else if (commandList[0].equals("F")) {
                            durDesc = commandList[i];
                        } else {
                            dateDesc = commandList[i];
                        }
                    }
                }
                boolean checked = false;
                if (commandList.length > 1) {
                    if (!(commandList[1].equals("1") || commandList[1].equals("0"))) {
                        throw new DukeException("Error reading 1 or 0, skipping to next line");
                    }
                    checked = commandList[1].equals("1");
                }
                Task t;
                if (commandList[0].equals("T")) {
                    if (taskDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description, skipping to next line");
                    } else {
                        t = new Todo(taskDesc);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (commandList[0].equals("D")) {

                    if (taskDesc.trim().isEmpty() || dateDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Deadline(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (commandList[0].equals("E")) {
                    if (taskDesc.isEmpty() || dateDesc.isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Event(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (commandList[0].equals("R")) {
                    if (taskDesc.isEmpty() || dateDesc.isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Repeat(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (commandList[0].equals("A")) {
                    if (taskDesc.isEmpty() || afterDesc.isEmpty()) {
                        throw new DukeException("Error reading description or do after description,"
                                + " skipping to next line");
                    } else {
                        t = new DoAfter(taskDesc, afterDesc);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (commandList[0].equals("F")) {
                    System.out.println(taskDesc + dateDesc);
                    if (taskDesc.isEmpty() || durDesc.isEmpty()) {
                        throw new DukeException("Error reading description or do after description,"
                                + " skipping to next line");
                    } else {
                        int duration = Integer.parseInt(durDesc.split(" ")[0]);
                        t = new FixedDuration(taskDesc, duration, durDesc.split(" ")[1]);
                        t.setStatusIcon(checked);
                        items.add(t);
                    }
                } else if (!commandList[0].isEmpty()) {
                    throw new DukeException("Error reading whether if its T, D, E, R, A, or F skipping to next line");
                }
            } catch (Exception e) {
                ui.showErrorMsg("     Error when reading current line, please fix the text file:");
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue reading the rest of file");
            }
        }
        br.close();
        return items;
    }

    /**
     * Updates the text file from interpreting the tasks of the task list.
     *
     * @param items The task list that contains a list of tasks.
     * @throws IOException  If there is an error writing the text file.
     */
    public void write(TaskList items) throws IOException {
        String fileContent = "";
        for (int i = 0; i < items.size(); i++) {
            fileContent += items.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }

    //    public void saveFile(ArrayList<Task> listOfTasks){
    //        try {
    //            FileOutputStream fw = new FileOutputStream(filePath);
    //            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fw);
    //            objectOutputStream.writeObject(listOfTasks);
    //            objectOutputStream.close(); //always close
    //            fw.flush();
    //            fw.close();
    //        } catch (IOException IOE) {
    //            System.out.println("Something went wrong " + IOE.getMessage());
    //        }
    //    }
}
