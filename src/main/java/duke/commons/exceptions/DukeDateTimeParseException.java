package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Messages.INVALID_FORMAT);
    }
}
