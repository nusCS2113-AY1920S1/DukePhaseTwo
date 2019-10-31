package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientDeleteCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientDeleteSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();
        String searchCritical = getSwitchVal("critical");
        String searchInvestigation = getSwitchVal("investigation");
        String searchImpression = getSwitchVal("impression");

        if (searchCritical != null && (patient.getPrimaryDiagnosis()) != null) {
            if (patient.getPrimaryDiagnosis().getName().equals((searchCritical))) {
                patient.deletePriDiagnose();
                core.updateUi("Successfully deleted " + searchCritical);
            } else {
                core.updateUi("Unsuccessfully deleted patient's primary diagnosis does not match " + searchCritical);
            }
        } else if (searchCritical != null) {
            core.updateUi("Patient does not have a primary diagnosis.");
        }

        if (searchInvestigation != null) {
            // TODO
            core.updateUi("Not implemented yet, bug when adding treatments needs to be solved first");
            //core.ui.print("Successfully deleted " + searchInvestigation);
        }
        if (searchImpression != null) {
            if ((patient.getImpressionsObservableMap().containsKey(searchImpression))) {
                patient.deleteImpression(searchImpression);
                core.updateUi("Successfully deleted " + searchImpression);
            } else {
                core.updateUi("Unsuccessfully deleted, none of patient's impressions match " + searchImpression);
            }
        }

        patient.updateAttributes();
    }
}