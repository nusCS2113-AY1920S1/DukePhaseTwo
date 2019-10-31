package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when duplicate route is found.
 */
public class RouteDuplicateException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public RouteDuplicateException() {
        super(Messages.ERROR_ROUTE_DUPLICATE);
    }
}