package model;

import model.task.Task;
import utils.DukeException;

import java.util.ArrayList;

public class Member {
    public static final String EMAIL_VALIDATION_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String MESSAGE_WRONG_EMAIL_FORMAT = "Wromg email format.";

    String name;
    ArrayList<Task> taskList;
    String biography;
    String email;
    String phone;

    public Member(String name) {
        setName(name);
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task toAdd) {
        if (!taskList.contains(toAdd)) {
            taskList.add(toAdd);
        }
    }

    public void deleteTask(Task toDelete) {
        if (taskList.contains(toDelete)) {
            taskList.remove(toDelete);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.trim();
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws DukeException {
        if (email.matches(EMAIL_VALIDATION_REGEX)) {
            this.email = email;
        } else {
            throw new DukeException(MESSAGE_WRONG_EMAIL_FORMAT);
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
