package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.stream.Collectors;

public class addModuleCommand {
    /** This method allows user to add module code into study plan, store changes in data structure and external txt files.
     *
     * @param StudyPlan The object contain all needed modules data structure and a showplan method to display plan table
     * @param storage The object that deals with access,modify and save files
     * @param ui The object that deals with interaction between users and the system.
     * @throws IOException
     */
    public void execute(StudyPlannerCommand StudyPlan, Storage storage, Ui ui) throws IOException,DukeException {
        try {
            if (ui.fullCommand.split(" ").length != 4){
                throw new DukeException("Please follow the correct input format~");
            }
            String ModuleCode = ui.fullCommand.split(" ")[1];
            if(ModuleCode.isEmpty()) throw new DukeException("Module code could not be null");
            if(StudyPlan.MCMap.get(ModuleCode)==null) throw new DukeException("We currently do not support this module.");
            int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
            if(Semester >=8||Semester<0)throw new ArrayIndexOutOfBoundsException();
            boolean flag = false;
            for(int i=0;i<StudyPlan.StudyPlan.size()&& !flag;i++){
                if(StudyPlan.StudyPlan.get(i).contains(ModuleCode)) flag = true;
            }
            if(flag) throw new DukeException("This module is already inside the study plan");
            StudyPlan.StudyPlan.get(Semester).add(ModuleCode);
            String toStore = "";
            for (int i = 0; i < StudyPlan.StudyPlan.size(); i++) {
                toStore += StudyPlan.StudyPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.Storage_StudyPlan(toStore);
            System.out.println("This module " + ModuleCode + " has been successfully added to Sem" + (Semester + 1) + ".");
        }catch (DukeException e){
            System.out.println(e.getMessage());
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input correct Semester number");
        }
    }
}