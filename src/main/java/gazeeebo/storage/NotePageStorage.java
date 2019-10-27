package gazeeebo.storage;

import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class NotePageStorage {
    public static void writeToGoalFile() throws IOException {
        File f = new File("/goal.txt");
        if (f.setWritable(true, false)) {
            System.out.println("can write");
        } else {
            System.out.println("cannot write");
        }
        FileWriter file = new FileWriter(f);
        file.write(GeneralNotePage.goal);
        file.flush();
        file.close();
    }

    public static void readFromGoalFile() throws IOException {
        InputStream inputStream = NoteStorage.class.getResourceAsStream("/goal.txt");
        Scanner txtFile = new Scanner(inputStream);
        if (txtFile.hasNextLine()) {
            GeneralNotePage.goal = txtFile.nextLine();
        }
        inputStream.close();
    }

    public static void writeToModulesFile() throws IOException {
        FileWriter file = new FileWriter("/modules.txt");
        for (Module m : GeneralNotePage.modules) {
            file.write(m.name + "\n");
            file.write(m.assessments.size() + "\n");
            for (Assessment a : m.assessments) {
                file.write(a.name + "\n");
                file.write(a.weightage + "\n");
            }
            file.write(m.miscellaneousInfo.size() + "\n");
            for (String s : m.miscellaneousInfo) {
                file.write(s + "\n");
            }
        }
        file.flush();
        file.close();
    }

    public static void readFromModulesFile() throws IOException {
        InputStream inputStream = NoteStorage.class.getResourceAsStream("/modules.txt");
        Scanner txtFile = new Scanner(inputStream);
        while (txtFile.hasNextLine()) {
            Module m = new Module(txtFile.nextLine()); //read in module name
            int numOfAssmt = Integer.parseInt(txtFile.nextLine());
            for (int i = 0; i < numOfAssmt; i++) {
                m.assessments.add(new Assessment(txtFile.nextLine(), Integer.parseInt(txtFile.nextLine())));
            }
            int numOfMsc = Integer.parseInt(txtFile.nextLine());
            for (int j = 0; j < numOfMsc; j++) {
                m.miscellaneousInfo.add(txtFile.nextLine());
            }
            GeneralNotePage.modules.add(m);
        }
        inputStream.close();
    }
}
