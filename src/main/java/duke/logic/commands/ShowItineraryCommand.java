package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

import java.io.FileNotFoundException;

/**
 * Shows the requested Itinerary.
 */
public class ShowItineraryCommand extends Command {
    private String number;

    /**
     * Constructs the command with the given itinerary name.
     *
     */
    public ShowItineraryCommand(String number) {
        this.number = number;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        Itinerary itinerary = model.getItinerary(number);
        return new CommandResultText(itinerary.printItinerary());
    }
}