package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import UI.Ui;
import java.io.IOException;
import java.text.ParseException;
import Exception.DukeException;
import java.util.ArrayList;

public class RescheduleCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        try {
        if (ui.FullCommand.length() == 10) {
            throw new DukeException("OOPS!!! The object of a rescheduling cannot be null.");
        } else {
            int index = Integer.parseInt(ui.FullCommand.split(" ")[1]) - 1;
            String Decription = list.get(index).description;;
            System.out.println("You are rescheduling this task: " + Decription);
            System.out.println("Please type in your new timeline");
            ui.ReadCommand();
            String time = ui.FullCommand;
            System.out.println("Are you sure you want to reschedule this task? (yes/no)");
            ui.ReadCommand();
            if (ui.FullCommand.equals("yes")) {
                if (list.get(index).listFormat().contains("by")) {
                    Task RescheduledDeadline = new Deadline(Decription, time);
                    list.remove(index);
                    list.add(RescheduledDeadline);
                    System.out.println("Noted. I've changed this task's timeline: ");
                    System.out.println(RescheduledDeadline.listFormat());
                } else {
                    Event RescheduledEvent = new Event(Decription, time);
                    list.remove(index);
                    list.add(RescheduledEvent);
                    System.out.println("Noted. I've changed this task's timeline: ");
                    System.out.println(RescheduledEvent.listFormat());
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                    }
                    storage.Storages(sb.toString());
                } else {
                    System.out.println("It's fine. Nothing has been changed.");
                }
            }
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}