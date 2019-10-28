package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Ui;

public class CommandLevelReset extends Command {
    /**
     * Resets the level and farmer variables to previous state
     * @param farmio The game which is reverted to a previous state
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try{
            farmio.setFarmer(new Farmer(storage.loadFarmer()));//TODO implement level.reset to reset the stage in case user types some wrong thing
        } catch (FarmioException e) {
            ui.showWarning(e.getMessage());
            ui.showInfo("Attempting recovery process.");
            try {
                farmio.setFarmer(new Farmer(storage.loadFarmerBackup()));
            } catch (FarmioException ex) {
                ui.showError("Recovery process failed!");
                ui.showInfo("Game cannot continue. Exiting now.");
            }
            ui.showInfo("Recovery successful.");
        }
        farmio.getUi().typeWriter("Level reset successful! Press [ENTER] to continue", false);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
