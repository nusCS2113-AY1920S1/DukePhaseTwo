package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a route is not found.
 */
public class DukeRouteNotFoundException extends DukeException {
    public DukeRouteNotFoundException() {
        super(Messages.ROUTE_NOT_FOUND);
    }
}
