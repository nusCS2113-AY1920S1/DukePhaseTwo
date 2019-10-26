package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientPrimaryCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientPrimarySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();
        patient.setPrimaryDiagnosis(getArg());
        patient.updateAttributes();
        core.ui.print("Primary diagnosis set!");
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
