package duke.user;

import duke.exceptions.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class User {
    private ArrayList<Tuple> weight = new ArrayList();
    private int height = 0;
    private int age;
    private Gender sex;
    private boolean isSetup;
    private String name;
    private int activityLevel;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private boolean loseWeight;

    public User() {
        this.isSetup = false;
    }

    public User(String name, int age, int height, Gender sex, int activityLevel, boolean loseWeight) {
        this.name = name;
        this.height = height;
        this.sex = sex;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.loseWeight = loseWeight;
    }

    public void setup() throws DukeException {
        Scanner in = new Scanner(System.in);
        String name;
        int weight = 0;
        int height = 0;
        System.out.println("     Input name");
        name = in.nextLine();
        try {
            System.out.println("     Input age");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input weight");
            weight = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input height");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        System.out.println("     Input gender(Male/Female)");
        String sex = in.nextLine();
        if (sex.charAt(0) == 'M') {
            this.sex = Gender.MALE;
        } else {
            this.sex = Gender.FEMALE;
        }
        int activityLevel = 5;
        while (activityLevel > 4 || activityLevel < 0) {
            System.out.println("     Input Activity Level");
            System.out.println("     1) Sedentary (Little or no exercise, desk job");
            System.out.println("     2) Lightly active (Light exercise/ sports 1-3 days/week");
            System.out.println("     3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
            System.out.println("     4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
            System.out.println("     5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                    + "marathon, or triathlon, etc. )");
            try {
                activityLevel = Integer.parseInt(in.nextLine()) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException(e.getMessage());
            }
        }
        System.out.println("     Would you like to lose weight?(Y/N)");
        String choice = in.nextLine();
        if (choice.charAt(0) == 'Y') {
            this.loseWeight = true;
        } else {
            this.loseWeight = false;
        }
        this.name = name;
        setWeight(weight);
        this.height = height;
        this.activityLevel = activityLevel;
        this.isSetup = true;
    }

    public void setWeight(int weight) {
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendarDate.getTime());
        this.weight.add(new Tuple(currentDate, weight));
    }

    public void setWeight(int weight, String date) throws DukeException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        String currentDate = dateFormat.format(temp.getTime());
        this.weight.add(new Tuple(currentDate, weight));
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setLoseWeight() {
        this.loseWeight = true;
    }

    public void setMaintainWeight() {
        this.loseWeight = true;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getWeight() {
        return this.weight.get(this.weight.size() - 1).weight;
    }

    public ArrayList<Tuple> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getActivityLevel() {
        return this.activityLevel;
    }

    public int getDailyCalorie() {
        double calorie;
        if (this.sex == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        return (int)(((this.loseWeight) ? 0.8 : 1) * this.factor[this.activityLevel] * calorie);
    }

    public boolean getLoseWeight() {
        return this.loseWeight;
    }

    public Gender getSex() {
        return this.sex;
    }

    public boolean getIsSetup() {
        return this.isSetup;
    }
}