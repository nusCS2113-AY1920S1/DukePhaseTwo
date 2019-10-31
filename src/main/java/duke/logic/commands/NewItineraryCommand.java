package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

import java.io.FileNotFoundException;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private Itinerary itinerary;

    /**
     * Constructs the command with the given sample itinerary.
     *
     */
    public NewItineraryCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        model.saveItinerary(itinerary);
        model.itineraryListSave(itinerary);
        return new CommandResultText("New Itinerary Created :" + itinerary.printItinerary());
    }
}