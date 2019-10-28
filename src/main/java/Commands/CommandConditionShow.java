package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandConditionShow extends Command {
    /**
     * Shows a list of Conditions the user can take
     * @param farmio the game where the level is extracted to show only the relevant conditions
     * @throws FarmioFatalException if Simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        if ((int)farmio.getFarmer().getLevel() == 1) {
            farmio.getSimulation().simulate("ConditionList", 1);
        } else if ((int)farmio.getFarmer().getLevel() == 2) {
            farmio.getSimulation().simulate("ConditionList", 2);
        } else if ((int)farmio.getFarmer().getLevel() == 3) {
            farmio.getSimulation().simulate("ConditionList", 3);
        }
        ui.show("Press [Enter] to go back");
    }
}
