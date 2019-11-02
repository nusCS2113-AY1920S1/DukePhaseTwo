package gazeeebo.commands.specialization;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class CompletedCommand {
    final static int COMMS_NETWORKING_INDEX = 1;
    final static int EMBEDDED_COMPUTING_INDEX = 2;
    final static int INTELLIGENT_SYSTEMS_INDEX = 3;
    final static int INTERACTIVE_DIGITAL_MEDIA_INDEX = 4;
    final static int LARGE_SCALE_COMPUTING_INDEX = 5;
    final static int SYS_DESIGN_INDEX = 6;
    public CompletedCommand(Ui ui, Storage storage, Map<String, ArrayList<ModuleCategory>> specMap, Map<String, ArrayList<String>> completedEMap) throws DukeException, IOException {
        try {
            ArrayList<String> completedElectiveList = new ArrayList();
            System.out.println("Which specialization number is your module under?");
            ArrayList<String> specList = new ArrayList<String>();
            specList.add("Communications & Networking"); //index 0
            specList.add("Embedded Computing"); //index 1
            specList.add("Intelligent Systems"); //index 2
            specList.add("Interactive Digital Media"); //index 3
            specList.add("Large-Scale Computing"); //index 4
            specList.add("System-On-A-Chip Design"); //index 5
            for (int i = 0; i < specList.size(); i++) {
                System.out.println(i + 1 + ". " + specList.get(i));
            }

            ui.readCommand();
            int specNumber = Integer.parseInt(ui.fullCommand);
            if(specNumber < 0 || specNumber > specList.size()) {
                throw new DukeException("Specialization index does not exist.");
            }
            String checkKey = "";
            if (specNumber == COMMS_NETWORKING_INDEX) {
                checkKey = specList.get(COMMS_NETWORKING_INDEX - 1);
            } else if (specNumber == EMBEDDED_COMPUTING_INDEX) {
                checkKey = specList.get(EMBEDDED_COMPUTING_INDEX - 1);
            } else if (specNumber == INTELLIGENT_SYSTEMS_INDEX) {
                checkKey = specList.get(INTELLIGENT_SYSTEMS_INDEX - 1);
            } else if (specNumber == INTERACTIVE_DIGITAL_MEDIA_INDEX) {
                checkKey = specList.get(INTERACTIVE_DIGITAL_MEDIA_INDEX - 1);
            } else if (specNumber == LARGE_SCALE_COMPUTING_INDEX) {
                checkKey = specList.get(LARGE_SCALE_COMPUTING_INDEX - 1);
            } else if (specNumber == SYS_DESIGN_INDEX) {
                checkKey = specList.get(SYS_DESIGN_INDEX - 1);
            }
            System.out.println("Which module have you completed?");
            for (int i = 0; i < specMap.get(checkKey).size(); i++) {
                System.out.println(i + 1 + ". " + specMap.get(checkKey).get(i).code);
            }

            ui.readCommand();
            int moduleCodeIndex = Integer.parseInt(ui.fullCommand);

            if(moduleCodeIndex < 0 || moduleCodeIndex > specMap.get(checkKey).size()) {
                throw new DukeException("The module index does not exist.");
            }
            String moduleCode = specMap.get(checkKey).get(moduleCodeIndex - 1).code;

            boolean isEqual = false;
            for (String key : completedEMap.keySet()) {
                if (checkKey.equals(key)) { //if date equal
                    completedEMap.get(key).add(moduleCode);
                    isEqual = true;
                }
            }
            if (isEqual == false) {
                completedElectiveList.add(moduleCode);
                completedEMap.put(checkKey, completedElectiveList);
            }

            System.out.println("You have completed " + moduleCode + ".");

            String toStoreCN = "";
            String toStoreEC = "";
            String toStoreIS = "";
            String toStoreIDM = "";
            String toStoreLS = "";
            String toStoreSC = "";

            for (String key : completedEMap.keySet()) {
                if (key.equals("Communications & Networking")) {
                    for (int i = 0; i < completedEMap.get("Communications & Networking").size(); i++) {
                        toStoreCN = toStoreCN.concat("Communications & Networking" + "|" + completedEMap.get("Communications & Networking").get(i));
                    }
                } else if (key.equals("Embedded Computing")) {
                    for (int i = 0; i < completedEMap.get("Embedded Computing").size(); i++) {
                        toStoreEC = toStoreEC.concat("Embedded Computing" + "|" + completedEMap.get("Embedded Computing").get(i));
                    }
                } else if (key.equals("Intelligent Systems")) {
                    for (int i = 0; i < completedEMap.get("Intelligent Systems").size(); i++) {
                        toStoreIS = toStoreIS.concat("Intelligent Systems" + "|" + completedEMap.get("Intelligent Systems").get(i));
                    }
                } else if (key.equals("Interactive Digital Media")) {
                    for (int i = 0; i < completedEMap.get("Interactive Digital Media").size(); i++) {
                        toStoreIS = toStoreIS.concat("Interactive Digital Media" + "|" + completedEMap.get("Interactive Digital Media").get(i));
                    }
                } else if (key.equals("Large-Scale Computing")) {
                    for (int i = 0; i < completedEMap.get("Large-Scale Computing").size(); i++) {
                        toStoreIS = toStoreIS.concat("Large-Scale Computing" + "|" + completedEMap.get("Large-Scale Computing").get(i));
                    }
                } else if (key.equals("System-On-A-Chip Design")) {
                    for (int i = 0; i < completedEMap.get("System-On-A-Chip Design").size(); i++) {
                        toStoreIS = toStoreIS.concat("System-On-A-Chip Design" + "|" + completedEMap.get("System-On-A-Chip Design").get(i));
                    }
                }
                String allCompletedE = toStoreCN + "\n" + toStoreEC + "\n" + toStoreIS + "\n" + toStoreIDM + "\n" + toStoreLS + "\n" + toStoreSC;
                storage.completedElectivesStorage(allCompletedE);
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}