package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class ViewCommand extends CommandSuper {
    private Controller controller;
    private int constant = 5;

    public ViewCommand(Controller uicontroller) {
        super(COMMANDKEYS.view, CommandStructure.cmdStructure.get(COMMANDKEYS.view), uicontroller);
    }

    @Override
    public void executeCommands() throws Exceptions {
        switch (this.getSubRootCommand()) {
            case watchlist:
                WatchlistHandler.print_list((MovieHandler) (this.getUiController()));
                break;
            case blacklist:
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(Blacklist.printList());
                break;
            case entry:
                int num = Integer.parseInt(getPayload());
                executeEntryCommands(num);
                break;
            case recommendation:
                executeRecommendationCommand();
                break;
            default:
                break;
        }
    }

    /**
     * Responsible for displaying more information about a movie/TV show.
     * Called when user is viewing list of movies/TV shows from a search request and want to know more information.
     *
     * @param num The number of the movie or TV show in the list indicated below the title.
     * @throws Exceptions
     */
    private void executeEntryCommands(int num) throws Exceptions {
        ((MovieHandler) this.getUiController()).showMovie(num);
    }

    /**
     * prints out a list of recommendations based on the users set preferences.
     * @throws IOException file was not able to be found
     */
    private void executeRecommendationCommand() throws Exceptions {
        String feedback = "Your recommended movies are: \n";
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ArrayList<Integer> preferenceIndices = movieHandler.getUserProfile().getGenreIdPreference();
        ArrayList<MovieInfoObject>  movies = movieHandler.getAPIRequester()
                .beginSearchGenre(Integer.toString(preferenceIndices.get(0)), movieHandler.getUserProfile().isAdult());
        for (int i = 0; i < constant; i++) {
            feedback += i + 1 + ". " + movies.get(i).getTitle() + "\n";
        }
        movieHandler.setGeneralFeedbackText(feedback);
    }

}
