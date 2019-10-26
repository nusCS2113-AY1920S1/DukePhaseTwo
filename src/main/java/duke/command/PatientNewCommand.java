package duke.command;

import duke.DukeCore;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientNewCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Patient patient = (Patient) core.uiContext.getObject();
        Impression imp = new Impression(getSwitchVal("name"), getSwitchVal("description"), patient.getBedNo());
        patient.addNewImpression(imp);
        core.ui.print("Impression added:\n" + patient.getImpression(imp.getName()).toString());
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
