package duke.storage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import duke.exceptions.DukeException;
import duke.tasks.Dinner;
import duke.tasks.Lunch;
import duke.tasks.Meal;
import duke.tasks.Breakfast;
import duke.user.User;
import duke.user.Gender;
import duke.user.Tuple;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 * @author Ivan Andika Lie
 */
public class Storage {
    private String line = null;
    private File file = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private File nameFile = null;
    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that TaskList.
     * @return the ArrayList of task loaded from the file
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */

    public HashMap<String, ArrayList<Meal>> load() throws DukeException {
        HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();
        String sep = System.getProperty("file.separator");
        file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                            + sep + "Data" + sep + "duke.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, mealTracker);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        return mealTracker;
    }

    /**
     * This function acts as a line by line parser from the text file which is used to load a particular type of task.
     * @param line the line input from the input file
     * @param mealTracker the task arraylist that will store the tasks from the input file
     */
    private void loadFile(String line, HashMap<String, ArrayList<Meal>> mealTracker) {
        String[] splitLine = line.split("\\|",4);
        String taskType = splitLine[0];
        boolean isDone = splitLine[1].equals("1");
        String description = splitLine[2];
        String[] nutritionalValue = splitLine[3].split("\\|");
        Meal newMeal = null;
        if (taskType.equals("B")) {
            newMeal = new Breakfast(description, nutritionalValue);
        } else if (taskType.equals("L")) {
            newMeal = new Lunch(description, nutritionalValue);
        } else if (taskType.equals("D")) {
            newMeal = new Dinner(description, nutritionalValue);
        }
        if (isDone) {
            newMeal.markAsDone();
        }
        String mealDate = newMeal.getDate();
        if (!mealTracker.containsKey(mealDate)) {
            mealTracker.put(mealDate, new ArrayList<Meal>());
            mealTracker.get(mealDate).add(newMeal);
        } else {
            mealTracker.get(mealDate).add(newMeal);
        }

    }

    /**
     * This is a function that will update the input/output file from the current arraylist of tasks.
     * @param meals the task arraylist that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(HashMap<String, ArrayList<Meal>> meals) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : meals.keySet()) {
                ArrayList<Meal> mealsInDay = meals.get(i);
                for (int j = 0; j < meals.get(i).size(); j++) {
                    Meal currentMeal = mealsInDay.get(j);
                    String status = "0";
                    if (currentMeal.getIsDone()) {
                        status = "1";
                    }
                    String toWrite = currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                            + "|date|" + currentMeal.getDate();
                    HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                    if (nutritionData.size() != 0) {
                        toWrite += "|";
                        for (String k : nutritionData.keySet()) {
                            toWrite += k + "|" + nutritionData.get(k) + "|";
                        }
                        toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                    }
                    bufferedWriter.write(toWrite);
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public User loadUser() throws DukeException {
        User tempUser;
        String sep = System.getProperty("file.separator");
        nameFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "Data" + sep + "user.txt");

        if (nameFile.length() == 0) {
            return new User();
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(nameFile));
            String line =  bufferedReader.readLine();
            String[] splitLine = line.split("\\|");
            String name = splitLine[0];
            int age = Integer.parseInt(splitLine[1]);
            int height = Integer.parseInt(splitLine[2]);
            int activityLevel = Integer.parseInt(splitLine[3]);
            boolean loseWeight = Boolean.parseBoolean(splitLine[4]);
            String sex = splitLine[5];
            if (sex.equals("M")) {
                tempUser = new User(name, age, height, Gender.MALE, activityLevel, loseWeight);
            } else {
                tempUser = new User(name, age, height, Gender.FEMALE, activityLevel, loseWeight);
            }
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitWeightInfo = line.split("\\|");
                tempUser.setWeight(Integer.parseInt(splitWeightInfo[1]), splitWeightInfo[0]);
            }
            bufferedReader.close();
            return tempUser;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void saveUser(User user) throws DukeException {
        String toWrite = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getLoseWeight() + "|";
        if (user.getSex() == Gender.MALE) {
            toWrite += "M";
        } else {
            toWrite += "F";
        }
        ArrayList<Tuple> allWeight = user.getAllWeight();
        for (int i = 0; i < user.getAllWeight().size(); i += 1) {
            toWrite += "\n";
            String date = allWeight.get(i).date;
            int weight = allWeight.get(i).weight;
            toWrite += date + "|" + weight;
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(nameFile));
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
