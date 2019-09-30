package Storage;

import java.io.File;

import Tasks.Task;
import Tasks.*;
import commands.FixDurationCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Storage {
    //String directory = System.getProperty("user.home");
    //String fileName = "sample.txt";

    private String absolutePath = "Save.txt";

    public void Storages(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public ArrayList<Task> ReadFile() throws IOException {
        ArrayList<Task> tList = new ArrayList<Task>();
        if (new File(absolutePath).exists()) {
            File file = new File(absolutePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] details = sc.nextLine().split("\\|");
                if (details[0].equals("T")) {
                    Todo t = new Todo(details[2].trim());
                    if (details[1].equals("\u2713")) {
                        t.isDone = true;
                    } else {
                        t.isDone = false;
                    }
                    tList.add(t);
                } else if (details[0].equals("D")) {
                    Deadline d = new Deadline(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("\u2713")) {
                        d.isDone = true;
                    } else {
                        d.isDone = false;
                    }
                    tList.add(d);
                } else if (details[0].equals("P")) {
                    Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                    if (details[1].equals("\u2713")) {
                        tb.isDone = true;
                    } else {
                        tb.isDone = false;
                    }
                    tList.add(tb);
                } else if (details[0].equals("FD")) {
                    FixedDuration FD = new FixedDuration(details[2].trim(), details[3].trim());
                    if (details[1].equals("\u2713")) {
                        FD.isDone = true;
                    } else {
                        FD.isDone = false;
                    }
                    tList.add(FD);
                } else if (details[0].equals("DA")) {
                    DoAfter DA = new DoAfter(details[3].trim(), details[3].trim(), details[2].trim());
                    if (details[1].equals("\u2713")) {
                        DA.isDone = true;
                    } else
                        DA.isDone = false;
                } else {
                    Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("\u2713")) {
                        e.isDone = true;
                    } else {
                        e.isDone = false;
                    }
                    tList.add(e);
                }
            }
        }
        return tList;
    }
}
