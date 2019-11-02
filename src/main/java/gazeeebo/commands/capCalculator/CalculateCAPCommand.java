package gazeeebo.commands.capCalculator;

import java.util.ArrayList;
import java.util.Map;

/**
 * Calculate the cap by using a formula.
 */
public class CalculateCAPCommand {
    /**
     * Calculate the CAP of all the modules.
     *
     * @param caplist the object that deals
     *                stores semNumber, moduleCode, moduleCredits and CAP score.
     * @return the CAP.
     */
    public double calculateCAP(final Map<String,
            ArrayList<CAPCommand>> caplist) {
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : caplist.keySet()) {
            for (int i = 0; i < caplist.get(key).size(); i++) {
                double score = new ConvertGradeToScoreCommand().
                        converter(caplist.get(key).get(i).grade);
                if (score != 0.1) {
                    sumGPAMCS += caplist.get(key).get(i).moduleCredit * score;
                    sumMCS += caplist.get(key).get(i).moduleCredit;
                }
            }
        }
        double cap = sumGPAMCS / sumMCS;
        return cap;
    }

    /**
     * Calculate the GPA of the particular sem.
     *
     * @param caplist   the object that deals stores
     *                  semNumber, moduleCode, moduleCredits and GPA.
     * @param semNumber the sem which you want to find the GPA.
     * @return the GPA.
     */
    public double calculateCAPPerSem(final Map<String,
            ArrayList<CAPCommand>> caplist, final String semNumber) {
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : caplist.keySet()) {
            if (key.equals(semNumber)) {
                for (int i = 0; i < caplist.get(key).size(); i++) {
                    double score =
                            new ConvertGradeToScoreCommand().converter(caplist.get(key).get(i).grade);
                    if (score != 0.1) {
                        sumGPAMCS += caplist.get(key).get(i).moduleCredit
                                * score;
                        sumMCS += caplist.get(key).get(i).moduleCredit;
                    }
                }
            }
        }
        double cap = sumGPAMCS / sumMCS;
        return cap;
    }
}