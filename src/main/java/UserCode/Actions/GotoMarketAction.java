package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {

    public GotoMarketAction() {
        this.type = ActionType.gotoMarket;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException {
        farmer.changeLocation("-Traveling-");
        Simulation.animate(ui, storage, farmer, "GotoMarketSimulation", 1, 11);
        farmer.changeLocation("Market");
        Simulation.animate(ui, storage, farmer, "GotoMarketSimulation", 12, 1000);
        ui.typeWriter("You have arrived at the market");
    }
}

