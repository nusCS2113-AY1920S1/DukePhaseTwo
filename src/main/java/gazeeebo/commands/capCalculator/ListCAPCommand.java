package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * List out the modules in the semester
 * or list out all modules and the CAP score will be shown.
 */
public class ListCAPCommand {
    /** For formatting the spacing between module name and vertical line. */
    private static final int BLANK_SPACING = 12;

    /**
     * list out the modules and show the CAP of all the modules or a particular sem.
     *
     * @param ui        prints things to the user.
     * @param caplist   deals stores semNumber, moduleCode, moduleCredits and CAP score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @throws IOException catch the error if the read file fails.
     */
    public ListCAPCommand(final Ui ui, final Map<String, ArrayList<CAPCommand>> caplist, final String lineBreak) throws IOException {
        double cap;
        CalculateCAPCommand calculatedGPA = new CalculateCAPCommand();
        System.out.print("Which sem do you want to list? "
                + "all,1,2,3,4,5,6,7,8\n");
        ui.readCommand();
        if (ui.fullCommand.equals("all")) {
            cap = calculatedGPA.calculateCAP(caplist);
            listAll(caplist, lineBreak, cap);
        } else {
            cap = calculatedGPA.calculateCAPPerSem(caplist, ui.fullCommand);
            listSem(caplist, ui, lineBreak, cap);
        }
    }

    /**
     * This method list out all the modules and show the accumulative cap.
     *
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     */
    public void listAll(final Map<String, ArrayList<CAPCommand>> caplist, final String lineBreak, final double cap) {
        System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
        for (String key : caplist.keySet()) {
            for (int i = 0; i < caplist.get(key).size(); i++) {
                int noBlankSpacing = BLANK_SPACING
                        - caplist.get(key).get(i).moduleCode.length();
                System.out.print(key + "   | "
                        + caplist.get(key).get(i).moduleCode);
                for (int j = 0; j < noBlankSpacing; j++) {
                    System.out.print(" ");
                }
                System.out.print("| " + caplist.get(key).get(i).moduleCredit
                        + "  | " + caplist.get(key).get(i).grade
                        + "\n" + lineBreak);
            }
        }
        System.out.print("Total CAP: " + cap + "\n");
    }

    /**
     * This method list out the modules
     * and show the GPA of the a particular sem.
     *
     * @param ui        prints things to the user
     * @param caplist   deals stores
     *                  semNumber, moduleCode, moduleCredits and GPA score.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param cap       CAP of the modules.
     */
    public void listSem(final Map<String, ArrayList<CAPCommand>> caplist, final Ui ui, final String lineBreak, final double cap) {
        System.out.print("Sem | Module code | MC | CAP\n" + lineBreak);
        for (String key : caplist.keySet()) {
            for (int i = 0; i < caplist.get(key).size(); i++) {
                if (key.equals(ui.fullCommand)) {
                    int noBlankSpacing = BLANK_SPACING
                            - caplist.get(key).get(i).moduleCode.length();
                    System.out.print(key + "   | "
                            + caplist.get(key).get(i).moduleCode);
                    for (int j = 0; j < noBlankSpacing; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| " + caplist.get(key).get(i).moduleCredit + "  | " + caplist.get(key).get(i).grade
                            + "\n" + lineBreak);
                }
            }
        }
        System.out.print("Sem " + ui.fullCommand + " CAP: " + cap + "\n");
    }
}